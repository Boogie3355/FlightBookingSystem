import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
public class FlightBookingSystem {
    private static Scanner scanner = new Scanner(System.in);
    private static String fullName;
    private static int age;
    private static String gender;
    private static String phoneNumber;
    private static String bookingType;
    private static int totalPeople = 1;
    private static String selectedCountry;
    private static String selectedClass;
    private static int baseTicketPrice;
    private static boolean hasFreeTicket = false;
    private static boolean hasFreeFood = false;
    private static boolean hasFreeAlcohol = false;
    private static String selectedFood = "";
    private static String selectedAlcohol = "";
    private static String selectedSeats = "";
    private static int totalAmount = 0;
    private static LocalDate todayDate;
    private static LocalDate bookingDate;
    private static String[] countries = {
        "USA", "UK", "Canada", "Australia", "Germany", 
        "France", "Japan", "Singapore", "Dubai", "Italy",
        "Spain", "Thailand", "Malaysia", "Switzerland", "Netherlands"
    };
    private static String[] foodItems = {
        "Biryani", "Sandwich", "Pasta", "Pizza", "Burger",
        "Noodles", "Salad", "Chicken Curry", "Fish Fry", "Vegetable Rice"
    };
    private static String[] alcoholicDrinks = {
        "Wine (Red)", "Wine (White)", "Beer", "Whiskey", "Vodka",
        "Rum", "Gin", "Tequila", "Champagne", "Cocktail (Mojito)",
        "Cocktail (Martini)", "Brandy", "Scotch", "Bourbon", "Sake"
    };
    private static String[][] firstClassSeats = new String[2][4];
    private static String[][] businessSeats = new String[3][4];
    private static String[][] economySeats = new String[5][6];
    
