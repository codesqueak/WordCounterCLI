package com.codingrodent.wordcounter;

import picocli.CommandLine;

public class Main {
    public static void main(String[] args) {
        int exitCode = new CommandLine(new CountCommand()).execute(args);
        System.exit(exitCode);
    }

}