package com.performance_measurement.perforamancemeasurement.Classes;

public class Book {
    private String name;
    private String author;
    private int book_id;

    public Book(String name, String author, int book_id) {
        this.name = name;
        this.author = author;
        this.book_id = book_id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getBook_id() {
        return book_id;
    }

}