    public static void main(String[] args) {
        System.out.println("=== WELCOME TO FLIGHT BOOKING ===\n");
        initializeSeats();
        getDates();
        getPersonalDetails();
        selectDestination();
        selectClassAndPrice();
        selectFood();
        selectAlcohol();
        bookSeats();
        showBookingSummary();
        
        scanner.close();
    }
    private static void initializeSeats() {
        for (int i = 0; i < firstClassSeats.length; i++) {
            for (int j = 0; j < firstClassSeats[i].length; j++) {
                firstClassSeats[i][j] = "[ ]";
            }
        }
        for (int i = 0; i < businessSeats.length; i++) {
            for (int j = 0; j < businessSeats[i].length; j++) {
                businessSeats[i][j] = "[ ]";
            }
        }
        for (int i = 0; i < economySeats.length; i++) {
            for (int j = 0; j < economySeats[i].length; j++) {
                economySeats[i][j] = "[ ]";
            }
        }
    }
    private static void getDates() {
        System.out.println("=== DATE INFORMATION ===");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        System.out.print("Enter today's date (dd/MM/yyyy): ");
        String todayInput = scanner.nextLine();
        try {
            todayDate = LocalDate.parse(todayInput, formatter);
        } catch (Exception e) {
            System.out.println("Invalid date format! Using system date.");
            todayDate = LocalDate.now();
        }
        
        System.out.print("Enter your travel/booking date (dd/MM/yyyy): ");
        String bookingInput = scanner.nextLine();
        try {
            bookingDate = LocalDate.parse(bookingInput, formatter);
            if (bookingDate.isBefore(todayDate)) {
                System.out.println("Travel date cannot be in the past! Using tomorrow's date.");
                bookingDate = todayDate.plusDays(1);
            }
        } catch (Exception e) {
            System.out.println("Invalid date format! Using tomorrow's date.");
            bookingDate = todayDate.plusDays(1);
        }
        
        System.out.println("Today's Date: " + todayDate.format(formatter));
        System.out.println("Travel Date: " + bookingDate.format(formatter));
        System.out.println();
    }
    private static void getPersonalDetails() {
        System.out.println("=== PERSONAL DETAILS ===");
        
        System.out.print("Enter your Full Name: ");
        fullName = scanner.nextLine();
        
        System.out.print("Enter your Age: ");
        age = scanner.nextInt();
        scanner.nextLine(); 
        
        System.out.print("Enter your Gender (Male/Female/Other): ");
        gender = scanner.nextLine();
        
        System.out.print("Enter your Phone Number: ");
        phoneNumber = scanner.nextLine();
        
        System.out.println("Booking Types:");
        System.out.println("1. Single");
        System.out.println("2. Couple");
        System.out.println("3. Parent & Child");
        System.out.print("Enter Booking Type (1-3): ");
        int bookingChoice = scanner.nextInt();
        scanner.nextLine();
        
        while (bookingChoice < 1 || bookingChoice > 3) {
            System.out.print("Please enter valid choice (1-3): ");
            bookingChoice = scanner.nextInt();
            scanner.nextLine();
        }
        
        switch (bookingChoice) {
            case 1:
                bookingType = "Single";
                totalPeople = 1;
                break;
            case 2:
                bookingType = "Couple";
                totalPeople = 2;
                break;
            case 3:
                bookingType = "Parent & Child";
                System.out.print("How many people in total (including parent and children) ");
                totalPeople = scanner.nextInt();
                scanner.nextLine();
                while (totalPeople < 2) {
                    System.out.print("Please enter at least 2 people for Parent & Child booking: ");
                    totalPeople = scanner.nextInt();
                    scanner.nextLine();
                }
                break;
        }
        
        System.out.println("Booking Type: " + bookingType);
        System.out.println("Total People: " + totalPeople);
        System.out.println();
    }
    private static void selectDestination() {
        System.out.println("=== DESTINATION SELECTION ===");
        System.out.println("Available Countries:");
        
        for (int i = 0; i < countries.length; i++) {
            System.out.println((i + 1) + ". " + countries[i]);
        }
        System.out.print("\nWhere do you want to travel? Enter country number (1-15): ");
        int countryChoice = scanner.nextInt();
        scanner.nextLine(); 
        
        while (countryChoice < 1 || countryChoice > 15) {
            System.out.print("Please enter a valid country number (1-15): ");
            countryChoice = scanner.nextInt();
            scanner.nextLine();
        }
        selectedCountry = countries[countryChoice - 1];
        System.out.println("You selected: " + selectedCountry + "\n");
    }
    private static void selectClassAndPrice() {
        System.out.println("=== CLASS SELECTION ===");
        System.out.println("Available Classes:");
        System.out.println("1. First Class - $8000");
        System.out.println("2. Business Class - $5000");
        System.out.println("3. Economy Class - $3000");
        
        System.out.print("Which class do you want put. Enter choice (1-3): ");
        int classChoice = scanner.nextInt();
        scanner.nextLine(); 
        
        while (classChoice < 1 || classChoice > 3) {
            System.out.print("Please enter valid choice (1-3): ");
            classChoice = scanner.nextInt();
            scanner.nextLine();
        }
        
        switch (classChoice) {
            case 1:
                selectedClass = "First Class";
                baseTicketPrice = 8000;
                break;
            case 2:
                selectedClass = "Business Class";
                baseTicketPrice = 5000;
                break;
            case 3:
                selectedClass = "Economy Class";
                baseTicketPrice = 3000;
                break;
        }
        calculatePricing();
        
        System.out.println("Selected Class: " + selectedClass);
        System.out.println("Base Price per ticket: $" + baseTicketPrice);
        System.out.println("Total Amount (after discounts): $" + totalAmount + "\n");
    }
    private static void calculatePricing() {
        long daysDifference = ChronoUnit.DAYS.between(todayDate, bookingDate);
        int finalPrice = baseTicketPrice;
        if (daysDifference <= 1) {
            finalPrice = baseTicketPrice / 2;
            System.out.println("Last-minute booking discount: 50% off!");
        } else {
            System.out.println("Regular pricing applies.");
        }
        if (bookingType.equals("Couple")) {
            if (selectedClass.equals("Economy Class")) {
                totalAmount = finalPrice; 
                hasFreeTicket = true;
                hasFreeFood = true;
                System.out.println("Couple booking in Economy: One ticket FREE + FREE FOOD!");
            } else {
                totalAmount = finalPrice;
                hasFreeTicket = true;
                hasFreeFood = true;
                hasFreeAlcohol = true;
                System.out.println("Couple booking in " + selectedClass + ": One ticket FREE + FREE FOOD + FREE ALCOHOL!");
            }
        } else if (bookingType.equals("Parent & Child")) {
            totalAmount = finalPrice * totalPeople; 
            if (!selectedClass.equals("Economy Class")) {
                hasFreeFood = true;
                hasFreeAlcohol = true; 
                System.out.println("Family booking in " + selectedClass + ": FREE FOOD + FREE ALCOHOL for adults!");
            }
        } else { 
            totalAmount = finalPrice;
            if (selectedClass.equals("First Class")) {
                hasFreeFood = true;
                hasFreeAlcohol = true;
                System.out.println("Single First Class booking: FREE FOOD + FREE ALCOHOL included!");
            } else if (selectedClass.equals("Business Class")) {
                hasFreeFood = true;
                hasFreeAlcohol = true;
                System.out.println("Single Business Class booking: FREE FOOD + FREE ALCOHOL included!");
            } else {
                hasFreeAlcohol = true; 
                System.out.println("Single booking: FREE ALCOHOL included!");
            }
        }
    }
    private static void selectFood() {
        if (hasFreeFood) {
            System.out.println("=== FOOD SELECTION (FREE) ===");
        } else {
            System.out.println("=== FOOD SELECTION ===");
        }
        
        System.out.println("Available Food Items:");
        for (int i = 0; i < foodItems.length; i++) {
            System.out.println((i + 1) + ". " + foodItems[i]);
        }
        
        System.out.println((foodItems.length + 1) + ". Select All Items");
        System.out.print("Which food do you want? (You can select multiple items, separate by comma): ");
        String foodInput = scanner.nextLine();
        
        if (foodInput.equals(String.valueOf(foodItems.length + 1))) {
            selectedFood = "All Items";
        } else {
            String[] choices = foodInput.split(",");
            List<String> selectedItems = new ArrayList<>();
            
            for (String choice : choices) {
                try {
                    int index = Integer.parseInt(choice.trim()) - 1;
                    if (index >= 0 && index < foodItems.length) {
                        selectedItems.add(foodItems[index]);
                    }
                } catch (NumberFormatException e) {
                }
            }
            
            if (selectedItems.isEmpty()) {
                selectedFood = foodItems[0]; 
            } else {
                selectedFood = String.join(", ", selectedItems);
            }
        }
        if (hasFreeFood) {
            System.out.println("Food selected (FREE): " + selectedFood);
        } else {
            System.out.println("Food selected: " + selectedFood);
        }
        System.out.println();
    }
    private static void selectAlcohol() {
        if (hasFreeAlcohol) {
            System.out.println("=== ALCOHOL SELECTION (FREE) ===");
            System.out.print("Do you want alcoholic drinks? (yes/no): ");
            String wantAlcohol = scanner.nextLine();
            
            if (wantAlcohol.equalsIgnoreCase("yes")) {
                System.out.println("Available Alcoholic Drinks:");
                for (int i = 0; i < alcoholicDrinks.length; i++) {
                    System.out.println((i + 1) + ". " + alcoholicDrinks[i]);
                }
                
                System.out.println((alcoholicDrinks.length + 1) + ". Select All Drinks");
                System.out.print("Which drinks do you want? (You can select multiple, separate by comma): ");
                String alcoholInput = scanner.nextLine();
                
                if (alcoholInput.equals(String.valueOf(alcoholicDrinks.length + 1))) {
                    selectedAlcohol = "All Drinks";
                } else {
                    String[] choices = alcoholInput.split(",");
                    List<String> selectedDrinks = new ArrayList<>();
                    
                    for (String choice : choices) {
                        try {
                            int index = Integer.parseInt(choice.trim()) - 1;
                            if (index >= 0 && index < alcoholicDrinks.length) {
                                selectedDrinks.add(alcoholicDrinks[index]);
                            }
                        } catch (NumberFormatException e) {
                        }
                    }
                    
                    if (selectedDrinks.isEmpty()) {
                        selectedAlcohol = alcoholicDrinks[0]; 
                    } else {
                        selectedAlcohol = String.join(", ", selectedDrinks);
                    }
                }
                
                System.out.println("Alcohol selected (FREE): " + selectedAlcohol);
            } else {
                selectedAlcohol = "No alcohol selected";
                System.out.println("No alcoholic drinks selected.");
            }
        }
        System.out.println();
    }
    private static void bookSeats() {
        System.out.println("=== SEAT BOOKING ===");
        System.out.println("  AIRPLANE SEATING LAYOUT ");
        System.out.println("Legend: [ ] = Available, [X] = Selected/Booked");
        System.out.println();
        
        if (selectedClass.equals("First Class")) {
            displayFirstClassSeats();
            selectFirstClassSeats();
        } else if (selectedClass.equals("Business Class")) {
            displayBusinessSeats();
            selectBusinessSeats();
        } else {
            displayEconomySeats();
            selectEconomySeats();
        }
    }
    
