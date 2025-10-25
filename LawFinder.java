import java.util.*;
import java.io.*;

// Main interface for the Law Finder application
public class LawFinder {
    private Scanner scanner;
    private List<String> queryHistory;

    public LawFinder() {
        this.scanner = new Scanner(System.in);
        this.queryHistory = new ArrayList<>();
    }

    // Display main menu
    public void showMainMenu() {
        System.out.println("\n========================================");
        System.out.println("   OFFLINE LAW FINDER & PENALTY CALC");
        System.out.println("========================================");
        System.out.println("1. Search Law by Keyword");
        System.out.println("2. Browse Laws by Category");
        System.out.println("3. Calculate Penalty");
        System.out.println("4. View Query History");
        System.out.println("5. View All Laws");
        System.out.println("6. Exit");
        System.out.println("========================================");
        System.out.print("Enter your choice (1-6): ");
    }

    // Search law by keyword
    public void searchLawByKeyword() {
        System.out.print("\nEnter keyword to search (e.g., overspeeding, theft, hacking): ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("❌ Keyword cannot be empty!");
            return;
        }

        try {
            List<Law> results = LawDatabase.searchLaws(keyword);
            if (results.isEmpty()) {
                System.out.println("❌ No laws found for: " + keyword);
                System.out.println("📝 Try searching with keywords like: overspeeding, theft, hacking, etc.");
            } else {
                System.out.println("\n✅ Found " + results.size() + " matching law(s):\n");
                for (int i = 0; i < results.size(); i++) {
                    displayLawDetails(results.get(i), i + 1);
                }
            }
            queryHistory.add("Searched: " + keyword);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }

    // Browse laws by category
    public void browseLawsByCategory() {
        List<String> categories = LawDatabase.getAllCategories();
        System.out.println("\n📂 Available Categories:");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println((i + 1) + ". " + categories.get(i));
        }
        System.out.print("Select a category (1-" + categories.size() + "): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice < 1 || choice > categories.size()) {
                System.out.println("❌ Invalid choice!");
                return;
            }

            String selectedCategory = categories.get(choice - 1);
            List<Law> laws = LawDatabase.getLawsByCategory(selectedCategory);

            System.out.println("\n📋 Laws in " + selectedCategory + ":\n");
            for (int i = 0; i < laws.size(); i++) {
                displayLawDetails(laws.get(i), i + 1);
            }
            queryHistory.add("Browsed: " + selectedCategory);
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }

    // Calculate penalty
    public void calculatePenalty() {
        System.out.print("\nEnter law keyword: ");
        String keyword = scanner.nextLine().trim();

        try {
            Law law = LawDatabase.findLawByKeyword(keyword);
            System.out.print("Enter number of repeat offenses (0 for first time): ");
            int repeatOffenses = Integer.parseInt(scanner.nextLine().trim());

            if (repeatOffenses < 0) {
                System.out.println("❌ Repeat offenses cannot be negative!");
                return;
            }

            int penalty = law.calculatePenalty(repeatOffenses);

            System.out.println("\n💰 PENALTY CALCULATION");
            System.out.println("========================");
            System.out.println("Law Section: " + law.getSection());
            System.out.println("Violation: " + law.getDescription());
            System.out.println("Basic Fine: ₹" + law.getBasicFine());
            System.out.println("Repeat Offenses: " + repeatOffenses);
            System.out.println("------------------------");
            System.out.println("Total Penalty: ₹" + penalty);
            System.out.println("Punishment: " + law.getPunishment());
            System.out.println("========================");

            queryHistory.add("Calculated penalty for: " + law.getSection() + " (₹" + penalty + ")");
        } catch (LawNotFoundException e) {
            System.out.println("❌ " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ Please enter a valid number!");
        }
    }

    // View query history
    public void viewQueryHistory() {
        if (queryHistory.isEmpty()) {
            System.out.println("\n📜 Query history is empty!");
        } else {
            System.out.println("\n📜 Query History:");
            for (int i = 0; i < queryHistory.size(); i++) {
                System.out.println((i + 1) + ". " + queryHistory.get(i));
            }
        }
    }

    // View all laws
    public void viewAllLaws() {
        List<Law> allLaws = LawDatabase.getAllLaws();
        System.out.println("\n📚 ALL LAWS IN DATABASE (" + allLaws.size() + " laws)\n");
        for (int i = 0; i < allLaws.size(); i++) {
            displayLawDetails(allLaws.get(i), i + 1);
        }
    }

    // Helper method to display law details
    private void displayLawDetails(Law law, int serialNumber) {
        System.out.println("[" + serialNumber + "] " + law.getSection());
        System.out.println("    Description: " + law.getDescription());
        System.out.println("    Category: " + law.getCategory());
        System.out.println("    Basic Fine: ₹" + law.getBasicFine());
        System.out.println("    Punishment: " + law.getPunishment());
        System.out.println();
    }

    // Run the application
    public void run() {
        System.out.println("\n🚀 Welcome to Offline Law Finder & Penalty Calculator!");
        System.out.println("📚 Comprehensive Indian Laws Database\n");

        boolean running = true;
        while (running) {
            showMainMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    searchLawByKeyword();
                    break;
                case "2":
                    browseLawsByCategory();
                    break;
                case "3":
                    calculatePenalty();
                    break;
                case "4":
                    viewQueryHistory();
                    break;
                case "5":
                    viewAllLaws();
                    break;
                case "6":
                    System.out.println("\n👋 Thank you for using Law Finder. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice! Please enter 1-6.");
            }
        }
        scanner.close();
    }
}