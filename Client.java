import java.util.Scanner;

public class Client {

    public static void main(String[] args) {

        Menu menu = new Menu();
        Scanner input = new Scanner(System.in);

        menu.add("Bruschetta", Menu.APPETIZERS, Menu.HEART_HEALTHY, "8.00");
        menu.add("Onion Rings", Menu.APPETIZERS, Menu.NOT_HEART_HEALTHY, "12.00");
        menu.add("Steak", Menu.MAIN_DISH, Menu.NOT_HEART_HEALTHY, "30.00");
        menu.add("Grilled Salmon", Menu.MAIN_DISH, Menu.HEART_HEALTHY, "22.00");
        menu.add("Pasta", Menu.MAIN_DISH, Menu.NOT_HEART_HEALTHY, "14.00");
        menu.add("Cake", Menu.DESSERT, Menu.NOT_HEART_HEALTHY, "10.00");
        menu.add("Fruit Cup", Menu.DESSERT, Menu.HEART_HEALTHY, "6.00");

        while (true) {
            printOptions();
            int choice;
            try {
                choice = Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.\n");
                continue;
            }

            switch (choice) {
                case 1:
                    display("ALL MENU ITEMS", menu.getAllItemsIterator());
                    break;
                case 2:
                    display("APPETIZERS", menu.getMenuItemIterator(Menu.APPETIZERS));
                    break;
                case 3:
                    display("MAIN DISHES", menu.getMenuItemIterator(Menu.MAIN_DISH));
                    break;
                case 4:
                    display("DESSERTS", menu.getMenuItemIterator(Menu.DESSERT));
                    break;
                case 5:
                    display("ALL HEART HEALTHY MENU ITEMS", menu.getHeartHealthyIterator());
                    break;
                case 6:
                    System.out.print("Show main dishes under what price? ");
                    String price = input.nextLine().trim();
                    try {
                        display("MAIN DISHES UNDER $" + price,
                                menu.getPriceIterator(Menu.MAIN_DISH, price));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid price.\n");
                    }
                    break;
                case 7:
                    addItem(menu, input);
                    break;
                case 8:
                    removeItem(menu, input);
                    break;
                case 0:
                case 9:
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Please choose 1-8 (0 or 9 to quit).\n");
            }
        }
    }

    private static void printOptions() {
        System.out.println("1 - Display all menu items");
        System.out.println("2 - Display all appetizers");
        System.out.println("3 - Display all main dishes");
        System.out.println("4 - Display all desserts");
        System.out.println("5 - Display all heart healthy items");
        System.out.println("6 - Display all main dishes under a specified price");
        System.out.println("7 - Add menu item");
        System.out.println("8 - Remove menu item");
        System.out.println("0 or 9 - Quit");
        System.out.print("Select an option: ");
    }


    private static void display(String title, MenuIterator itr) {
        System.out.println("\n" + title);
        while (itr.hasNext()) {
            MenuItem item = itr.next();
            System.out.println(item.getName() + " $" + item.getPrice());
        }
        System.out.println();
    }

    private static void addItem(Menu menu, Scanner input) {
        System.out.print("Name: ");
        String name = input.nextLine().trim();
        System.out.print("Category (1=Appetizer, 2=Main dish, 3=Dessert): ");
        int category;
        try {
            category = Integer.parseInt(input.nextLine().trim());
        } catch (NumberFormatException e) {
            category = 0;
        }
        if (category < Menu.APPETIZERS || category > Menu.DESSERT) {
            System.out.println("Invalid category. Item not added.\n");
            return;
        }
        System.out.print("Heart healthy? (y/n): ");
        boolean healthy = input.nextLine().trim().equalsIgnoreCase("y");
        System.out.print("Price: ");
        try {
            menu.add(name, category, healthy, input.nextLine().trim());
            System.out.println(name + " added.\n");
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Item not added.\n");
        }
    }

    private static void removeItem(Menu menu, Scanner input) {
        System.out.println("\nPress Enter to move to the next item, 'd' to delete the item shown.");
        MenuIterator itr = menu.getAllItemsIterator();
        while (itr.hasNext()) {
            MenuItem item = itr.next();
            System.out.print(item.getName() + " $" + item.getPrice() + " > ");
            if (input.nextLine().trim().equalsIgnoreCase("d")) {
                menu.delete(itr);
                System.out.println(item.getName() + " deleted.");
            }
        }
        System.out.println();
    }
}
