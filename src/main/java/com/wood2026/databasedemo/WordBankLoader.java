package com.wood2026.databasedemo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class WordBankLoader {

    public static List<String> loadWords(String filePath) {
        Gson gson = new Gson();

        // Use try-with-resources to ensure the file reader closes automatically
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            // Define the target type: List<String>
            Type listType = new TypeToken<List<String>>() {}.getType();

            // Parse the JSON array directly into your Java List
            return gson.fromJson(reader, listType);

        } catch (IOException e) {
            System.err.println("Could not read the word bank file: " + e.getMessage());
            return List.of(); // Return an empty list if loading fails
        }
    }
}
