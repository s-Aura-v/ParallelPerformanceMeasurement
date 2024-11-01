package com.performance_measurement.perforamancemeasurement.Classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class JSONReader {

    static SkipList convertJSON(File jsonFile) throws IOException {
        SkipList skipList = new SkipList();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(jsonFile);

        for (JsonNode book : root) {
            long isbnID = book.get("isbnID").asLong();
            skipList.insert(isbnID);
        }

        System.out.println(skipList.search(9780679732761L));
        System.out.println(skipList.search(2));
        skipList.delete(9780679732761L);
        skipList.insert(2);
        System.out.println(skipList.search(9780679732761L));
        System.out.println(skipList.search(2));
        return skipList;
    }

    // tester for json reader
    public static void main(String[] args) throws IOException {
        File jsonFile = new File("src/main/resources/database/short_BookList.json");
    }
}
