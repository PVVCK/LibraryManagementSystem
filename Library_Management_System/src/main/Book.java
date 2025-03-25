package main;

import java.util.Objects;

public class Book {

	private final int bookId;
    private final String title;
    private final String author;
    private final String isbn;
    private final int publicationYear;

    public Book(int bookId, String title, String author, String isbn, int publicationYear) {
        if (title == null || title.isEmpty() || author == null || author.isEmpty() || isbn == null || isbn.isEmpty()) {
            throw new IllegalArgumentException("Title, author, and ISBN cannot be null or empty.");
        }
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getBookDetails() {
        return String.format("Book[ID: %d, Title: %s, Author: %s, ISBN: %s, Year: %d]", bookId, title, author, isbn, publicationYear);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }
}
