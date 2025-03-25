package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class LendingSystem {
    private final Map<Book, List<BookCopy>> borrowedBooks;
    private final Map<Book, Queue<Patron>> reservedBooks;
    private final InventoryManager inventoryManager;
    private final NotificationSystem notificationSystem;

    public LendingSystem(InventoryManager inventoryManager, NotificationSystem notificationSystem) {
        this.inventoryManager = inventoryManager;
        this.notificationSystem = notificationSystem;
        this.borrowedBooks = new HashMap<>();
        this.reservedBooks = new HashMap<>();
    }

    // Patron borrows a book from a specific branch
    public BookCopy borrowBook(Patron patron, int branchId, Book book) {
        BookCopy bookCopy = inventoryManager.borrowBookCopy(branchId, book);
        if (bookCopy != null) {
            borrowedBooks.computeIfAbsent(book, k -> new ArrayList<>()).add(bookCopy);
            patron.borrowBook(bookCopy);
            return bookCopy;
        }
        return null;
    }

 // Patron reserves a book if unavailable
    public boolean reserveBook(Patron patron, int branchId, Book book) {
        int availableCopies = inventoryManager.getAvailableCopies(branchId, book);
        System.out.println("DEBUG: Available copies for '" + book.getTitle() + "' at branch " + branchId + ": " + availableCopies);

        if (availableCopies <= 0) { // Allow reservation only when no copies are available
            reservedBooks.computeIfAbsent(book, k -> new LinkedList<>()).add(patron);
            System.out.println(patron.getName() + " has reserved '" + book.getTitle() + "'.");
            return true;
        }

        System.out.println("Reservation failed: Copies are available for '" + book.getTitle() + "'.");
        return false;
    }

    // Patron cancels a reservation
    public boolean cancelReservation(Patron patron, int branchId, Book book) {
        Queue<Patron> reservationQueue = reservedBooks.get(book);
        System.out.println("Reservation queue size for '" + book.getTitle() + "': " + 
                            (reservationQueue != null ? reservationQueue.size() : 0));

        if (reservationQueue != null && reservationQueue.contains(patron)) {
            reservationQueue.remove(patron);
            System.out.println("\n"+patron.getName() + " canceled the reservation for '" + book.getTitle() + "'.");
            return true;
        }

        System.out.println("Cancellation failed: No active reservation found for '" + book.getTitle() + "'.");
        return false;
    }


    // Patron returns a borrowed book
    public void returnBook(Patron patron, int branchId, BookCopy bookCopy) {
        Book book = bookCopy.getBook();
        
        // Remove from patron's borrowed list
        patron.returnBook(bookCopy);

        // Remove from LendingSystem borrowed list
        List<BookCopy> copies = borrowedBooks.get(book);
        if (copies != null) {
            copies.remove(bookCopy);
            if (copies.isEmpty()) {
                borrowedBooks.remove(book);
            }
        }

        // Return the book to inventory
        inventoryManager.returnBookCopy(branchId, bookCopy);

        // Notify the next patron in queue if the book was reserved
        if (reservedBooks.containsKey(book) && !reservedBooks.get(book).isEmpty()) {
            Patron nextPatron = reservedBooks.get(book).poll();
            if (nextPatron != null) {
                notifyPatron(nextPatron, "Book available: " + book.getTitle());
            }
        }
    }

    // Sends notifications to patrons
    private void notifyPatron(Patron patron, String message) {
        notificationSystem.notifyPatron(patron, message);
    }
     
    // All Reservations
    public Queue<Patron> getReservations(Book book) {
        return reservedBooks.getOrDefault(book, new LinkedList<>());
    }
}
