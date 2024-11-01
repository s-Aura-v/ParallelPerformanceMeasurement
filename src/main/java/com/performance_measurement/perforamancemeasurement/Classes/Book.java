package com.performance_measurement.perforamancemeasurement.Classes;

import lombok.Getter;

@Getter
public class Book {
    private final String name;
    private final String author;
    private final int isbnID;

    public Book(String name, String author, int isbnID) {
        this.name = name;
        this.author = author;
        this.isbnID = isbnID;
    }
}
