package com.parallelJMH.ParallelPerformance;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
public class Benchmark {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Benchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Param({"600"})
    private int numberOfLibrarians;

    @Param({"10000"})
    private int numberOfBooks;

    @Param({".96"})
    private double readProbability;

    private Library library;
    private ConcurrentHashMap<Long,Long> libraryAsHashMap;

    @org.openjdk.jmh.annotations.Benchmark
    public void runSkipListBenchmark() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(numberOfLibrarians)) {
            for (int i = 0; i < numberOfLibrarians; i++) {
                executorService.execute(new SkipListLibrarian(numberOfLibrarians, readProbability, library, libraryAsHashMap));
            }
            executorService.shutdown();
        }
    }

    @org.openjdk.jmh.annotations.Benchmark
    public void runHashMapBenchmark() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(numberOfLibrarians)) {
            for (int i = 0; i < numberOfLibrarians; i++) {
                executorService.execute(new HashMapLibrarian(numberOfLibrarians, readProbability, library, libraryAsHashMap));
            }
            executorService.shutdown();
        }
    }

    @Setup
    public void setupBenchmark() {
        SkipList skipList = JSONReader.generateRandomBooksSkipList(numberOfBooks);
        libraryAsHashMap = JSONReader.generateRandomBooksHashMap(numberOfBooks);
        library = new Library(skipList);
    }

}


