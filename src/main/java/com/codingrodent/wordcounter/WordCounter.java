package com.codingrodent.wordcounter;

import java.io.*;
import java.util.*;

import static java.util.stream.Collectors.groupingBy;

public class WordCounter {

    private final WordStreamer wordStreamer;

    /**
     * Build word counter using the default wordStreamer
     */
    public WordCounter() {
        this(simpleWordStreamer);
    }

    /**
     * Build word counter and supply a customer word streamer if needed
     *
     * @param wordStreamer Function to split a string into words. Leave as null if you want to use the default
     */
    public WordCounter(final WordStreamer wordStreamer) {
        this.wordStreamer = wordStreamer == null ? simpleWordStreamer : wordStreamer;
    }

    /**
     * Process text represented by a BufferedReader and generated a word count
     * sorted by the number of occurrences starting with the most frequent word.
     *
     * @param reader Wrapper for source text
     * @return List of words and their associated counts, starting with the most frequent
     * @throws IOException Buffer was null
     */
    public List<Map.Entry<String, Integer>> count(final BufferedReader reader) throws IOException {
        if (Objects.isNull(reader)) {
            throw new IOException("The buffer cannot be null!");
        }
        var totals = new HashMap<String, Integer>();
        // count everything
        reader.lines()
                .map(String::trim)
                .flatMap(wordStreamer)
                .filter(element -> !element.isBlank())
                .forEach(word -> totals.compute(word, (k, v) -> (v == null) ? 1 : ++v));

        // Group words by counts, descending order
        var byValueGroup = new ArrayList<>(totals.entrySet()).stream().collect(groupingBy(Map.Entry::getValue));
        var keyList = new ArrayList<>(byValueGroup.keySet());
        keyList.sort(Comparator.reverseOrder());
        //
        // sort each group internally to guarantee a consistent output
        // then add to a list to enforce order and return
        return keyList.stream()
                .map(byValueGroup::get)
                .peek(v -> v.sort(Map.Entry.comparingByKey()))
                .flatMap(Collection::stream)
                .toList();
    }

    // simplistic function to break up a line of text into individual words using spaces as
    // the break character. Ignores periods, commas and is case-insensitive. You get everything else !
    //
    // depending on the source material you may want to take into account other things such as:
    //
    // 1. contractions aren't -> are not
    // 2. various non alpha characters ( ) ? numerics etc, e.g. (something) -> something
    // 3. non english languages
    // 4. words are tricky things ...
    public final static WordStreamer simpleWordStreamer = s -> Arrays.stream(s.replaceAll("[.,]", " ")
                    .split(" "))
            .map(String::toLowerCase);

}
