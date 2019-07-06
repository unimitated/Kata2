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

    public POSItem getItemInfo(String name) {
        return availableItemMap.get(name);
    }


    public void voidScannedItem(String item, double weight) {
        POSItem currentItem = currentScannedItems.get(item);
        currentItem.quantity = currentItem.quantity - 1;
        currentItem.totalWeight = currentItem.totalWeight - weight;

        currentScannedItems.put(item, currentItem);
    }
    /* Tracks total quantity or weight per item, and applies any markdowns available.  Once complete
        it adds the item to the currentScannedItems hashmap.
     */

    public void scanItem(String item, double weight) {
        POSItem currentItem = availableItemMap.get(item);
        currentItem.quantity = currentItem.quantity + 1;
        currentItem.totalWeight = weight + currentItem.totalWeight;
        if (!currentItem.markdownApplied) {
            currentItem.price = (currentItem.price - currentItem.markdownAmount);
            currentItem.markdownApplied = true;
        }
        currentScannedItems.put(item, currentItem);
    }

    public double getCurrentTotal() {
        double total = 0;
        for (POSItem item : currentScannedItems.values()) {
            if(item.totalWeight > 0) {
                total = (item.price*item.totalWeight) + total;
            } else {
                total = (item.price*item.quantity) + total;
            }

        }
        return total;
    }
}

