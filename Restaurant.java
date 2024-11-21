import java.util.ArrayList;
import java.util.Scanner;

public class Restaurant {
    private static final int MAX_TABLES = 10;
    private static boolean[] tables = new boolean[MAX_TABLES];
    private static ArrayList<Integer> bookedTables = new ArrayList<>();
    private static ArrayList<MenuItem> menu = new ArrayList<>();
    private static ArrayList<Order> orders = new ArrayList<>();
    private static String customerName;

    public static void main(String[] args) {
        initializeMenu();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("         ------------------------------------------------------------------      ");
            System.out.println("\n             Welcome to the Restaurant Management System!"            );
            System.out.print("         ------------------------------------------------------------------      ");
            System.out.println("\nEnter the user type");
            System.out.println("1. Customer");
            System.out.println("2. Manager");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int userType = scanner.nextInt();
            switch (userType) {
                case 1:
                    customerActions(scanner);
                    break;
                case 2:
                    managerActions(scanner);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid user type!");
            }
        }
    }

    private static void initializeMenu() {
        menu.add(new MenuItem("Burger", "Classic beef burger", 5.99));
        menu.add(new MenuItem("Pizza", "Margherita pizza", 8.99));
        menu.add(new MenuItem("Soft drinks", "Lemonade", 2.99));
        menu.add(new MenuItem("Sandwich", "Chicken sandwich", 4.99));
        menu.add(new MenuItem("Desserts", "Brownie", 5.99));
    }

    private static void customerActions(Scanner scanner) {
        scanner.nextLine();
        System.out.println("Enter name");
        customerName = scanner.nextLine();

        try {
            if (customerName.matches(".*\\d+.*")) {
                System.out.println("Error: Name cannot contain numbers.");
                return;
            }

            System.out.println("Welcome, " + customerName + "!");
        } catch (Exception e) {

            System.out.println("An error occurred: " + e.getMessage());
        }


        while (true) {
            System.out.println();
            System.out.println("1. Book Table");
            System.out.println("2. Display Menu");
            System.out.println("3. Order Food");
            System.out.println("4. Cancel Food Order");
            System.out.println("5. Cancel Table Booking");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    bookTable(scanner);
                    break;
                case 2:
                    displayMenu();
                    break;
                case 3:
                    orderFood(scanner);
                    break;

                case 4:
                    cancelFoodOrder(scanner);
                    break;
                case 5:
                    cancelTableBooking(scanner);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void managerActions(Scanner scanner) {
        while (true) {
            System.out.println("\nManager Section:");
            System.out.println("1. Display Booked Tables");
            System.out.println("2. Add Menu Items");
            System.out.println("3. Remove Menu Items");
            System.out.println("4. Display Ordered Food");
			  System.out.println("5. Display Menu");
            System.out.println("6. Exit to main menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    displayBookedTables();
                    break;
                case 2:
                    addMenuItems(scanner);
                    break;
                case 3:
                    removeMenuItems(scanner);
                    break;
                case 4:
                    displayOrderedFood();
                    break;
			   case 5:
			         displayMenu();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void bookTable(Scanner scanner) {
        System.out.print("Enter table number to book (1-" + MAX_TABLES + "): ");
        int tableNumber = scanner.nextInt();
        if (tableNumber < 1 || tableNumber > MAX_TABLES) {
            System.out.println("Invalid table number!");
            return;
        }
        if (tables[tableNumber - 1]) {
            System.out.println("Table " + tableNumber + " is already booked!");
        } else {
            tables[tableNumber - 1] = true;
            bookedTables.add(tableNumber);
            System.out.println("Table " + tableNumber + " booked successfully by " + customerName);
        }
    }

    private static void displayBookedTables() {
        System.out.println("\nBooked Tables:");
        for (Integer table : bookedTables) {
            System.out.println("Table " + table + " Booked by " + customerName);
        }
    }

    private static void displayMenu() {
        System.out.println("\nMenu:");
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.get(i);
            System.out.println((i + 1) + ". " + item.getName() + " - " + item.getDescription() + " - $" + item.getPrice());
        }
		System.out.println();
    }

    private static void orderFood(Scanner scanner) {
        System.out.println("\nPlease choose from the menu items:");
        displayMenu();
        while (true) {
            System.out.print("Enter the number corresponding to the menu item (0 to finish ordering): ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                break; // Exit the loop if the user chooses to finish ordering
            }
            if (choice >= 1 && choice <= menu.size()) {
                MenuItem chosenItem = menu.get(choice - 1);
                System.out.println(customerName + " have ordered: " + chosenItem.getName());
                System.out.println("Description: " + chosenItem.getDescription());
                System.out.println("Price: $" + chosenItem.getPrice());

                // Add the ordered item to the order list
                ArrayList<MenuItem> itemsOrdered = new ArrayList<>();
                itemsOrdered.add(chosenItem);
                Order order = new Order(bookedTables.get(bookedTables.size() - 1), customerName, itemsOrdered); // Pass customerName
                orders.add(order);
            } else {
                System.out.println("Invalid menu item choice!");
            }
        }
    }

    private static void cancelFoodOrder(Scanner scanner) {
        System.out.print("Enter the table number for which you want to cancel the food order: ");
        int tableNumber = scanner.nextInt();

        boolean orderFound = false;
        for (Order order : orders) {
            if (order.getTableNumber() == tableNumber && order.getCustomerName().equals(customerName)) {
                orders.remove(order);
                orderFound = true;
                break;
            }
        }
        if (orderFound) {
            System.out.println("Food order for table " + tableNumber + " canceled successfully.");
        } else {
            System.out.println("No food order found for table " + tableNumber + " under your name.");
        }
    }

    private static void addMenuItems(Scanner scanner) {
        System.out.print("Enter name of new menu item: ");
        scanner.nextLine();
        String name = scanner.nextLine();
        System.out.print("Enter description of new menu item: ");
        String description = scanner.nextLine();
        System.out.print("Enter price of new menu item: ");
        double price = scanner.nextDouble();
        menu.add(new MenuItem(name, description, price));
        System.out.println("New menu item added successfully!");
    }

    private static void removeMenuItems(Scanner scanner) {
        System.out.println("Menu Items:");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i + 1) + ". " + menu.get(i).getName());
        }
        System.out.print("Enter the number of menu item to remove: ");
        int choice = scanner.nextInt();
        if (choice >= 1 && choice <= menu.size()) {
            menu.remove(choice - 1);
            System.out.println("Menu item removed successfully!");
        } else {
            System.out.println("Invalid menu item choice!");
        }
    }

    private static void displayOrderedFood() {
        System.out.println("\nOrdered Food:");
        int currentTableNumber = -1; // Initialize with an invalid table number
        for (Order order : orders) {
            if (order.getTableNumber() != currentTableNumber) {
                System.out.println("\nTable " + order.getTableNumber() + "  Order (Customer: " + order.getCustomerName() + "):");
                currentTableNumber = order.getTableNumber();
            }
            for (MenuItem item : order.getItems()) {
                System.out.println("- " + item.getName() + " (" + item.getDescription() + ") - $" + item.getPrice());
            }
        }
    }

    private static void cancelTableBooking(Scanner scanner) {
        System.out.print("Enter the table number to cancel booking: ");
        int tableNumber = scanner.nextInt();
        if (bookedTables.contains(tableNumber)) {
            // Free up the table
            tables[tableNumber - 1] = false;
            bookedTables.remove(Integer.valueOf(tableNumber));
            System.out.println("Table " + tableNumber + " booking canceled successfully.");
        } else {
            System.out.println("Table " + tableNumber + " is not booked.");
        }
    }

    private static void displayBill() {
        System.out.print("-------------------------------------------------------------------------------------");
        System.out.println("\n             --- Bill for " + customerName + " ---");

        double totalBill = 0;
        for (Order order : orders) {

            for (MenuItem item : order.getItems()) {
                System.out.println("              " + item.getName() + " (" + item.getDescription() + ") - $" + item.getPrice());
                totalBill += item.getPrice();
            }
        }
        System.out.println("                Total: $" + totalBill);
        System.out.print("-------------------------------------------------------------------------------------");
    }

    private static class MenuItem {
        private String name;
        private String description;
        private double price;

        public MenuItem(String name, String description, double price) {
            this.name = name;
            this.description = description;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public double getPrice() {
            return price;
        }
    }

    private static class Order {
        private int tableNumber;
        private String customerName; // Add customer name field
        private ArrayList<MenuItem> items;

        public Order(int tableNumber, String customerName, ArrayList<MenuItem> items) {
            this.tableNumber = tableNumber;
            this.customerName = customerName;
            this.items = items;
        }

        public int getTableNumber() {
            return tableNumber;
        }

        public String getCustomerName() { // Getter for customer name
            return customerName;
        }

        public ArrayList<MenuItem> getItems() {
            return items;
        }
    }
}
