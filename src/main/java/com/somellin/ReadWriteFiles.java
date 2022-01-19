package com.somellin;

import com.somellin.exceptions.MyException;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ReadWriteFiles {

    private final List<File> inputFiles;
    private final File outputFile;

    public ReadWriteFiles(List<String> inputFileNames, String outputFileName) {
        this.inputFiles = new ArrayList<>(inputFileNames.size());

        for (String filename : inputFileNames) {
            inputFiles.add(new File(filename));
        }
        this.outputFile = new File(outputFileName);
        log.info("Files is init");
    }

    public void readFile(boolean isString) {
        incorrectRemove(isString);

        for (File file : inputFiles) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
                System.out.println(file);
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    System.out.println(str);
                }
            } catch (IOException e) {
                log.warn("File not found Exception");
                System.out.println("Enter the correct path to the file");

            }
        }
    }

//    public void writeFile() {
        //TODO writing answer
//    }

    private boolean correctCheck(File file, boolean isString) {
        boolean res = false;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String str;
            int a = Integer.MIN_VALUE;
            String s = "";

            while ((str = bufferedReader.readLine()) != null) {
                if (!isString) {
                    if (!isDigit(str)) {
                        log.warn("File :" + file +" words in the file, try -s\n");
                        throw new MyException("Words in the file, Try -s ");
                    }
                    if (a > Integer.parseInt(str)) {
                        log.warn("File :" + file +" not sorted\n");
                        throw new MyException("File not sorted");
                    }
                    a = Integer.parseInt(str);
                } else {
                    if (isDigit(str)) {
                        log.warn("File :" + file +" numbers in the file, try -i\n");
                        throw new MyException("Words in the file, Try -i ");
                    }
                    if (str.compareTo(s) < 0) {
                        log.warn("File :" + file +" not sorted\n");
                        throw new MyException("File not sorted");
                    }
                    s = str;
                }
            }
            res = true;
        } catch (IOException e) {
            log.warn("File not found Exception");
        } catch (MyException e) {
            log.warn(e.getMessage());
        }
        return res;
    }

    private static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void incorrectRemove(boolean isString){
        List<File> incorrectFiles = new ArrayList<>();
        for(File file : inputFiles){
            System.out.println(file);
            System.out.println(correctCheck(file,isString));
            if (!correctCheck(file,isString)){
                incorrectFiles.add(file);
            }
        }

        System.out.println(incorrectFiles);
        for(File fl : incorrectFiles){
            inputFiles.remove(fl);
        }
    }

    @Override
    public String toString() {
        return "ReadWriteFiles{" +
                "inputFiles=" + inputFiles +
                ", outputFile=" + outputFile +
                '}';
    }
}
