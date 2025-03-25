package main;

import java.util.Objects;

public class BookCopy {
	private static int idCounter = 1; // for auto unique Id generation
    private final int copyId;
    private final Book book;
    private boolean isBorrowed;

    public BookCopy(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null.");
        }
        this.copyId = idCounter++;
        this.book = book;
        this.isBorrowed = false;
    }

    public int getCopyId() {
        return copyId;
    }

    public Book getBook() {
        return book;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void markAsBorrowed() {
        if (isBorrowed) {
            throw new IllegalStateException("Book copy is already borrowed.");
        }
        isBorrowed = true;
    }

    public void markAsReturned() {
        if (!isBorrowed) {
            throw new IllegalStateException("Book copy is not borrowed.");
        }
        isBorrowed = false;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BookCopy bookCopy = (BookCopy) obj;
        return copyId == bookCopy.copyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(copyId);
    }

    @Override
    public String toString() {
        return String.format("BookCopy[ID: %d, Book: %s, Borrowed: %b]", copyId, book.getTitle(), isBorrowed);
    }
}
