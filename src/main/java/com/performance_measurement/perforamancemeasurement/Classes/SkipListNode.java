package com.performance_measurement.perforamancemeasurement.Classes;

public class SkipListNode {
    long value;
    SkipListNode[] forward; // array to hold references to different levels

    public SkipListNode(long value, int level) {
        this.value = value;
        this.forward = new SkipListNode[level + 1]; // level + 1 because level is 0-based
    }
}
