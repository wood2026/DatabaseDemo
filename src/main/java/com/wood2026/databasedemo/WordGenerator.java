package com.wood2026.databasedemo;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class WordGenerator {
    private static final List<String> words = WordBankLoader.loadWords(
            "/com/wood2026/databasedemo/index.json");
    private static final Random random = new Random();

    public static String getRandomWord() {
        return words.get(random.nextInt(words.size()));
    }

    public static List<String> getUniqueRandomWords(int count) {
        if (count < 0 || count > words.size()) {
            throw new IllegalArgumentException("Invalid word count: " + count);
        }
        Set<String> selected = new HashSet<>();
        while (selected.size() < count) {
            selected.add(getRandomWord());
        }
        return List.copyOf(selected);
    }
}
