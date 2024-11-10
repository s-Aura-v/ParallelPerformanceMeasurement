package com.parallelJMH.ParallelPerformance;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class JSONReader {
    static int ID_MAX = 10000;

    static SkipList convertJSON(File jsonFile) throws IOException {
        SkipList skipList = new SkipList();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(jsonFile);

        for (JsonNode book : root) {
            long isbnID = book.get("isbnID").asLong();
            skipList.insert(isbnID);
        }

        return skipList;
    }

    static SkipList generateRandomBooksSkipList(int numberOfBooks) {
        SkipList skipList = new SkipList();
        for (int i = 0; i < numberOfBooks; i++) {
            long randomID = ThreadLocalRandom.current().nextInt(0, ID_MAX);
            skipList.insert(randomID);
        }
        return skipList;
    }

    static ConcurrentHashMap<Long, Long> generateRandomBooksHashMap(int numberOfBooks) {
        ConcurrentHashMap<Long, Long> concurrentHashMap = new ConcurrentHashMap<>();
        for (int i = 0; i < numberOfBooks; i++) {
            long randomID = ThreadLocalRandom.current().nextInt(0, ID_MAX);
            concurrentHashMap.put(randomID, randomID);
        }
        return concurrentHashMap;
    }

    // tester for json reader
    public static void main(String[] args) throws IOException {
        File jsonFile = new File("src/main/resources/database/short_BookList.json");
        SkipList skipList = convertJSON(jsonFile);


        System.out.println(skipList.search(9780679732761L));
        System.out.println(skipList.search(2));
        skipList.delete(9780679732761L);
        skipList.insert(2);
        System.out.println(skipList.search(9780679732761L));
        System.out.println(skipList.search(2));


        int readProbability = ThreadLocalRandom.current().nextInt(100);
        int randomNumber = ThreadLocalRandom.current().nextInt(0, 1) * readProbability;
    }
}
