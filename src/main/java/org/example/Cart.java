package org.example;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

public class Cart {

    private final static int PRICE_SCALE = 2;

    private final static int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    private final static double TAX_RATE = 0.125;


    private List<Product> productList = new LinkedList<>();

    public List<Product> getProductList() {
        return productList;
    }

    public void add(Product product) {
        if (product == null)
            throw new IllegalArgumentException("Product can not be Null");
        if (product.getPrice() == null)
            throw new IllegalArgumentException("Product should have price");
        if (product.getPrice().compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Product's price should not be negative");
        if (product.getName() == null || product.getName().length() < 1)
            throw new IllegalArgumentException("Product should have a valid name");
        this.productList.add(product);
    }

    public int size() {
        return productList.size();
    }

    public BigDecimal getTotalNetPrice() {
        return BigDecimal.valueOf(getTotalNetPriceInDouble()).setScale(PRICE_SCALE, ROUNDING_MODE);
    }

    public BigDecimal getTotalPrice() {
        return BigDecimal.valueOf(getTotalNetPriceInDouble() * (1 + TAX_RATE)).setScale(PRICE_SCALE, ROUNDING_MODE);
    }

    public BigDecimal getTotalSalesTax() {
        return BigDecimal.valueOf(getTotalNetPriceInDouble() * TAX_RATE).setScale(PRICE_SCALE, ROUNDING_MODE);
    }

    private Double getTotalNetPriceInDouble() {
        return productList.stream().map(Product::getPrice).mapToDouble(BigDecimal::doubleValue).sum();
    }
}
