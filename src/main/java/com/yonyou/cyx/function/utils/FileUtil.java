package com.yonyou.cyx.function.utils;

import java.io.File;
import java.util.List;

/**
 * @description:
 * @author: lijun
 * @create: 2020-03-19 13:00:14
 **/
public class FileUtil {

    public static List<String> getFileList(String strPath, List<String> fileList) {
        File dir = new File(strPath);
        // 该文件目录下文件全部放入数组
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                // 判断是文件还是文件夹
                if (files[i].isDirectory()) {
                    // 获取文件绝对路径
                    getFileList(files[i].getAbsolutePath(), fileList);
                } else {
                    String strFileName = files[i].getAbsolutePath();
                    if (strFileName.endsWith(".java")) {
                        System.out.println("---" + strFileName);
                        fileList.add(strFileName);
                    }
                }
            }
        }
        return fileList;
    }
}
