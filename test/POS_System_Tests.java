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
        item.price = 3.99;
        posSystem.addOrUpdateScannableItem("Beef", item);
        posSystem.scanItem("Beef");
        Assertions.assertEquals(3.99, posSystem.getCurrentTotal());
    }
}