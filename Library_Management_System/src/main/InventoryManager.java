package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryManager {
    private final Map<Integer, Map<Book, List<BookCopy>>> branchInventory;

    public InventoryManager() {
        this.branchInventory = new HashMap<>();
    }

    
    public void addBookCopy(int branchId, Book book, int copies) {
        branchInventory.putIfAbsent(branchId, new HashMap<>());
        Map<Book, List<BookCopy>> branchBooks = branchInventory.get(branchId);

        branchBooks.putIfAbsent(book, new ArrayList<>());

        List<BookCopy> copiesList = branchBooks.get(book);
        for (int i = 0; i < copies; i++) {
            copiesList.add(new BookCopy(book)); // Creating unique BookCopy objects
        }
    }


    // Removes books from a specific branch
    public boolean returnBookCopy(int branchId, BookCopy bookCopy) {
        if (bookCopy == null || !bookCopy.isBorrowed()) {
            return false;
        }

        bookCopy.markAsReturned();
        branchInventory.get(branchId).computeIfAbsent(bookCopy.getBook(), k -> new ArrayList<>()).add(bookCopy); // Add back to available copies
        return true;
    }

    // Transfers books between branches
    public boolean transferBook(int fromBranchId, int toBranchId, Book book, int copies) {
        if (!branchInventory.containsKey(fromBranchId) || !branchInventory.get(fromBranchId).containsKey(book)) {
            System.out.println("Book not found in source branch " + fromBranchId);
            return false;
        }

        List<BookCopy> fromBranchCopies = branchInventory.get(fromBranchId).get(book);
        
        if (fromBranchCopies.size() < copies) {
            System.out.println("Not enough copies in source branch " + fromBranchId);
            return false;
        }

        // Remove copies from the source branch
        List<BookCopy> copiesToTransfer = new ArrayList<>();
        for (int i = 0; i < copies; i++) {
            copiesToTransfer.add(fromBranchCopies.remove(fromBranchCopies.size() - 1));
        }

        // Add copies to the destination branch
        branchInventory.putIfAbsent(toBranchId, new HashMap<>());
        branchInventory.get(toBranchId).putIfAbsent(book, new ArrayList<>());
        branchInventory.get(toBranchId).get(book).addAll(copiesToTransfer);

        System.out.println("Successfully transferred " + copies + " copies of '" + book.getTitle() + "' from branch " + fromBranchId + " to branch " + toBranchId);
        return true;
    }

    // Gets available copies in a specific branch
    public int getAvailableCopies(int branchId, Book book) {
        return branchInventory.getOrDefault(branchId, Collections.emptyMap())
                .getOrDefault(book, Collections.emptyList()).size();
    }

    // Checks if a book is available in any branch
    public boolean isAvailable(Book book) {
        return branchInventory.values().stream()
                .anyMatch(branch -> branch.getOrDefault(book, Collections.emptyList()).size() > 0);
    }

    // Borrows a book from a specific branch
    public BookCopy borrowBookCopy(int branchId, Book book) {
        List<BookCopy> copies = branchInventory.getOrDefault(branchId, Collections.emptyMap())
                .getOrDefault(book, Collections.emptyList());

        for (BookCopy copy : copies) {
            if (!copy.isBorrowed()) {
                copy.markAsBorrowed();
                branchInventory.get(branchId).get(book).remove(copy); // Remove from available copies
                return copy;
            }
        }
        return null;
    }
    
    
    public void removeAllCopies(int branchId, Book book) {
        Map<Book, List<BookCopy>> books = branchInventory.get(branchId);
        if (books != null) {
            books.remove(book);  // Removes all copies of the book
        }
    }

}
