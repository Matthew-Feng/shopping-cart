package org.example;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Cart {

    private List<Product> products = new LinkedList<>();

    public List<Product> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public void add(Product product) {
        if (product == null)
            throw new IllegalArgumentException("Product can not be Null");

        this.products.add(product);
    }

    public int size() {
        return products.size();
    }

    public void add(Product product, int number) {
        for (int i = 0; i < number; i++) {
            this.add(product);

        }
    }
}
