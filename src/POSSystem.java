import java.util.Dictionary;
import java.util.HashMap;


 class POSSystem {

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
    void addOrUpdateScannableItem(String name, POSItem item) {
        if(!availableItemMap.containsKey(name)) {
            availableItemMap.put(name, item);
        } else {
            availableItemMap.remove(name);
            availableItemMap.put(name, item);
        }

    }

    void removeScannableItem(String name) {
        availableItemMap.remove(name);
    }

    POSItem getItemInfo(String name) {
        return availableItemMap.get(name);
    }


    void voidScannedItem(String item, double weight) {
        POSItem currentItem = currentScannedItems.get(item);
        currentItem.quantity = currentItem.quantity - 1;
        currentItem.totalWeight = currentItem.totalWeight - weight;

        currentScannedItems.put(item, currentItem);
    }
    /* Tracks total quantity or weight per item, and applies any markdowns available.  Once complete
        it adds the item to the currentScannedItems hashmap.
     */

     void scanItem(String item, double weight) {
        POSItem currentItem = availableItemMap.get(item);
        currentItem.quantity = currentItem.quantity + 1;
        currentItem.totalWeight = weight + currentItem.totalWeight;
        if (!currentItem.markdownApplied) {
            currentItem.price = (currentItem.price - currentItem.markdownAmount);
            currentItem.markdownApplied = true;
        }
        currentScannedItems.put(item, currentItem);
    }

     double getCurrentTotal() {
        double total = 0;
        for (POSItem item : currentScannedItems.values()) {
            if(item.totalWeight > 0) {
                total = (item.price*item.totalWeight) + total;
            } else {
                if(item.special != 0) {
                    total = applySpecial(item) + total;
                } else {
                    total = (item.price*item.quantity) + total;
                }

            }

        }
        return total;
    }

     double applySpecial(POSItem item) {

        double total = 0;
        switch(item.special) {
            /* Special Case 1 - Buy (currentItem.specialBuyCount), Get (currentItem.specialGetCount) at
                (currentItem.specialDiscount) percentage off */
            case 1:
                int count = item.quantity;
                if(item.quantity >= (item.specialBuyCount + item.specialGetCount)) {
                    int result = item.quantity / (item.specialBuyCount + item.specialGetCount);
                    total = result*(item.price*item.specialDiscount) + (count-result)*item.price;
                } else {
                    total = count*item.price;
                }
                break;
            case 2:
                int result = item.quantity / item.specialBuyCount;
                if(result > 0) {
                    total = result * item.specialDiscount;
                } else {
                    total = item.quantity * item.price;
                }
                break;
            default:
                return 0;
        }
    return total;
    }
}

