package com.jape.springbootdemo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 上传下载
 *
 * @author Jape
 * @since 2021/01/27 17:02
 */
@Controller
@RequestMapping("file")
@Slf4j
public class UpDownController {

    private final String filePath = System.getProperty("user.dir") + File.separator + "file" + File.separator;


    @GetMapping("/download/{fileName}")
    public void download(@PathVariable String fileName, HttpServletRequest req, HttpServletResponse rsp) {

        File file = new File(filePath + fileName);
        if (!file.exists()) {
            log.error("文件不存在");
            return;
        }

        // 默认从头开始的全文下载
        long fileLength = file.length(); //文件大小
        long startP = 0L; //文件块开始指针
        long endP = fileLength - 1; //文件块结束指针
        long contentLength = fileLength; //响应文件块大小

        rsp.reset();
        rsp.setHeader("Accept-Ranges", "bytes");
        rsp.setContentType("application/octet-stream");
        if (req.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
            //IE编码
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8);
        } else {
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), Charset.forName("ISO8859-1"));
        }
        rsp.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        rsp.setHeader("Yc-filename", fileName);
        String range = req.getHeader("Range");
        if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
            rsp.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
            startP = Long.parseLong(range.substring(6, range.indexOf("-")));
            if (range.endsWith("-")) {
                // 续传：bytes=270000- 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
                endP = fileLength - 1;
            } else {
                // 分段下载：bytes=270000-320000
                endP = Long.parseLong(range.substring(range.indexOf("-") + 1));
            }
            contentLength = endP - startP + 1; // 客户端请求的是 270000-320000 之间的字节
        }
        // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
        // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
        rsp.setHeader("Content-Length", Long.toString(contentLength));
        rsp.setHeader("Yc-Length", Long.toString(contentLength));
        // 响应的格式: Content-Range: bytes [文件块的开始字节]-[文件块的结束字节]/[文件的总大小]
        rsp.setHeader("Content-Range", "bytes " + startP + "-" + endP + "/" + fileLength);

        InputStream ins = null;
        try {
            int bufSize = 8192;
            byte[] buffer = new byte[bufSize];
            ins = new FileInputStream(file);
            ins.skip(startP);//断点续传
            long readP = startP;//文件读取指针
            OutputStream out = rsp.getOutputStream();
            while (readP <= endP) {
                int readLen = ins.read(buffer);
                readP += readLen;
                out.write(buffer, 0, readLen);
            }
            out.flush();
            out.close();
            ins.close();
        } catch (IOException ie) {
            // 忽略 ClientAbortException 之类的异常
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }
            } catch (IOException e) {
                log.error("关闭流异常", e);
            }
        }
        log.info("下载退出");
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        File uploadFile = new File(filePath + fileName);
        try {
            boolean suc = uploadFile.createNewFile();
            file.transferTo(uploadFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "上传成功";
    }

    @GetMapping("/openUpload")
    public String openUpload() {
        return "upload";
    }
}

