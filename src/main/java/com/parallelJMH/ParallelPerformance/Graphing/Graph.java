package com.parallelJMH.ParallelPerformance.Graphing;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The Graph class reads benchmark data from a JSON file created by Benchmark
 * and creates line charts based on this data.
 */
public class Graph {

    public static void main(String[] args) throws IOException {
        File jsonFile = new File("jmh-result.json");

        List<List<List<Double>>> rawData = getBenchmarkData(jsonFile);
        for (List<List<Double>> dataSet : rawData) {
            System.out.println("Raw Data: " + dataSet);
        }

        createHashMapLineChart(rawData);
    }

    static List<List<List<Double>>> getBenchmarkData(File jsonFile) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            BenchmarkResult[] results = objectMapper.readValue(jsonFile, BenchmarkResult[].class);
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
        // Create XYSeries for HashMap and SkipList
        XYSeries hashMapXY = new XYSeries("HashMap");
        XYSeries skipListXY = new XYSeries("SkipList");

        int index = 0;
        boolean completed = false;
        int iterationIndex = 0;
        int temp = 0;

        // This is a simple way to determine iteration count (make sure it works for your data)
        for (List<List<Double>> dataSet : rawData) {
            for (List<Double> data : dataSet) {
                iterationIndex = data.size() * dataSet.size();
                break;
            }
            break;
        }
        temp = iterationIndex;  // Store for later use

        for (List<List<Double>> dataSet : rawData) {
            for (int i = 0; i < dataSet.size(); i++) {
                for (int j = 0; j < dataSet.get(i).size(); j++) {
                    if (iterationIndex == 0) {
                        iterationIndex = temp;
                        completed = true;
                    }

                    // Add data to respective series (HashMap or SkipList)
                    if (!completed) {
                        hashMapXY.add(iterationIndex, dataSet.get(i).get(j));
                    } else {
                        skipListXY.add(iterationIndex, dataSet.get(i).get(j));
                    }
                    iterationIndex--;
                }
            }
        }

        // Create XYSeriesCollections for HashMap and SkipList charts
        XYSeriesCollection hashMapDataset = new XYSeriesCollection(hashMapXY);
        XYSeriesCollection skipListDataset = new XYSeriesCollection(skipListXY);

        // Create HashMap and SkipList Line charts using XYSeriesCollection
        JFreeChart hashMapLineChart = ChartFactory.createXYLineChart(
                "Average Runtime for Hashmap Read Mostly Dataset",
                "Iteration",
                "Millisecond/Operation",
                hashMapDataset,
                PlotOrientation.VERTICAL,
                true,  // Show legend
                true,  // Show tooltips
                false  // Do not generate URLs
        );

        JFreeChart skipListLineChart = ChartFactory.createXYLineChart(
                "Average Runtime for SkipList Read Mostly Dataset",
                "Iteration",
                "Millisecond/Operation",
                skipListDataset,
                PlotOrientation.VERTICAL,
                true,  // Show legend
                true,  // Show tooltips
                false  // Do not generate URLs
        );

        // Create a combined XYLine chart (both HashMap and SkipList in one chart)
        XYSeriesCollection datasetXY = new XYSeriesCollection();
        datasetXY.addSeries(hashMapXY);
        datasetXY.addSeries(skipListXY);

        // Create combined chart with both HashMap and SkipList
        JFreeChart combinedXYLineChart = ChartFactory.createXYLineChart(
                "Average Runtime for SkipList and HashMap Read Mostly Dataset",
                "Iteration",
                "Millisecond/Operation",
                datasetXY,
                PlotOrientation.VERTICAL,
                true,  // Show legend
                true,  // Show tooltips
                false  // Do not generate URLs
        );

        // Customize the plot for the combined chart
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.RED);  // Color for HashMap
        renderer.setSeriesPaint(1, Color.BLUE); // Color for SkipList
        combinedXYLineChart.getXYPlot().setDomainPannable(true);
        combinedXYLineChart.getXYPlot().setRangePannable(true);
        combinedXYLineChart.getXYPlot().setDomainGridlinePaint(Color.lightGray);
        combinedXYLineChart.getXYPlot().setRangeGridlinePaint(Color.lightGray);
        combinedXYLineChart.getXYPlot().setRenderer(renderer);

        // Save the charts as PNG files
        try {
            File hashMapImage = new File("src/main/resources/static/HashMap.png");
            File skipListImage = new File("src/main/resources/static/SkipList.png");
            File xyImage = new File("src/main/resources/static/xyImage.png");

            ChartUtils.saveChartAsPNG(hashMapImage, hashMapLineChart, 800, 600);
            ChartUtils.saveChartAsPNG(skipListImage, skipListLineChart, 800, 600);
            ChartUtils.saveChartAsPNG(xyImage, combinedXYLineChart, 800, 600);
        } catch (IOException e) {
            System.err.println("Error saving chart: " + e.getMessage());
        }
    }
}
