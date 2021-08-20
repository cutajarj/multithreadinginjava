package com.cutajarjames.multithreading.creatingProcesses;

import java.io.IOException;

public class StartProcess {
    public static void main(String[] args) throws IOException, InterruptedException {
        //Replace calc.exe with an app on your system
        var p = Runtime.getRuntime().exec("calc.exe");
        System.out.println(p.info());
    }
}