    private static void displayFirstClassSeats() {
        System.out.println("╔═══════════════════════════════╗");
        System.out.println("║         FIRST CLASS           ║");
        System.out.println("║           (FRONT)             ║");
        System.out.println("╠═══════════════════════════════╣");
        System.out.println("║     A    B         C    D     ║");
        System.out.println("║                               ║");
        for (int i = 0; i < firstClassSeats.length; i++) {
            System.out.print("║  " + (i + 1) + "  ");
            for (int j = 0; j < firstClassSeats[i].length; j++) {
                if (j == 2) System.out.print("   "); 
                System.out.print(firstClassSeats[i][j] + "  ");
            }
            System.out.println(" ║");
        }
        System.out.println("║                               ║");
        System.out.println("╚═══════════════════════════════╝");
        System.out.println();
    }
    
    private static void displayBusinessSeats() {
        System.out.println("╔═══════════════════════════════╗");
        System.out.println("║        BUSINESS CLASS         ║");
        System.out.println("║           (MIDDLE)            ║");
        System.out.println("╠═══════════════════════════════╣");
        System.out.println("║     A    B         C    D     ║");
        System.out.println("║                               ║");
        for (int i = 0; i < businessSeats.length; i++) {
            System.out.print("║  " + (i + 1) + "  ");
            for (int j = 0; j < businessSeats[i].length; j++) {
                if (j == 2) System.out.print("   "); 
                System.out.print(businessSeats[i][j] + "  ");
            }
            System.out.println(" ║");
        }
        System.out.println("║                               ║");
        System.out.println("╚═══════════════════════════════╝");
        System.out.println();
    }
    
