package com.somellin;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.cli.*;

import java.util.List;

@Slf4j
public class ParseCommandLine {

    private String[] args;

    public ParseCommandLine(String... args) {
        this.args = args;
    }

    private static void usagePrint(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("java -jar mergeSort.jar [OPTIONS] output.file input.files...\n" +
                "output.file  Mandatory file name with the sort result.\n" +
                "input.files  One or more input files.\n", options);
    }

    private static void usagePrintAndShutdown(Options options, int status) {
        usagePrint(options);
        System.exit(status);
    }

    void parse() {
        Options options = new Options();
        options.addOption("s", false, "The files contain lines. Mandatory, mutually exclusive with -i.");
        options.addOption("i", false, "The files contain integers. Mandatory, mutually exclusive with -s.");
        options.addOption("a", false, "Sort Ascending. Applied by default if no -a or -d.");
        options.addOption("d", false, "Descending sort. The option is not required as well as -a.");
        options.addOption("h", "help", false, "Display help.");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, args);
        } catch (UnrecognizedOptionException e) {
            log.warn("Unknown option \'{}\'", e.getOption());
            usagePrintAndShutdown(options, 1);
        } catch (ParseException e) {
            log.error("Command line parsing failed \'{}\'", e.getMessage());
            usagePrintAndShutdown(options, 2);
        }
        if (cmd == null) {
            log.error("Command line parsing failed for unknown reason");
            usagePrintAndShutdown(options, 3);
        } else {
            if (cmd.hasOption('h') || args.length == 0) {
                usagePrintAndShutdown(options, 0);
            }
            if (!(cmd.hasOption('i') || cmd.hasOption('s'))) {
                log.warn("Missing required option -s or -i");
                usagePrintAndShutdown(options, 4);
            }
            if (cmd.hasOption('i') && cmd.hasOption('s')) {
                log.warn("There must be only one option either -s or -i");
                usagePrintAndShutdown(options, 5);
            }
            if (cmd.hasOption('a') && cmd.hasOption('d')) {
                log.warn("There must be only one option either -a or -d");
                usagePrintAndShutdown(options, 6);
            }

            List<String> files = cmd.getArgList();
            if (files.size() < 2) {
                log.warn("Missing filename for result, or at least one input filename.");
                usagePrintAndShutdown(options, 7);
            }

            if (cmd.hasOption('d')) Launcher.isAscending = false;
            if (cmd.hasOption('i')) Launcher.isStrings = false;
            Launcher.outputFileName = files.get(0);
            files.remove(0);
            Launcher.inputFileNames = files;

        }
    }
}
