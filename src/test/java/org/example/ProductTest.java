package org.example;

import org.junit.Test;

import java.math.BigDecimal;

public class ProductTest {

    @Test(expected = IllegalArgumentException.class)
    public void newProductWithNullNameShouldFail() {
        new Product(null, BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newProductWithNullPriceShouldFail() {
        new Product("name", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newProductWithEmptyNameShouldFail() {
        new Product("", BigDecimal.ONE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void newProductWithNagtiveShouldFail() {
        new Product("name", BigDecimal.valueOf(-1));
    }
}
