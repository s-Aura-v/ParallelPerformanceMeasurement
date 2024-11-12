package com.parallelJMH.ParallelPerformance;

/**
 * The Library class represents a collection of books managed using a SkipList.
 * Concurrent access to the collection is handled using read and write locks.
 */
public class Library {
    private final SkipList bookCollection;

    public Library(SkipList bookCollection) {
        this.bookCollection = bookCollection;
    }

    public void addBook(long isbnID) {
        LibraryLock.writeLock();
        try {
            bookCollection.insert(isbnID);
        } finally {
            LibraryLock.unlockWrite();
        }
    }

    public boolean readBook(long isbnID) {
        LibraryLock.readLock();
        try {
            return bookCollection.search(isbnID);
        } finally {
            LibraryLock.unlockRead();
        }
    }

    public void removeBook(long isbnID) {
        LibraryLock.writeLock();
        try {
            bookCollection.delete(isbnID);
        } finally {
            LibraryLock.unlockWrite();
        }
    }
}
