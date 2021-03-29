package com.jape.exceldemo.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.jape.exceldemo.easy.EasyUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入导出接口
 *
 * @author Jape
 * @since 2021/3/23 16:39
 */
@RestController
public class ExcelController {

    @PostMapping("/importUser")
    public String importUser(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        File saveFile = new File(fileName);
        file.transferTo(saveFile);
        System.err.printf("接收到文件：%s%n", fileName);

        EasyExcel.read(saveFile)
                .head(EasyUser.class)
                .registerReadListener(new AnalysisEventListener<EasyUser>() {

                    @Override
                    public void invoke(EasyUser data, AnalysisContext context) {
                        //入库
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                        //入库完毕
                    }
                })
                .sheet().doRead();
        return "上传成功";
    }

    @PostMapping("/exportUser")
    public void exportUser(HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("导出用户.xlsx", StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName);
        //查库
        List<EasyUser> data = new ArrayList<>();
        EasyExcel.write(response.getOutputStream(), EasyUser.class).sheet("用户")
                .doWrite(data);
    }

}
