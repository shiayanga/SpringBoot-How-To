package com.github.shiayanga.system;

import java.io.File;

public class SystemUtil {
    public static void main(String[] args) {
        String path = System.getProperty("user.dir");
        String jarPath = path + "/applications";
        File file = new File(path);
        boolean exists = file.exists();
        System.out.println(path);
        System.out.println(exists);
    }
}
