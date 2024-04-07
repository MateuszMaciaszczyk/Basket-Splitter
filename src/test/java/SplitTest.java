import com.ocado.basket.BasketSplitter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SplitTest {

    @Test
    public void testSplit() {
        BasketSplitter splitter = new BasketSplitter("src/main/resources/config.json");

        List<String> items = Arrays.asList("Fond - Chocolate", "Chocolate - Unsweetened", "Nut - Almond, Blanched, Whole", "Haggis", "Mushroom - Porcini Frozen", "Cake - Miini Cheesecake Cherry", "Sauce - Mint", "Longan", "Bag Clear 10 Lb", "Nantucket - Pomegranate Pear", "Puree - Strawberry", "Numi - Assorted Teas", "Apples - Spartan", "Garlic - Peeled", "Cabbage - Nappa", "Bagel - Whole White Sesame", "Tea - Apple Green Tea");

        Map<String, List<String>> result = splitter.split(items);

        String expected = "{Same day delivery=[Sauce - Mint, Numi - Assorted Teas, Garlic - Peeled], Courier=[Cake - Miini Cheesecake Cherry], Express Collection=[Fond - Chocolate, Chocolate - Unsweetened, Nut - Almond, Blanched, Whole, Haggis, Mushroom - Porcini Frozen, Longan, Bag Clear 10 Lb, Nantucket - Pomegranate Pear, Puree - Strawberry, Apples - Spartan, Cabbage - Nappa, Bagel - Whole White Sesame, Tea - Apple Green Tea]}";
        assertEquals(3, result.size());
        assertEquals(3, result.get("Same day delivery").size());
        assertEquals(1, result.get("Courier").size());
        assertEquals(13, result.get("Express Collection").size());
        assertEquals(result.toString(), expected);
    }

    @Test
    public void testSpit2() {
        BasketSplitter splitter = new BasketSplitter("src/main/resources/config.json");

        List<String> items = Arrays.asList("Cocoa Butter", "Tart - Raisin And Pecan", "Table Cloth 54x72 White", "Flower - Daisies", "Fond - Chocolate", "Cookies - Englishbay Wht");

        Map<String, List<String>> result = splitter.split(items);
        String expected = "{Pick-up point=[Fond - Chocolate], Courier=[Cocoa Butter, Tart - Raisin And Pecan, Table Cloth 54x72 White, Flower - Daisies, Cookies - Englishbay Wht]}";

        assertEquals(2, result.size());
        assertEquals(result.toString(), expected);
    }
}
