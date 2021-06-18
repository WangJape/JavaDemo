package com.jape.Net.FTP;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FTPUtil {

    private static String hostname = "10.7.3.168";//ip或域名地址
    private static int port = 21;//端口
    private static String username = "root";//用户名
    private static String password = "123456";//密码

    /**
     * 断开连接
     *
     * @param ftpClient
     * @throws Exception
     */
    public static void disconnect(FTPClient ftpClient) {
        if (ftpClient.isConnected()) {
            try {
                ftpClient.disconnect();
                System.err.println("已关闭连接");
            } catch (IOException e) {
                System.err.println("没有关闭连接");
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试是否能连接
     *
     * @param ftpClient
     * @param hostname  ip或域名地址
     * @param port      端口
     * @param username  用户名
     * @param password  密码
     * @return 返回真则能连接
     */
    public static boolean connect(FTPClient ftpClient, String hostname, int port, String username, String password) {
        boolean flag = false;
        try {
            //ftp初始化的一些参数
            ftpClient.connect(hostname, port);
            ftpClient.enterLocalPassiveMode();// 设置被动传输
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);// 设置以二进制流的方式传输
            ftpClient.setControlEncoding("UTF-8");// 文件编码必须在未连接前设置
            if (ftpClient.login(username, password)) {
                System.out.println("连接ftp成功");
                flag = true;
            } else {
                System.err.println("连接ftp失败，可能用户名或密码错误");
                try {
                    disconnect(ftpClient);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("连接失败，可能ip或端口错误");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 得到某个目录下的文件名(夹)列表
     */
    @SuppressWarnings("unused")
    private List<String> getFileList(FTPClient ftpClient, String path) throws IOException {
        FTPFile[] ftpFiles = ftpClient.listFiles(path);
        List<String> retList = new ArrayList();
        if (ftpFiles == null || ftpFiles.length == 0) {
            return retList;
        }

        for (FTPFile ftpFile : ftpFiles) {
            retList.add(ftpFile.getName());
        }
        return retList;
    }

    /**
     * 上传文件
     *
     * @param ftpClient
     * @param saveName        全路径。如/home/public/a.txt
     * @param fileInputStream 要上传的文件流
     * @return
     */
    public static boolean uploadFile(FTPClient ftpClient, String saveName, InputStream fileInputStream) {
        boolean flag = false;
        try {
            if (ftpClient.storeFile(saveName, fileInputStream)) {
                flag = true;
                System.out.println("上传成功");
            }
        } catch (IOException e) {
            System.err.println("上传失败");
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 功能: 下载文件
     */
    private InputStream downFile(FTPClient ftpClient, String sourceFileName) throws IOException {
        return ftpClient.retrieveFileStream(sourceFileName);
    }

    /**
     * 从FTP服务器上下载文件,支持断点续传，下载百分比汇报
     *
     * @param remoteFile 远程文件路径及名称
     * @param local      本地文件完整绝对路径
     * @return 下载的状态
     * @throws IOException
     */
    public boolean download(FTPClient ftpClient, String remoteFile, String local) throws IOException {
        // 检查远程文件是否存在
        FTPFile[] files = ftpClient.listFiles(remoteFile);
        if (files.length != 1) {
            return false;
        }
        long lRemoteSize = files[0].getSize();
        File localFile = new File(local);
        // 先删除本地文件,不需要断点续传功能
        localFile.delete();
        OutputStream out = new FileOutputStream(localFile);
        InputStream in = ftpClient.retrieveFileStream(remoteFile);
        byte[] bytes = new byte[1024];
        long step = lRemoteSize / 100;
        // 文件过小，step可能为0
        step = step == 0 ? 1 : step;
        long process = 0;
        long localSize = 0L;
        int c;
        while ((c = in.read(bytes)) != -1) {
            out.write(bytes, 0, c);
            localSize += c;
            long nowProcess = localSize / step;
            if (nowProcess > process) {
                process = nowProcess;
                if (process % 10 == 0) {
                    System.err.println("下载进度：" + process);
                }
            }
        }
        in.close();
        out.close();
        return ftpClient.completePendingCommand();

    }

    /**
     * 上传文件到服务器,新上传和断点续传
     *
     * @param remoteFile 远程文件名，在上传之前已经将服务器工作目录做了改变
     * @param localFile  本地文件File句柄，绝对路径
     * @param remoteSize 远程大小
     * @throws IOException
     */
    private static boolean uploadFile(FTPClient ftpClient, File localFile, String remoteFile, long remoteSize) throws IOException {
        // 显示进度的上传
        long step = localFile.length() / 100;
        // 文件过小，step可能为0
        step = step == 0 ? 1 : step;
        long process = 0;
        long localreadbytes = 0L;
        RandomAccessFile raf = new RandomAccessFile(localFile, "r");
        OutputStream out = ftpClient.appendFileStream(remoteFile);
        // 断点续传
        if (remoteSize > 0) {
            ftpClient.setRestartOffset(remoteSize);
            process = remoteSize / step;
            //指定文件的光标位置，下次读文件数据的时候从该位置读取
            raf.seek(remoteSize);
            localreadbytes = remoteSize;
        }

        byte[] bytes = new byte[1024];
        int c;
        while ((c = raf.read(bytes)) != -1) {
            out.write(bytes, 0, c);
            localreadbytes += c;
            if (localreadbytes / step != process) {
                process = localreadbytes / step;
                if (process % 10 == 0) {
                    System.err.println("文件上传进度：" + process);
                }
            }
        }
        out.flush();
        raf.close();
        out.close();
        return ftpClient.completePendingCommand();
    }

    public static void main(String[] args) {
        FTPClient ftpClient = new FTPClient();
        //ftpClient.enterLocalActiveMode();
        ftpClient.enterLocalPassiveMode();
        //1 测试连接
        connect(ftpClient, hostname, port, username, password);
        try {
            File file = new File("H:\\项目\\Java\\wjp\\src\\main\\java\\Net\\FTP\\readme.txt");
            uploadFile(ftpClient, file, "/root/work/test.txt", 0);
        } catch (IOException e) {
            System.err.println("上传失败");
            e.printStackTrace();
        } finally {
            disconnect(ftpClient);
        }
    }

}