    private static void displayEconomySeats() {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║            ECONOMY CLASS              ║");
        System.out.println("║             (BACK)                    ║");
        System.out.println("╠═══════════════════════════════════════╣");
        System.out.println("║   A    B    C       D    E    F       ║");
        System.out.println("║                                       ║");
        for (int i = 0; i < economySeats.length; i++) {
            System.out.print("║ " + (i + 1) + " ");
            for (int j = 0; j < economySeats[i].length; j++) {
                if (j == 3) System.out.print("   "); 
                System.out.print(economySeats[i][j] + " ");
            }
            System.out.println(" ║");
        }
        System.out.println("║                                       ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println();
    }
    
    private static void selectFirstClassSeats() {
        for (int seat = 1; seat <= totalPeople; seat++) {
            System.out.println("Selecting seat " + seat + " of " + totalPeople + ":");
            System.out.print("Enter Row (1-2): ");
            int row = scanner.nextInt();
            System.out.print("Enter Column (A=1, B=2, C=3, D=4): ");
            int col = scanner.nextInt();
            
            while (row < 1 || row > 2 || col < 1 || col > 4 || firstClassSeats[row-1][col-1].equals("[X]")) {
                if (row >= 1 && row <= 2 && col >= 1 && col <= 4 && firstClassSeats[row-1][col-1].equals("[X]")) {
                    System.out.println(" Seat already booked! Choose another seat.");
                } else {
                    System.out.println(" Invalid seat selection!");
                }
                System.out.print("Enter Row (1-2): ");
                row = scanner.nextInt();
                System.out.print("Enter Column (A=1, B=2, C=3, D=4): ");
                col = scanner.nextInt();
            }
            firstClassSeats[row-1][col-1] = "[X]";
            String seatLabel = row + "" + (char)('A' + col - 1);
            
            if (seat == 1) {
                selectedSeats = seatLabel;
            } else {
                selectedSeats += ", " + seatLabel;
            }
            System.out.println(" Seat " + seatLabel + " selected successfully!");
            System.out.println("\nUpdated Seating Layout:");
            displayFirstClassSeats();
        }
        scanner.nextLine(); 
        System.out.println(" All seats booked: " + selectedSeats + "\n");
    }
    private static void selectBusinessSeats() {
        for (int seat = 1; seat <= totalPeople; seat++) {
            System.out.println("Selecting seat " + seat + " of " + totalPeople + ":");
            System.out.print("Enter Row (1-3): ");
            int row = scanner.nextInt();
            System.out.print("Enter Column (A=1, B=2, C=3, D=4): ");
            int col = scanner.nextInt();
            while (row < 1 || row > 3 || col < 1 || col > 4 || businessSeats[row-1][col-1].equals("[X]")) {
                if (row >= 1 && row <= 3 && col >= 1 && col <= 4 && businessSeats[row-1][col-1].equals("[X]")) {
                    System.out.println(" Seat already booked! Choose another seat.");
                } else {
                    System.out.println(" Invalid seat selection!");
                }
                System.out.print("Enter Row (1-3): ");
                row = scanner.nextInt();
                System.out.print("Enter Column (A=1, B=2, C=3, D=4): ");
                col = scanner.nextInt();
            }
            
            businessSeats[row-1][col-1] = "[X]";
            String seatLabel = row + "" + (char)('A' + col - 1);
            
            if (seat == 1) {
                selectedSeats = seatLabel;
            } else {
                selectedSeats += ", " + seatLabel;
            }
            System.out.println(" Seat " + seatLabel + " selected successfully!");
            System.out.println("\nUpdated Seating Layout:");
            displayBusinessSeats();
        }
        scanner.nextLine(); 
        System.out.println(" All seats booked: " + selectedSeats + "\n");
    }
    private static void selectEconomySeats() {
        for (int seat = 1; seat <= totalPeople; seat++) {
            System.out.println("Selecting seat " + seat + " of " + totalPeople + ":");
            System.out.print("Enter Row (1-5): ");
            int row = scanner.nextInt();
            System.out.print("Enter Column (A=1, B=2, C=3, D=4, E=5, F=6): ");
            int col = scanner.nextInt();
            while (row < 1 || row > 5 || col < 1 || col > 6 || economySeats[row-1][col-1].equals("[X]")) {
                if (row >= 1 && row <= 5 && col >= 1 && col <= 6 && economySeats[row-1][col-1].equals("[X]")) {
                    System.out.println(" Seat already booked! Choose another seat.");
                } else {
                    System.out.println(" Invalid seat selection!");
                }
                System.out.print("Enter Row (1-5): ");
                row = scanner.nextInt();
                System.out.print("Enter Column (A=1, B=2, C=3, D=4, E=5, F=6): ");
                col = scanner.nextInt();
            }
            economySeats[row-1][col-1] = "[X]";
            String seatLabel = row + "" + (char)('A' + col - 1);
            
            if (seat == 1) {
                selectedSeats = seatLabel;
            } else {
                selectedSeats += ", " + seatLabel;
            }
            System.out.println("  Seat " + seatLabel + " selected successfully!");
            System.out.println("\nUpdated Seating Layout:");
            displayEconomySeats();
        }
        scanner.nextLine(); 
        System.out.println(" All seats booked: " + selectedSeats + "\n");
    }
    private static void showBookingSummary() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║           BOOKING SUMMARY            ║");
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║ Name: " + String.format("%-28s", fullName) + " ║");
        System.out.println("║ Age: " + String.format("%-29s", age) + " ║");
        System.out.println("║ Gender: " + String.format("%-26s", gender) + " ║");
        System.out.println("║ Phone Number: " + String.format("%-20s", phoneNumber) + " ║");
        System.out.println("║ Booking Type: " + String.format("%-20s", bookingType) + " ║");
        System.out.println("║ Total People: " + String.format("%-20s", totalPeople) + " ║");
        System.out.println("║ Today's Date: " + String.format("%-20s", todayDate.format(formatter)) + " ║");
        System.out.println("║ Travel Date: " + String.format("%-21s", bookingDate.format(formatter)) + " ║");
        System.out.println("║ Destination: " + String.format("%-21s", selectedCountry) + " ║");
        System.out.println("║ Class: " + String.format("%-27s", selectedClass) + " ║");
        System.out.println("║ Ticket Amount: $" + String.format("%-17s", totalAmount) + " ║");
        System.out.println("║ Free Ticket: " + String.format("%-21s", (hasFreeTicket ? "Yes" : "No")) + " ║");
        System.out.println("║ Free Food: " + String.format("%-23s", (hasFreeFood ? "Yes" : "No")) + " ║");
        System.out.println("║ Free Alcohol: " + String.format("%-19s", (hasFreeAlcohol ? "Yes" : "No")) + " ║");
        
        if (hasFreeFood || !selectedFood.isEmpty()) {
            System.out.println("║ Food Selected: " + String.format("%-19s", selectedFood.length() > 19 ? selectedFood.substring(0,16) + "..." : selectedFood) + " ║");
        }
        if (hasFreeAlcohol && !selectedAlcohol.equals("No alcohol selected") && !selectedAlcohol.isEmpty()) {
            System.out.println("║ Alcohol Selected: " + String.format("%-16s", selectedAlcohol.length() > 16 ? selectedAlcohol.substring(0,13) + "..." : selectedAlcohol) + " ║");
        }
        System.out.println("╠══════════════════════════════════════╣");
        System.out.println("║          SEAT CONFIRMATION          ║");
        System.out.println("╠══════════════════════════════════════╣");
        if (selectedClass.equals("First Class")) {
            displayFinalFirstClassSeats();
        } else if (selectedClass.equals("Business Class")) {
            displayFinalBusinessSeats();
        } else {
            displayFinalEconomySeats();
        }
        
        System.out.println("║                                      ║");
        System.out.println("║  Your Seat(s): " + String.format("%-17s", selectedSeats) + " ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println("\n === Thank you for booking with us! ===  ");
    }
    private static void displayFinalFirstClassSeats() {
        System.out.println("║     A    B         C    D            ║");
        System.out.println("║                                      ║");
        for (int i = 0; i < firstClassSeats.length; i++) {
            System.out.print("║  " + (i + 1) + "  ");
            for (int j = 0; j < firstClassSeats[i].length; j++) {
                if (j == 2) System.out.print("   ");
                if (firstClassSeats[i][j].equals("[X]")) {
                    System.out.print("[X]  ");
                } else {
                    System.out.print("[ ]  ");
                }
            }
            System.out.println(" ║");
        }
    }
    private static void displayFinalBusinessSeats() {
        System.out.println("║     A    B         C    D            ║");
        System.out.println("║                                      ║");
        for (int i = 0; i < businessSeats.length; i++) {
            System.out.print("║  " + (i + 1) + "  ");
            for (int j = 0; j < businessSeats[i].length; j++) {
                if (j == 2) System.out.print("   "); 
                if (businessSeats[i][j].equals("[X]")) {
                    System.out.print("[X]  ");
                } else {
                    System.out.print("[ ]  ");
                }
            }
            System.out.println(" ║");
        }
    }
    private static void displayFinalEconomySeats() {
        System.out.println("║   A    B    C       D    E    F      ║");
        System.out.println("║                                      ║");
        for (int i = 0; i < economySeats.length; i++) {
            System.out.print("║ " + (i + 1) + " ");
            for (int j = 0; j < economySeats[i].length; j++) {
                if (j == 3) System.out.print("   "); 
                if (economySeats[i][j].equals("[X]")) {
                    System.out.print("[*] ");
                } else {
                    System.out.print("[ ] ");
                }
            }
            System.out.println(" ║");
        }
    }
}