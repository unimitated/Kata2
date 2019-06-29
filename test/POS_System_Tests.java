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
    void whenAItemIsScannedItAddsToTheTotal() {
        POSItem item = new POSItem();
        POSItem item2 = new POSItem();
        item.price = 3.99;
        posSystem.addOrUpdateScannableItem("Beef", item);
        posSystem.scanItem("Beef");
        item2.price = 5.01;
        posSystem.addOrUpdateScannableItem("Fish", item2);
        posSystem.scanItem("Fish");
        Assertions.assertEquals(9.00, posSystem.getCurrentTotal());
    }


}