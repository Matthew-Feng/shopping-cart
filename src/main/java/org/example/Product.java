package org.example;

import java.math.BigDecimal;
import java.util.Objects;

public final class Product {

    private String name;

    private BigDecimal price;

    public Product(String name, BigDecimal price) {
        if (price == null)
            throw new IllegalArgumentException("Product's price should not be null");
        if (price.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Product's price should not be negative");
        if (name == null || name.length() < 1)
            throw new IllegalArgumentException("Product should have a valid name");
       
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name) &&
                price.equals(product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}