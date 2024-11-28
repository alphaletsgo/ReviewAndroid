package cn.isif.reviewandroid.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * 进程工具类
 */
public class ProcessCreate {
    //使用ProcessBuilder创建进程
    public static void createByProcessBuilder() throws IOException {
        Process process = new ProcessBuilder("/system/bin/ping").redirectErrorStream(true).start();
        OutputStream stdout = process.getOutputStream();
        InputStream stdin = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdin));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdout));
    }

    //使用Runtime.getRuntime().exec()
    public static void createByRuntime() throws IOException {
        Process process = Runtime.getRuntime().exec("/system/bin/ping");
        OutputStream stdout = process.getOutputStream();
        InputStream stderr = process.getErrorStream();
        InputStream stdin = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stdin));
        BufferedReader err= new BufferedReader(new InputStreamReader(stderr));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(stdout));
    }
}
