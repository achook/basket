package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;

public class BasketSplitter {
    // This map stores the delivery options available for each product
    private final Map<String, List<String>> deliveryOptions;

    /**
     * The constructor loads the delivery options from a configuration file.
     * @param absolutePathToConfigFile The absolute path to the configuration file.
     */
    public BasketSplitter(String absolutePathToConfigFile) {
        this.deliveryOptions = loadDeliveryOptions(absolutePathToConfigFile);
    }

    /**
     * This method splits the items in the basket into delivery options.
     * It tries to use the least number of delivery options and deliver the most items together.
     * @param itemsInBasket The items in the basket.
     * @return A map where the key is the delivery option and the value is the list of items for that delivery option.
     */
    public Map<String, List<String>> split(List<String> itemsInBasket) {
        /* Given that items is a list of strings, where each string represents a product in the basket,
           and that deliveryOptions is a map where the key is the name of the delivery option
           and the value is a list of products that can be delivered using that option,
           arrange the products into delivery options in such a way that
           1) the least number of delivery options is used
           2) the biggest group of products that can be delivered together is delivered together
         */
        Map<String, List<String>> result = new HashMap<>();
        // Make an editable copy of the items list
        ArrayList<String> items = new ArrayList<>(itemsInBasket);

        // Get a list of all delivery options for the items in the basket
        HashSet<String> allDeliveryOptions = new HashSet<>();
        for (String item : items) {
            List<String> deliveryOptionsForItem = deliveryOptions.get(item);
            if (deliveryOptionsForItem != null) {
                allDeliveryOptions.addAll(deliveryOptionsForItem);
            }
        }

        // While there are still items to deliver
        while (!items.isEmpty()) {
            // Find the delivery option that can deliver the most items
            String bestDeliveryOption = null;
            List<String> bestDeliveryOptionItems = new ArrayList<String>();
            for (String deliveryOption : allDeliveryOptions) {
                List<String> itemsForDeliveryOption = new ArrayList<>();
                for (String item : items) {
                    if (deliveryOptions.get(item) != null && deliveryOptions.get(item).contains(deliveryOption)) {
                        itemsForDeliveryOption.add(item);
                    }
                }
                // If the current delivery option can deliver more items than the best delivery option found so far
                // update the best delivery option
                if (itemsForDeliveryOption.size() > bestDeliveryOptionItems.size()) {
                    bestDeliveryOption = deliveryOption;
                    bestDeliveryOptionItems = itemsForDeliveryOption;
                }
            }

            // Remove the items that will be delivered by the best delivery option
            items.removeAll(bestDeliveryOptionItems);

            // Add the items to the result
            result.put(bestDeliveryOption, bestDeliveryOptionItems);

            // Remove the best delivery option from the list of all delivery options
            allDeliveryOptions.remove(bestDeliveryOption);

        }

        return result;
    }

    /**
     * This method loads the delivery options from a configuration file.
     * @param absolutePathToConfigFile The absolute path to the configuration file.
     * @return A map where the key is the product and the value is the list of delivery options for that product.
     */
    private Map<String, List<String>> loadDeliveryOptions(String absolutePathToConfigFile) {
        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, List<String>>>() {}.getType();

        try (FileReader reader = new FileReader(new File(absolutePathToConfigFile))) {
            return gson.fromJson(reader, type);
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}