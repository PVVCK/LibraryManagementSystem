package main;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        // Initialize Core Components
        InventoryManager inventoryManager = new InventoryManager();
        NotificationSystem notificationSystem = new NotificationSystem();
        LendingSystem lendingSystem = new LendingSystem(inventoryManager, notificationSystem);

        // Create Library Branches
        LibraryBranch branch1 = new LibraryBranch(1, "Downtown Library", "Downtown", inventoryManager);
        LibraryBranch branch2 = new LibraryBranch(2, "Uptown Library", "Uptown", inventoryManager);

        // Create Books
        Book book1 = new Book(101, "Effective Java", "Joshua Bloch", "9780134685991", 2018);
        Book book2 = new Book(102, "Clean Code", "Robert C. Martin", "9780132350884", 2008);

        // Create Patrons
        Patron patron1 = new Patron(201, "Alice",PatronType.STUDENT);
        Patron patron2 = new Patron(202, "Bob",PatronType.TEACHER);

        // Add Books to Branches
        System.out.println("\nAdding books to branch1...");
        branch1.addBook(book1, 3);
        branch1.addBook(book2, 2);

        System.out.println("\nAdding books to branch2...");
        branch2.addBook(book1, 2);
        branch2.addBook(book2, 3);

        // Check Available Copies
        System.out.println("\nAvailable copies in branch1:");
        System.out.println("Effective Java: " + branch1.getAvailableCopies(book1));
        System.out.println("Clean Code: " + branch1.getAvailableCopies(book2));
        
        System.out.println("\nAvailable copies in branch2:");
        System.out.println("Effective Java: " + branch2.getAvailableCopies(book1));
        System.out.println("Clean Code: " + branch2.getAvailableCopies(book2));

        // Borrowing Books
        System.out.println("\nAlice borrows 'Effective Java' from branch1...");
        BookCopy borrowedCopy = lendingSystem.borrowBook(patron1, 1, book1);
        if (borrowedCopy != null) {
            System.out.println("Borrowed: " + borrowedCopy);
        } else {
            System.out.println("No copies available.");
        }

        // Checking Availability After Borrowing
        System.out.println("\nAvailable copies after Alice borrowed:");
        System.out.println("Effective Java: " + branch1.getAvailableCopies(book1));

        // Returning a Book
        System.out.println("\nAlice returns 'Effective Java'...");
        lendingSystem.returnBook(patron1, 1, borrowedCopy);

        System.out.println("\nAvailable copies after return:");
        System.out.println("Effective Java: " + branch1.getAvailableCopies(book1));

        // Transferring Books Between Branches
        System.out.println("\nTransferring 'Clean Code' from branch1 to branch2...");
        boolean transferSuccess = branch1.transferBook(branch2, book2, 1);
        System.out.println("Transfer successful: " + transferSuccess);

        System.out.println("\nAvailable copies after transfer:");
        System.out.println("Clean Code in branch1: " + branch1.getAvailableCopies(book2));
        System.out.println("Clean Code in branch2: " + branch2.getAvailableCopies(book2));

     // Forcefully setting copies to 0 before reservation  
        branch1.removeAllCopies(book1);  
        System.out.println("\nCopies set to 0 for 'Effective Java'");  

        // Verify available copies before reservation  
        System.out.println("\nAvailable copies before reservation: " + branch1.getAvailableCopies(book1));  

        // Bob tries to reserve  
        boolean reservationSuccess = lendingSystem.reserveBook(patron2, 1, book1);  
        System.out.println("Reservation successful: " + reservationSuccess);  

        // Bob cancels the reservation  
        boolean cancellationSuccess = lendingSystem.cancelReservation(patron2, 1, book1);  
        System.out.println("Cancellation successful: " + cancellationSuccess);



    }
}
