package com.codingrodent.wordcounter;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class WordCounterTest {


    @Test
    void noDataTest() throws Exception {
        var wordCounter = new WordCounter();
        var result = wordCounter.count(new BufferedReader(new StringReader("")));
        assertTrue(result.isEmpty());
    }

    @Test
    void oneLineTest() throws Exception {
        var wordCounter = new WordCounter();
        var result = wordCounter.count(new BufferedReader(new StringReader("aa   bb  cc  BB  CC cc")));
        assertEquals(List.of(Map.entry("cc", 3), Map.entry("bb", 2), Map.entry("aa", 1)), result);
    }

    @Test
    void twoLineTest() throws Exception {
        var wordCounter = new WordCounter();
        var result = wordCounter.count(new BufferedReader(new StringReader("aa  AA bb bb.   \n  Cc  cc  CC cC aa dd dd")));
        assertEquals(List.of(Map.entry("cc", 4), Map.entry("aa", 3), Map.entry("bb", 2), Map.entry("dd", 2)), result);
    }


    @Test
    void verifyStaticOrdering() throws Exception {
        var wordCounter = new WordCounter();
        var text1 = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
                tempor incididunt ut labore et dolore magna aliqua. Non sodales neque
                sodales ut. Gravida arcu ac tortor dignissim convallis aenean et tortor.
                """;
        var text2 = """
                sodales ut. Gravida arcu ac tortor dignissim convallis aenean et tortor.
                tempor incididunt ut labore et dolore magna aliqua. Non sodales neque
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod
                """;
        var result1 = wordCounter.count(new BufferedReader(new StringReader(text1)));
        var result2 = wordCounter.count(new BufferedReader(new StringReader(text2)));
        assertEquals(result1, result2);
    }

    @Test
    void badReaderTest() {
        var wordCounter = new WordCounter();
        assertThrows(IOException.class, () -> wordCounter.count(null));
    }

    @Test
    void twoLineTesCustomWordStreamer() throws Exception {
        var wordCounter = new WordCounter(caseSensitiveSimpleWordStreamer);
        var result = wordCounter.count(new BufferedReader(new StringReader("aa AA AA aa aa")));
        assertEquals(List.of(Map.entry("aa", 3), Map.entry("AA", 2)), result);
    }

    public final static WordStreamer caseSensitiveSimpleWordStreamer = s ->
            Arrays.stream(s.replace(".", " ").split(" "));


}