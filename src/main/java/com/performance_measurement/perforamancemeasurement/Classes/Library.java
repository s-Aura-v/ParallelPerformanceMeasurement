package com.performance_measurement.perforamancemeasurement.Classes;

public class Library {
    private final SkipList bookCollection;

    public Library(SkipList bookCollection) {
        this.bookCollection = bookCollection;
    }

    public void addBook() {
        LibraryLock.writeLock();
        try {
            bookCollection.insert(1);
        } finally {
            LibraryLock.unlockWrite();
        }
    }

    public int getIndividualBook(int bookName) {
        LibraryLock.readLock();
        try {
            bookCollection.search(bookName);
        } finally {
            LibraryLock.unlockRead();
        }
        return 1;
    }
}
