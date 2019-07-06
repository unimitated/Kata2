import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class POS_System_Tests {

    private POSSystem posSystem;

    @BeforeEach
    void setUp() {
        posSystem = new POSSystem();

    }

    @Test
    void addScannableItem() {
        POSItem item = new POSItem();
        item.name = "Bread";
        posSystem.addOrUpdateScannableItem("Bread", item);

        Assertions.assertEquals(posSystem.getItemInfo("Bread").name, item.name);
    }

    @Test
    void removeScannableItem() {
        addScannableItem();
        posSystem.removeScannableItem("Bread");
        Assertions.assertEquals(posSystem.getItemInfo("Bread"), null);
    }


    @Test
    void whenAUnitItemHasAMarkdown() {
        POSItem item = new POSItem();

        item.price = 4.00;
        item.markdownAmount = 2.00;
        posSystem.addOrUpdateScannableItem("Beef", item);
        posSystem.scanItem("Beef", 0);
        posSystem.scanItem("Beef", 0);

        Assertions.assertEquals(4.00, posSystem.getCurrentTotal());
    }

    @Test
    void whenAWeighedItemHasAMarkdown() {
        POSItem item = new POSItem();

        item.price = 4.00;
        item.markdownAmount = 2.00;
        posSystem.addOrUpdateScannableItem("Beef", item);
        posSystem.scanItem("Beef", 3.0);
        posSystem.scanItem("Beef", 2.0);

        Assertions.assertEquals(10.00, posSystem.getCurrentTotal());
    }

    @Test
    void whenAItemIsScannedItAddsToTheTotal() {
        POSItem item = new POSItem();
        POSItem item2 = new POSItem();
        item.price = 3.99;
        posSystem.addOrUpdateScannableItem("Beef", item);
        posSystem.scanItem("Beef", 0);
        item2.price = 5.01;
        posSystem.addOrUpdateScannableItem("Fish", item2);
        posSystem.scanItem("Fish", 0);
        Assertions.assertEquals(9.00, posSystem.getCurrentTotal());
    }

    @Test
    void whenAItemAndWeightIsScannedItAddsToTheTotal() {
        POSItem item = new POSItem();
        POSItem item2 = new POSItem();
        item.price = 3.99;
        posSystem.addOrUpdateScannableItem("Beef", item);
        posSystem.scanItem("Beef", 0);
        item2.price = 5.01;
        posSystem.addOrUpdateScannableItem("Fish", item2);
        posSystem.scanItem("Fish", 3.0);
        Assertions.assertEquals(19.02, posSystem.getCurrentTotal());
    }

    @Test
    void whenAUnitItemIsRemovedTheTotalIsUpdated() {
        whenAItemIsScannedItAddsToTheTotal();
        posSystem.voidScannedItem("Beef", 0);
        Assertions.assertEquals(5.01, posSystem.getCurrentTotal());
    }

    @Test
    void whenAWeighedItemIsRemovedTheTotalIsUpdated() {
        whenAItemAndWeightIsScannedItAddsToTheTotal();
        posSystem.voidScannedItem("Fish", 2);
        Assertions.assertEquals(9.00, posSystem.getCurrentTotal());
    }
}