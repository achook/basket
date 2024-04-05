package com.ocado.basket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketSplitter {
    private final Map<String, List<String>> deliveryOptions;

    public BasketSplitter(String absolutePathToConfigFile) {
        this.deliveryOptions = loadDeliveryOptions(absolutePathToConfigFile);
    }

    public Map<String, List<String>> split(List<String> items) {
        Map<String, List<String>> result = new HashMap<>();

        for (String item : items) {
            List<String> availableOptions = deliveryOptions.get(item);
            if (availableOptions != null) {
                for (String option : availableOptions) {
                    result.computeIfAbsent(option, k -> new ArrayList<>()).add(item);
                }
            }
        }

        return result;
    }

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