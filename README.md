# Basket Splitter

Basket Splitter is an application written in Java that allows you to split a list of products into different delivery methods and optimally 
grouping so that there are as few different suppliers as possible

## Requirements

- Java 21 or later
- Gradle

## Description of operation

The program first takes data from a json file and stores it in a HashMap, where the key is product(String) and the value is an array of suppliers(List<String>).
Then we run the split function, which:

- First creates a deliveriesProducts map, which contains information about which products can be delivered using which delivery method. For this it uses the getDeliveryProductsMap function, which inverts the data, that is, from now on the key is the delivery method and the value is the lisat of products that can be delivered by a given delivery method.
- It then creates an empty finalDeliveryProducts map, which will store the final breakdown of products by delivery method.
- In a loop, until the deliveriesProducts map is empty, it selects the delivery method that can handle the most products. If there is no such method, it breaks the loop.
- Adds the selected delivery method to the finalDeliveryProducts map with a list of products it can handle.
- Removes the selected delivery method from the deliveriesProducts map.
- For each remaining delivery method in the deliveriesProducts map, removes from the list of products those already assigned to the longestDelivery method.
- At the end of the loop, returns the finalDeliveryProducts map, which contains the final breakdown of products by delivery method.

## How to use

To use the above-mentioned function, just create an instance of the BasketSplitter class and call the split() method on it with the selected list of products.
