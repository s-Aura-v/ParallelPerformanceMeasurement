package com.parallelJMH.ParallelPerformance.Graphing;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeriesCollection;

/**
 * The Graph class reads benchmark data from a JSON file created by Benchmark
 * and create line charts based on this data.
 */
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
        XYSeries hashMapXY = new XYSeries( "HashMap" );
        XYSeries skipListXY = new XYSeries( "SkipList" );


        DefaultCategoryDataset hashMapDataSet = new DefaultCategoryDataset();
        DefaultCategoryDataset skipListDataSet = new DefaultCategoryDataset();
        ArrayList<DefaultCategoryDataset> dataSets = new ArrayList<>();
        dataSets.add(hashMapDataSet);
        dataSets.add(skipListDataSet);
        int index = 0;
        int iterationIndex = 0;
        for (List<List<Double>> dataSet : rawData) {
            for (int i = 0; i < dataSet.size(); i++ ) {
                for (int j = 0; j < dataSet.get(i).size(); j++) {
                    dataSets.get(index).addValue(
                            dataSet.get(i).get(j), "Iterations", "" + iterationIndex);
                    if (iterationIndex < 10) {
                        hashMapXY.add(iterationIndex, dataSet.get(i).get(j));
                    } else {
                        skipListXY.add(iterationIndex, dataSet.get(i).get(j));
                    }
                    iterationIndex++;

                }
            }
            index++;
        }

        System.out.println(dataSets.get(0));
//         Title, X-Axis, Y-Axis, Dataset
        JFreeChart hashMapLineChart = ChartFactory.createLineChart(
                "Average Runtime for Hashmap Read Mostly Dataset",
                "Iteration",
                "Millisecond/Operation",
                dataSets.get(0)
        );
        JFreeChart skipListLineChart = ChartFactory.createLineChart(
                "Average Runtime for SkipList Read Mostly Dataset",
                "Iteration",
                "Millisecond/Operation",
                dataSets.get(1)
        );

        XYSeriesCollection datasetXY = new XYSeriesCollection( );
        datasetXY.addSeries( hashMapXY );
        datasetXY.addSeries( skipListXY );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.BLUE );
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
                "Average Runtime for SkipList and Hashmap Read Mostly Dataset",
                "Iteration",
                "Millisecond/Operation",
                datasetXY,
                PlotOrientation.VERTICAL ,
                true , true , false);
        XYPlot plot = xylineChart.getXYPlot( );






        try {
            File hashMapImage = new File("HashMap.png");
            File skipListImage = new File("SkipList.png");
            File xyImage = new File("xyImage.png");
            ChartUtils.saveChartAsPNG(hashMapImage, hashMapLineChart, 800, 600);
            ChartUtils.saveChartAsPNG(skipListImage, skipListLineChart, 800, 600);
            ChartUtils.saveChartAsPNG(xyImage, xylineChart, 800, 600);
        } catch (IOException e) {
            System.err.println("Error saving chart: " + e.getMessage());
        }
    }
}
