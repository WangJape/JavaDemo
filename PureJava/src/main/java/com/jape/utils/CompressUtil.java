package com.jape.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * 压缩工具
 *
 * @author Jape
 * @since 2020/11/22 13:19
 */
public class CompressUtil {

    private static final int BUFF_SIZE = 4096;

    public static void zip(String sourFilePathStr, String zipFilePathStr) {
        File sourFile = new File(sourFilePathStr);
        if (!sourFile.exists()) {
            throw new IllegalArgumentException("源文件不存在");
        }
        File zipFile = new File(zipFilePathStr);
        if (zipFile.exists()) {
            throw new IllegalArgumentException("压缩文件已存在");
        }
        ZipOutputStream zipOutputStream = null;
        try {
            zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFile));
            zipDir(sourFile, zipOutputStream, "");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zipOutputStream.closeEntry();
                zipOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void zipDir(File sourFile, ZipOutputStream zipOutputStream, String baseDirStr) throws IOException {
        if (sourFile.isDirectory()) {
            File[] childFileList = sourFile.listFiles();
            if (childFileList.length == 0) {
                ZipEntry zipEntry = new ZipEntry(baseDirStr + sourFile.getName() + "/");
                zipOutputStream.putNextEntry(zipEntry);
            } else {
                for (File childFile : childFileList) {
                    zipDir(childFile, zipOutputStream, baseDirStr + sourFile.getName() + File.separator);
                }
            }
        } else {
            zipFile(sourFile, zipOutputStream, baseDirStr + sourFile.getName());
        }
    }

    private static void zipFile(File sourFile, ZipOutputStream zipOutputStream, String baseDirFileStr) throws IOException {
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(sourFile));
            ZipEntry zipEntry = new ZipEntry(baseDirFileStr);
            zipOutputStream.putNextEntry(zipEntry);

            byte[] buff = new byte[BUFF_SIZE];
            while (true) {
                int readNum = bufferedInputStream.read(buff);
                if (readNum <= 0) {
                    break;
                }
                zipOutputStream.write(buff, 0, readNum);
            }
        } finally {
            bufferedInputStream.close();
        }
    }

    public static void unzip(String zipFilePathStr, String unzipDirStr) {
        File zipFile = new File(zipFilePathStr);
        if (!zipFile.exists()) {
            throw new IllegalArgumentException("压缩文件不存在");
        }
        if (!unzipDirStr.endsWith("/") || !unzipDirStr.endsWith("\\") || !unzipDirStr.endsWith(File.separator)) {
            unzipDirStr = unzipDirStr + File.separator;
        }
        File unzipDir = new File(unzipDirStr);
        if (!unzipDir.exists() || !unzipDir.isDirectory()) {
            unzipDir.mkdirs();
        }
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = new ZipInputStream(new FileInputStream(zipFile));
            while (true) {
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                if (zipEntry == null) {
                    break;
                }
                String fileName = zipEntry.getName();
                File file = new File(unzipDirStr + fileName);
                if (zipEntry.isDirectory()) {
                    file.mkdirs();
                } else {
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    byte[] buff = new byte[BUFF_SIZE];
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(file);
                        while (true) {
                            int readNum = zipInputStream.read(buff);
                            if (readNum <= 0) {
                                break;
                            }
                            fileOutputStream.write(buff, 0, readNum);
                        }
                    } finally {
                        fileOutputStream.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                zipInputStream.closeEntry();
                zipInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) throws FileNotFoundException {

    }

}
