package com.performance_measurement.perforamancemeasurement.Classes;

public class SkipListNode {
    int value;
    SkipListNode[] forward; // array to hold references to different levels

    public SkipListNode(int value, int level) {
        this.value = value;
        this.forward = new SkipListNode[level + 1]; // level + 1 because level is 0-based
    }
}
