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

    @Test
    public void testCartTotalPriceZeroAtBeginning() {
        assertEquals(new BigDecimal("0.00"), cart.getTotalNetPrice());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddingNullToCart() {
        addProducts(null, 1);
        assertEquals(0, cart.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddingProductWithNegtivePriceShouldThrowException() {
        addProducts(new Product("Dove Soap", new BigDecimal("-1")), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddingProductWithOutPriceShouldThrowException() {
        addProducts(new Product("Dove Soap", null), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddingProductWithoutNameShouldThrowException() {
        addProducts(new Product(null, new BigDecimal("1")), 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenAddingProductWithEmptyNameShouldThrowException() {
        addProducts(new Product("", new BigDecimal("1")), 1);
    }

    @Test
    public void testAddingProductIntoCart() {
        addProducts(doveSoap, 5);
        assertEquals(5, cart.size());
    }

    private void addProducts(Product product, int number) {
        for (int i = 0; i < number; i++) {
            cart.add(product);
        }
    }

    @Test
    public void testGetTotalNetPrice() {
        addProducts(doveSoap, 5);
        assertEquals(new BigDecimal("199.95"), cart.getTotalNetPrice());
    }

    @Test
    public void testAddingAnotherTreeProductsAfterAddingFiveProducts() {
        addProducts(doveSoap, 5);
        addProducts(doveSoap, 3);
        assertEquals(8, cart.size());
        assertEquals(new BigDecimal("319.92"), cart.getTotalNetPrice());
    }

    @Test
    public void testAddingTwoDoveSoapsAndTwoAxeDeos() {
        addProducts(doveSoap, 2);
        addProducts(axeDeo, 2);

        long doveSoapCount = cart.getProductList().stream().filter(product -> product.getName().equals(doveSoap.getName()) && product.getPrice().equals(doveSoap.getPrice())).count();
        long axeDeoCount = cart.getProductList().stream().filter(product -> product.getName().equals(axeDeo.getName()) && product.getPrice().equals(axeDeo.getPrice())).count();
        assertEquals(2, doveSoapCount);
        assertEquals(2, axeDeoCount);
        assertEquals("Incorrect Total Sales Tax", new BigDecimal("35.00"), cart.getTotalSalesTax());
        assertEquals("Incorrect Total Price", new BigDecimal("314.96"), cart.getTotalPrice());
    }


}
