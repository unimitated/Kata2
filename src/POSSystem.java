import java.util.Dictionary;
import java.util.HashMap;


public class POSSystem {

    private HashMap<String, POSItem> availableItemMap = new HashMap<>();
    private HashMap<String, POSItem> currentScannedItems = new HashMap<>();


    void POS_System() {

    }


    /* POSItem variables
        double markdownAmount - defaults to 0
        int special - defaults to 0
        double price - defaults to 0

        adding item not in hashmap will result in item being added, adding item
        currently in hashmap will result in item being updated with passed data.
     */
    public void addOrUpdateScannableItem(String name, POSItem item) {
        if(!availableItemMap.containsKey(name)) {
            availableItemMap.put(name, item);
        } else {
            availableItemMap.remove(name);
            availableItemMap.put(name, item);
        }

    }

    public void removeScannableItem(String name) {
        availableItemMap.remove(name);
    }

    public void scanItem(String item) {
        currentScannedItems.put(item, availableItemMap.get(item));
    }

    public double getCurrentTotal() {
        double total = 0;
        for (POSItem item : currentScannedItems.values()) {
            total = item.price + total;
        }
        return total;
    }
}

