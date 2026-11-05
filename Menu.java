import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Menu {

    public static final int APPETIZERS = 1;
    public static final int MAIN_DISH = 2;
    public static final int DESSERT = 3;
    public static final boolean HEART_HEALTHY = true;
    public static final boolean NOT_HEART_HEALTHY = false;

    private ArrayList<MenuItem> menu;

    public Menu() {
        menu = new ArrayList<MenuItem>();
    }

    public void add(MenuItem item) {
        menu.add(item);
    }

    public void add(String name, int category, boolean isHeartHealthy, String price) {
        double newPrice = Double.parseDouble(price);
        add(new MenuItem(name, category, isHeartHealthy, newPrice));
    }

    // Deletes the item the iterator most recently returned from next()
    public void delete(MenuIterator itr) {
        BaseIterator base = (BaseIterator) itr;
        if (base.lastReturned < 0) {
            throw new IllegalStateException("next() has not been called");
        }
        menu.remove(base.lastReturned);
        base.position = base.lastReturned; 
        base.lastReturned = -1;
    }

    public MenuIterator getAllItemsIterator() {
        return new AllItemsIterator();
    }

    public MenuIterator getMenuItemIterator(int itemType) {
        return new CategoryIterator(itemType);
    }

    public MenuIterator getHeartHealthyIterator() {
        return new HeartHealthyIterator();
    }

    // All items under the given price
    public MenuIterator getPriceIterator(String price) {
        return new PriceIterator(Double.parseDouble(price), 0);
    }

    // Items of one category under the given price (example: main dishes under $15)
    public MenuIterator getPriceIterator(int itemType, String price) {
        return new PriceIterator(Double.parseDouble(price), itemType);
    }

    
    private abstract class BaseIterator implements MenuIterator {
        int position = 0;       
        int lastReturned = -1;  

        abstract boolean matches(MenuItem item);

        // advance position to the next matching item
        private void skipToMatch() {
            while (position < menu.size() && !matches(menu.get(position))) {
                position++;
            }
        }

        public boolean hasNext() {
            skipToMatch();
            return position < menu.size();
        }

        public MenuItem next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastReturned = position;
            return menu.get(position++);
        }
    }

    private class AllItemsIterator extends BaseIterator {
        boolean matches(MenuItem item) {
            return true;
        }
    }

    private class CategoryIterator extends BaseIterator {
        private int category;

        CategoryIterator(int category) {
            this.category = category;
        }

        boolean matches(MenuItem item) {
            return item.getCategory() == category;
        }
    }

    private class HeartHealthyIterator extends BaseIterator {
        boolean matches(MenuItem item) {
            return item.isHeartHealthy();
        }
    }

    private class PriceIterator extends BaseIterator {
        private double maxPrice;
        private int category; // 0 = any category

        PriceIterator(double maxPrice, int category) {
            this.maxPrice = maxPrice;
            this.category = category;
        }

        boolean matches(MenuItem item) {
            return item.getPrice() < maxPrice
                && (category == 0 || item.getCategory() == category);
        }
    }
}
