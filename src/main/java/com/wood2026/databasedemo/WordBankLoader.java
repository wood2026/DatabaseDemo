package com.wood2026.databasedemo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WordBankLoader {
    public static List<String> loadWords(String resourcePath) {
        try (InputStream stream = WordBankLoader.class.getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IllegalStateException("Word bank resource not found: " + resourcePath);
            }
            try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
                Type listType = new TypeToken<List<String>>() {}.getType();
                List<String> words = new Gson().fromJson(reader, listType);
                if (words == null || words.isEmpty()) {
                    throw new IllegalStateException("Word bank is empty: " + resourcePath);
                }
                return words;
            }
        } catch (IOException e) {
            throw new IllegalStateException("Could not read word bank: " + resourcePath, e);
        }
    }
}
