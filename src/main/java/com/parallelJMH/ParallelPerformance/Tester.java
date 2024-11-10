package com.parallelJMH.ParallelPerformance;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tester {
    public static void main(String[] args) throws IOException {
        int numberOfLibrarians = 600;
        int numberOfBooks = 10000;
        double readProbability = .96;
        SkipList skipList = JSONReader.generateRandomBooksSkipList(numberOfBooks);
        ConcurrentHashMap<Long, Long> hashMap = JSONReader.generateRandomBooksHashMap(numberOfBooks);
        Library library = new Library(skipList);


        ExecutorService executorService = Executors.newFixedThreadPool(numberOfLibrarians);
        for (int i = 0; i < numberOfLibrarians; i++) {
            executorService.execute(new SkipListLibrarian(numberOfLibrarians, readProbability, library, hashMap));
        }
        for (int i = 0; i < numberOfLibrarians; i++) {
            executorService.execute(new HashMapLibrarian(numberOfLibrarians, readProbability, library, hashMap));
        }
        executorService.shutdown();
    }
}
