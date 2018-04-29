package com.FileContent;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileContents {
    public static void main(String[] args) throws IOException {
        File file1 = new File("D:\\IntelliJ\\GitDemo\\CoreJava\\com\\FileContent\\hi.txt");
        File file2 = new File("D:\\IntelliJ\\GitDemo\\CoreJava\\com\\FileContent\\bi.txt");
        boolean isTwoEqual = FileUtils.contentEquals(file1, file2);

        System.out.println("output is : " + isTwoEqual);
    }
}
