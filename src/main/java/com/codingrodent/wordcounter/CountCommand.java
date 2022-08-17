package com.codingrodent.wordcounter;

import picocli.CommandLine;

import java.io.*;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "wordcount", mixinStandardHelpOptions = true, version = "wordcount 0.1",
        description = "Prints word counts for a file to STDOUT.")
public class CountCommand implements Callable<Integer> {
    @CommandLine.Parameters(index = "0", description = "The file to count words from")
    private File file;

    @Override
    public Integer call() {
        try {
            if (!file.exists()) {
                System.out.println("Unable to find file: " + file.getAbsolutePath());
                return 1;
            }
            var reader = new BufferedReader(new FileReader(file));
            var wordCounter = new WordCounter();
            var result = wordCounter.count(reader);
            if (result.isEmpty()) {
                System.out.println("No data in file");
                return 1;
            } else {
                result.forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return 1;
        }
        return 0;
    }
}
