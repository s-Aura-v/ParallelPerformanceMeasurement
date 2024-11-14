# Parallel Performance Measurement
## Purpose
In this task, we are examining a read-mostly database with two data stuructures: skiplist and concurrentHashMap. After simulating a read-mostly database, we use Java Microbenchmark Harness (JMH) to test the efficiency of each method. 

## Tools/Skills Used
1. Java Concurrency 
2. Read/Write Lock with an emphasis on reading
3. JMH 
4. JFreeChart
5. Maven

## Program Results
With 5 forks, 5 warmup iterations, and 20 iterations with 10000 random read/write requests, the results are as follows:
HashMap Result:

![Graph depicting how HashMap runtime stayed around 72 ms/operations](/src/main/resources/static/HashMap.png)


SkipList Result:

![Graph depicting how SkipList runtime fluctuated from 2600-3600 ms/operations](/src/main/resources/static/HashMap.png)

Results Merged: 

![Graph combining the two graphs together](/src/main/resources/static/xyImage.png)


## Run Guide
1. Run The Benchmark
    1. java -jar target/benchmarks.jar Benchmark -f 2 -wi 5 -i 5 -rf json
2. Create the Graphs
    1. java -jar target/Graph.jar
3. View the Graph on LocalHost Website
    1. You can run it as an active process by
        1. java -jar target/runWebsite.jar
    2. You can also run it as a background process by doing
        1. nohup /home/dl/linux/jdk/bin/java -jar target/runWebsite.jar &
        2. To end this, run
            1. ps aux | grep java to find the process id
            2. kill [processid]


## Resources:
1. https://gee.cs.oswego.edu/dl/csc375/a2.html
2. https://jenkov.com/tutorials/java-performance/jmh.html
3. https://mkyong.com/java/java-jmh-benchmark-tutorial/
