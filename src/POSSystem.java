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
            removeScannableItem(name);
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
                if(item.special != 0) {
                    total = applySpecial(item) + total;

                } else {
                    total = (item.price * item.totalWeight) + total;
                }
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
        int result = 0;
        int count = 0;
        switch(item.special) {
            /* Special Case 1 - Buy (currentItem.specialBuyCount), Get (currentItem.specialGetCount) at
                (currentItem.specialDiscount) percentage off */
            case 1:
                count = item.quantity;
                if(item.quantity >= (item.specialBuyCount + item.specialGetCount)) {
                    result = item.quantity / (item.specialBuyCount + item.specialGetCount);
                    total = result*(item.price-(item.price*item.specialDiscount)) + (count-result)*item.price;
                } else {
                    total = count*item.price;
                }
                break;
                /* Special Case 2 - Buy (currentItem.specialBuyCount) at
                (currentItem.specialDiscount) discounted price. */
            case 2:
                result = item.quantity / item.specialBuyCount;
                int regular = item.quantity - (result * item.specialBuyCount);
                if(result > 0) {
                    total = result * item.specialDiscount + (regular * item.price);
                } else {
                    total = item.quantity * item.price;
                }
                break;
            case 3:
                double weight = item.totalWeight;
                if(item.totalWeight >= (item.specialBuyCount + item.specialGetCount)) {
                    result = (int)item.totalWeight / (item.specialBuyCount + item.specialGetCount);
                    total = result*(item.price - (item.price*item.specialDiscount)) + (item.totalWeight-result)*item.price;
                } else {
                    total = item.totalWeight*item.price;
                }
                break;
            default:
                return 0;
        }
    return total;
    }
}

