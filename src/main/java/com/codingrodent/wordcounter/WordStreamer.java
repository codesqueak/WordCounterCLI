package com.codingrodent.wordcounter;


import java.util.function.Function;
import java.util.stream.Stream;

// define a custom function compliant with this interface to suit the language you want to
// break into words
public interface WordStreamer extends Function<String, Stream<String>> {
    Stream<String> apply(String s);
}
