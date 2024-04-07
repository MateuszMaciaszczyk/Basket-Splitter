package com.ocado.basket;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class BasketSplitter {
    private final Map<String, List<String>> config;

    public BasketSplitter(String absolutePathToConfigFile) {
        config = new HashMap<>();
        try {
            String content = new String(Files.readAllBytes(Paths.get(absolutePathToConfigFile)));
            JSONObject jsonObject = new JSONObject(content);

            for (String key : jsonObject.keySet()) {
                JSONArray jsonArray = jsonObject.getJSONArray(key);
                List<String> values = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    values.add(jsonArray.getString(i));
                }
                config.put(key, values);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Map<String, List<String>> split(List<String> items) {
        Map<String, List<String>> deliveriesProducts = getDeliveryProductsMap(items);
        Map<String, List<String>> finalDeliveryProducts = new HashMap<>();
        String longestDelivery = null;

        while (!deliveriesProducts.isEmpty()) {
            longestDelivery = null;
            int longestDeliverySize = 0;

            for (Map.Entry<String, List<String>> entry : deliveriesProducts.entrySet()) {
                if (entry.getValue().size() > longestDeliverySize) {
                    longestDelivery = entry.getKey();
                    longestDeliverySize = entry.getValue().size();
                }
            }

            if (longestDelivery == null) {
                break;
            }

            finalDeliveryProducts.put(longestDelivery, deliveriesProducts.get(longestDelivery));
            deliveriesProducts.remove(longestDelivery);
            for (List<String> products : deliveriesProducts.values()) {
                products.removeAll(finalDeliveryProducts.get(longestDelivery));
            }
        }

        return finalDeliveryProducts;
    }

    // For each delivery, get the list of products that can be delivered
    private Map<String, List<String>> getDeliveryProductsMap(List<String> items) {
        Map<String, List<String>> output = new HashMap<>();

        for (String item : items) {
            for (String delivery : config.get(item)) {
                if (output.containsKey(delivery)) {
                    output.get(delivery).add(item);
                } else {
                    List<String> itemsList = new ArrayList<>();
                    itemsList.add(item);
                    output.put(delivery, itemsList);
                }
            }
        }
        return output;
    }
}