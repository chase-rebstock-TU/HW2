public class Client {
    public static void main(String [] args){

    MenuItem item;
    Menu menu = new Menu();

    menu.add("Steak", 2, true, "30.00");
    menu.add("Pasta", 2, true, "20.00");
    menu.add("Onion Rings", 1, false, "12.00");
    menu.add("Cake", 3, false, "10.00");

    MenuIterator itr = menu.getAllItemsIterator();
    System.out.println("ALL MENU ITEMS");

    while (itr.hasNext()){
        item = itr.next();
        System.out.println(item.getName() + "  $" + item.getPrice());
        }

    }

}
