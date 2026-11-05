import java.util.ArrayList;
public class Menu {
    
public static final int APPETIZERS = 1;
public static final int MAIN_DISH = 2;
public static final int DESSERT = 3;
public static final boolean HEART_HEALTHY = true;
public static final boolean NOT_HEART_HEALTHY = false;

private ArrayList<MenuItem> menu;

public Menu(){
    menu = new ArrayList<MenuItem>();
}

public void add(MenuItem item){
    menu.add(item);
}
public void add(String name, int category, boolean isHeartHealthy, String price){
    double newPrice = Double.parseDouble(price);
    MenuItem newItem = new MenuItem(name, category, isHeartHealthy, newPrice);
    add(newItem);
}

public MenuIterator getAllItemsIterator() {
    return new AllItemsIterator();
}

private class AllItemsIterator implements MenuIterator{
int position = 0;

public boolean hasNext(){
if(position < menu.size()){
    return true;
}
return false;

}
public MenuItem next(){
MenuItem currentItem = menu.get(position);
position++;
return currentItem;

}
}
}
