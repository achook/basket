package com.ocado.basket;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketSplitterTest {

    @Test
    public void testSplit1() {
        // Setup
        String absolutePathToConfigFile = "config.json";
        BasketSplitter basketSplitter = new BasketSplitter(absolutePathToConfigFile);

        List<String> itemsInBasket = Arrays.asList("Cocoa Butter", "Tart - Raisin And Pecan", "Table Cloth 54x72 White", "Flower - Daisies", "Fond - Chocolate", "Cookies - Englishbay Wht");

        // Expected result
        Map<String, List<String>> expectedResult = new HashMap<>();

        expectedResult.put("Pick-up point", Arrays.asList("Fond - Chocolate"));
        expectedResult.put("Courier", Arrays.asList("Cocoa Butter", "Tart - Raisin And Pecan", "Table Cloth 54x72 White", "Flower - Daisies", "Cookies - Englishbay Wht"));

        // Execute
        Map<String, List<String>> result = basketSplitter.split(itemsInBasket);

        // Assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSplit2() {
        // Setup
        String absolutePathToConfigFile = "config.json";
        BasketSplitter basketSplitter = new BasketSplitter(absolutePathToConfigFile);

        // ["Fond - Chocolate", "Chocolate - Unsweetened", "Nut - Almond, Blanched, Whole", "Haggis", "Mushroom - Porcini Frozen", "Cake - Miini Cheesecake Cherry", "Sauce - Mint", "Longan", "Bag Clear 10 Lb", "Nantucket - Pomegranate Pear", "Puree - Strawberry", "Numi - Assorted Teas", "Apples - Spartan", "Garlic - Peeled", "Cabbage - Nappa", "Bagel - Whole White Sesame", "Tea - Apple Green Tea"]
        List<String> itemsInBasket = Arrays.asList("Fond - Chocolate", "Chocolate - Unsweetened", "Nut - Almond, Blanched, Whole", "Haggis", "Mushroom - Porcini Frozen", "Cake - Miini Cheesecake Cherry", "Sauce - Mint", "Longan", "Bag Clear 10 Lb", "Nantucket - Pomegranate Pear", "Puree - Strawberry", "Numi - Assorted Teas", "Apples - Spartan", "Garlic - Peeled", "Cabbage - Nappa", "Bagel - Whole White Sesame", "Tea - Apple Green Tea");

        // Expected result
        Map<String, List<String>> expectedResult = new HashMap<>();

        //  <{Same day delivery=[Sauce - Mint, Numi - Assorted Teas, Garlic - Peeled], Courier=[Cake - Miini Cheesecake Cherry], Express Collection=[Fond - Chocolate, Chocolate - Unsweetened, Nut - Almond, Blanched, Whole, Haggis, Mushroom - Porcini Frozen, Longan, Bag Clear 10 Lb, Nantucket - Pomegranate Pear, Puree - Strawberry, Apples - Spartan, Cabbage - Nappa, Bagel - Whole White Sesame, Tea - Apple Green Tea]}>
        expectedResult.put("Same day delivery", Arrays.asList("Sauce - Mint", "Numi - Assorted Teas", "Garlic - Peeled"));
        expectedResult.put("Courier", Arrays.asList("Cake - Miini Cheesecake Cherry"));
        expectedResult.put("Express Collection", Arrays.asList("Fond - Chocolate", "Chocolate - Unsweetened", "Nut - Almond, Blanched, Whole", "Haggis", "Mushroom - Porcini Frozen", "Longan", "Bag Clear 10 Lb", "Nantucket - Pomegranate Pear", "Puree - Strawberry", "Apples - Spartan", "Cabbage - Nappa", "Bagel - Whole White Sesame", "Tea - Apple Green Tea"));

        // Execute
        Map<String, List<String>> result = basketSplitter.split(itemsInBasket);

        // Assert
        assertEquals(expectedResult, result);
    }

    @Test
    public void testSplit3() {
        // Setup
        String absolutePathToConfigFile = "config2.json";
        BasketSplitter basketSplitter = new BasketSplitter(absolutePathToConfigFile);

        List<String> itemsInBasket = Arrays.asList("Steak (300g)", "Carrots (1kg)", "Cold Beer (330ml)", "AA Battery (4 Pcs.)", "Espresso Machine", "Garden Chair");

        // Expected result
        Map<String, List<String>> expectedResult = new HashMap<>();

        expectedResult.put("Express Delivery", Arrays.asList("Steak (300g)", "Carrots (1kg)", "Cold Beer (330ml)", "AA Battery (4 Pcs.)"));
        expectedResult.put("Courier", Arrays.asList("Espresso Machine", "Garden Chair"));

        // Execute
        Map<String, List<String>> result = basketSplitter.split(itemsInBasket);

        // Assert
        assertEquals(expectedResult, result);
    }
}