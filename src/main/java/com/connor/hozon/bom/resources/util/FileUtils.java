package com.connor.hozon.bom.resources.util;

import java.io.*;

public class FileUtils {

    public static byte[] readFileToByteArray(File file) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int len = -1;
        while((len = bis.read(b)) != -1) {
            bos.write(b, 0, len);
        }
        byte[] fileByte = bos.toByteArray();
        bos.flush();
        bos.close();
        bis.close();
        return fileByte;
    }
}
