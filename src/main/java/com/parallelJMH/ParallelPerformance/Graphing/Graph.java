package com.parallelJMH.ParallelPerformance.Graphing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Graph {

    public static void main(String[] args) throws IOException {
        File jsonFile = new File("BenchmarkResults.json");

        List<List<List<Double>>> rawData = getBenchmarkData(jsonFile);
        for (List<List<Double>> dataSet : rawData) {
            System.out.println("Raw Data: " + dataSet);
        }

        createHashMapLineChart(rawData);
    }

    static List<List<List<Double>>> getBenchmarkData(File jsonFile) {
        try {
            // Step 1: Initialize ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Step 2: Read JSON file into an array of BenchmarkResult objects
            BenchmarkResult[] results = objectMapper.readValue(jsonFile, BenchmarkResult[].class);

            // Step 3: Loop through the results and print the rawData
            List<List<List<Double>>> rawData = new ArrayList<>();
            for (BenchmarkResult result : results) {
                System.out.println("Benchmark: " + result.getBenchmark());
                rawData.add(result.getPrimaryMetric().getRawData());
            }
            return rawData;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    static void createHashMapLineChart(List<List<List<Double>>> rawData) {
        DefaultCategoryDataset hashMapDataSet = new DefaultCategoryDataset();
        DefaultCategoryDataset skipListDataSet = new DefaultCategoryDataset();
        ArrayList<DefaultCategoryDataset> dataSets = new ArrayList<>();
        dataSets.add(hashMapDataSet);
        dataSets.add(skipListDataSet);
        for (List<List<Double>> dataSet : rawData) {
            for (int i = 0; i < dataSet.size(); i++ ) {
                for (int j = 0; j < dataSet.get(i).size(); j++) {
                    dataSets.get(i).addValue(
                            dataSet.get(i).get(j), "Iterations", dataSet.get(i).get(j));
                }
            }
        }


//         Title, X-Axis, Y-Axis, Dataset
        JFreeChart hashMapLineChart = ChartFactory.createLineChart(
                "Average Runtime for Hashmap Read Mostly Dataset",
                "Iteration",
                "Millisecond/Operation",
                dataSets.get(0)
        );

        try {
            File imageFile = new File("TESTCHART.png");
            ChartUtils.saveChartAsPNG(imageFile, hashMapLineChart, 800, 600);
            System.out.println("Line chart saved as hashMapLineChart.png");
        } catch (IOException e) {
            System.err.println("Error saving chart: " + e.getMessage());
        }
    }
}
