package com.parallelJMH.ParallelPerformance.Graphing;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BenchmarkResult {
    private String benchmark;

    @JsonProperty("primaryMetric")
    private PrimaryMetric primaryMetric;

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public PrimaryMetric getPrimaryMetric() {
        return primaryMetric;
    }

    public void setPrimaryMetric(PrimaryMetric primaryMetric) {
        this.primaryMetric = primaryMetric;
    }
}

@JsonIgnoreProperties(ignoreUnknown = true)
class PrimaryMetric {
    private List<List<Double>> rawData;

    public List<List<Double>> getRawData() {
        return rawData;
    }

    public void setRawData(List<List<Double>> rawData) {
        this.rawData = rawData;
    }
}
