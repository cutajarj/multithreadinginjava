package com.cutajarjames.multithreading.creatingProcesses;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class StartProcessBuilder {
    public static void main(String[] args) throws IOException, InterruptedException {
        if (args.length > 0 && args[0].equals("childProcess")) {
            System.out.println("This is the child process");
            TimeUnit.SECONDS.sleep(5);
            System.out.println("Child process is done");
        } else {
            System.out.println("This is the parent process ");
            var javaCmd = System.getProperty("java.home") + "/bin/java";
            var classpath = System.getProperty("java.class.path");
            var classToRun = StartProcessBuilder.class.getCanonicalName();
            new ProcessBuilder(javaCmd, "-classpath", classpath, classToRun, "childProcess")
                    .inheritIO()
                    .start();
            TimeUnit.SECONDS.sleep(1);
            System.out.println("Parent process is done");
        }
    }
}
