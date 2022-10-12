package com.jiaruiblog.lucenedemo.util;

import java.io.*;
import java.nio.charset.Charset;

/**
 * file utils
 * @Author Jarrett Luo
 * @Date 2022/10/12 17:13
 * @Version 1.0
 */
public class FileUtils {

    private FileUtils() {
    }

    /**
     * 读取文件中的word
     * @param file File
     * @return String
     */
    public static String readFileToString(File file) {
        if( file == null || !file.exists()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        try (
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, Charset.defaultCharset());
            BufferedReader br = new BufferedReader(isr);
            ){
            String line = "";
            while((line = br.readLine()) != null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * file size
     * @param file File
     * @return long
     */
    public static long sizeOf(File file) {
        if( file == null || !file.exists()) {
            return 0L;
        }
        return file.length();
    }
}
