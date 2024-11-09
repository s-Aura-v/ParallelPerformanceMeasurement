package org.example;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.*;

public class Benchmark {

    @Param({"600"})
    private int numberOfLibrarians;

    @Param({"10000"})
    private int numberOfBooks = 10000;

    @Param({".96"})
    private double readProbability;

    private Library library;
    private ConcurrentHashMap<Long,Long> libraryAsHashMap;
    private CountDownLatch countDownLatch;



    public void runClass() {
        ExecutorService executorService = setup();
        for (int i = 0; i < numberOfLibrarians; i++) {
            executorService.execute(new Librarian(numberOfLibrarians, readProbability, library, libraryAsHashMap, countDownLatch));
        }
        executorService.shutdown();
    }

    @Setup
    private ExecutorService setup() {
        CountDownLatch countDownLatch = new CountDownLatch(numberOfLibrarians); // used to separate the two ways of running this program
        SkipList skipList = JSONReader.generateRandomBooksSkipList(numberOfBooks);
        ConcurrentHashMap<Long, Long> hashMap = JSONReader.generateRandomBooksHashMap(numberOfBooks);
        Library library = new Library(skipList);


        ExecutorService executorService = Executors.newFixedThreadPool(numberOfLibrarians);
        return Executors.newFixedThreadPool(numberOfLibrarians);

    }
}

