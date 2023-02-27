package com.cleaner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import picocli.CommandLine;

import java.util.Arrays;
import java.util.concurrent.Callable;

@picocli.CommandLine.Command(
        name = "ditasdlcleaner.jar",
        description = "Rename files from input directory and move it to output directory")

public class Main implements Callable<Integer> {

    private static Logger log = LogManager.getLogger(Main.class.getName());
    private static Rename rename;

    @CommandLine.Option(names = {"-f", "--from"}, description = "Path to the DIR from where we rename files")
    private String fromFilePath;

    @CommandLine.Option(names = {"-t", "--to"}, description = "Path to the DIR where to write files with updated names")
    private String toFilePath;

    public static void main(String[] args){
        log.info("Command line arguments: {}", Arrays.asList(args));
        new CommandLine(new Main()).execute(args);
    }

    @Override
    public Integer call(){
        rename = new Rename(fromFilePath,toFilePath);
        rename.rename();
        return 0;
    }
}
