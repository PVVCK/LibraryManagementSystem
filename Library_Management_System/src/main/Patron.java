package main;

import java.util.HashSet;
import java.util.Set;

public class Patron {
    private final int patronId;
    private final String name;
    private final PatronType patronType;
	private final Set<BookCopy> borrowedBooks; // tracking borrowed books
    private final Set<Book> reservedBooks; // tracking reserved books

    public Patron(int patronId, String name, PatronType patronType) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Patron name cannot be null or empty.");
        }
        this.patronId = patronId;
        this.name = name;
        this.patronType = patronType;
        this.borrowedBooks = new HashSet<>();
        this.reservedBooks = new HashSet<>();
    }

    public int getPatronId() {
        return patronId;
    }

    public String getName() {
        return name;
    }

    public Set<BookCopy> getBorrowedBooks() {
        return borrowedBooks;
    }

    public Set<Book> getReservedBooks() {
        return reservedBooks;
    }

    // Patron borrows a book
    public void borrowBook(BookCopy bookCopy) {
        borrowedBooks.add(bookCopy);
    }

    // Patron returns a book
    public void returnBook(BookCopy bookCopy) {
        borrowedBooks.remove(bookCopy);
    }

    //  Patron reserves a book
    public boolean reserveBook(Book book) {
        return reservedBooks.add(book);
    }

    // Patron cancels a reservation
    public boolean cancelReservation(Book book) {
        return reservedBooks.remove(book);
    }
    
    public PatronType getPatronType() {
		return patronType;
	}
}
