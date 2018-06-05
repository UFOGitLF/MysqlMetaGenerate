package com.utils;

import java.io.*;


/**
 * @author liufei
 */
public class FileUtils {

    public static void writeToFile(String path, String content) throws IOException {
        BufferedWriter fw = null;
        try {
            File file = new File(path);
            file.getParentFile().mkdirs();
            if (file.exists()) {
                file.delete();
                file.createNewFile();
            }
            fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "UTF-8"));
            fw.write(content);
            fw.flush(); // 全部写入缓存中的内容
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        int num = 100;
        System.out.print(num | (1 << 0));
    }
}
