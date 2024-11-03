package com.performance_measurement.perforamancemeasurement.Classes;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tester {
    public static void main(String[] args) throws IOException {
        File jsonFile = new File("src/main/resources/database/short_BookList.json");
        SkipList skipList = JSONReader.convertJSON(jsonFile);

        int numberOfLibrarians = 10;
        double readProbability = .96;
        CountDownLatch countDownLatch = new CountDownLatch(numberOfLibrarians); // used to separate the two ways of running this program
        Library library = new Library(skipList);

        ExecutorService executorService = Executors.newFixedThreadPool(numberOfLibrarians);
        for (int i = 0; i < numberOfLibrarians; i++) {
            executorService.execute(new Librarian(numberOfLibrarians, readProbability));
        }
        executorService.shutdown();


    }
}
