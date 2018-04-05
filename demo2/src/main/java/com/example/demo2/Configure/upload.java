package com.example.demo2.Configure;

import java.io.File;
import java.io.FileOutputStream;

public class upload {
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        System.out.println("-------------------------------");
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        System.out.println("-------------------------------"+filePath+"============"+fileName);
        out.write(file);
        out.flush();
        out.close();
    }
}
