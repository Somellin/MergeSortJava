package com.somellin;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Launcher {
    static boolean isAscending = true;
    static boolean isStrings = true;
    static String outputFileName = "";
    static String encoding = "utf-8";
    static List<String> inputFileNames = new ArrayList<>();

    public static void main(String[] args) {

        new ParseCommandLine(args).parse();

        System.out.println(isAscending);
        System.out.println(isStrings);
        System.out.println(outputFileName);
        System.out.println(inputFileNames);
    }
}
