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
    static List<String> inputFileNames = new ArrayList<>();

    public static void main(String[] args) throws IOException {

        new ParseCommandLine(args).parse();

        ReadWriteFiles RWFiles = new ReadWriteFiles(inputFileNames,outputFileName);
        System.out.println(RWFiles);

        RWFiles.readFile(isStrings);

    }
}
