package main;

public class LibraryBranch {
    private final int branchId;
    private final String name;
    private final String location;
    private final InventoryManager inventoryManager;

    public LibraryBranch(int branchId, String name, String location, InventoryManager inventoryManager) {
        if (name == null || name.isEmpty() || location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Branch name and location cannot be null or empty.");
        }
        this.branchId = branchId;
        this.name = name;
        this.location = location;
        this.inventoryManager = inventoryManager;
    }

    public int getBranchId() {
        return branchId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    // Adds books to this branch
    public void addBook(Book book, int copies) {
        inventoryManager.addBookCopy(branchId, book, copies);
    }

    // Transfers books to another branch
    public boolean transferBook(LibraryBranch destinationBranch, Book book, int copies) {
        return inventoryManager.transferBook(branchId, destinationBranch.getBranchId(), book, copies);
    }

    // Gets available copies in this branch
    public int getAvailableCopies(Book book) {
        return inventoryManager.getAvailableCopies(branchId, book);
    }
    
    // Removed book from the Branch
    public void removeBook(Book book) {
        inventoryManager.removeAllCopies(branchId, book); // Remove all copies from inventory
        System.out.println("Book '" + book.getTitle() + "' removed from branch: " + name);
    }

    
    // remove book Copies (Control at the branch Level)
    public void removeAllCopies(Book book) {
        inventoryManager.removeAllCopies(branchId, book);
    }


}
