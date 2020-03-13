package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class CartTest {

    private Cart cart;

    private Product doveSoap;

    private Product axeDeo;

    @Before
    public void setup() {
        cart = new Cart();
        doveSoap = new Product("Dove Soap", new BigDecimal("39.99"));
        axeDeo = new Product("Axe Deo", new BigDecimal("99.99"));
    }

    @After
    public void tearDown() {
        cart = null;
        doveSoap = null;
        axeDeo = null;
    }

    @Test
    public void testCartEmptyAtBeginning() {
        assertEquals(0, cart.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullToCart() {
        cart.add(null, 1);
        assertEquals(0, cart.size());
    }

    @Test
    public void testAddingProductIntoCart() {
        cart.add(doveSoap, 5);
        assertEquals(5, cart.size());
    }

    @Test
    public void addProductShouldWork() {
        cart.add(doveSoap, 3);
        cart.add(axeDeo, 2);
        assertEquals(5, cart.size());
    }


}
