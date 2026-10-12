package com.wood2026.databasedemo;

import java.util.List;
import java.util.Random;

public class WordGenerator {
    private static final List<String> words = WordBankLoader.loadWords("src/main/resources/com/wood2026/databasedemo/index.json");
    private static final Random random = new Random();

    public static String getRandomWord() {
        int randomIndex = random.nextInt(words.size());
        return words.get(randomIndex);
    }
}