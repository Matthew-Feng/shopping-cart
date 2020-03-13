package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Buy3Get1FeeOfferTest {

    private Buy3Get1FeeOffer buy3Get1FeeOffer;

    private Product testProductA;

    private Product testProductB;

    private List<Product> products;


    @Before
    public void setup() {
        buy3Get1FeeOffer = new Buy3Get1FeeOffer();
        testProductA = new Product("A", BigDecimal.ONE);
        testProductB = new Product("B", BigDecimal.TEN);
        products = new ArrayList<>();
    }

    @After
    public void tearDown() {
        buy3Get1FeeOffer = null;
        testProductA = null;
        testProductB = null;
        products = null;
    }

    @Test
    public void testWithoutDiscount() {
        addProduct(testProductA, 1);
        assertEquals(0, BigDecimal.ZERO.compareTo(buy3Get1FeeOffer.discount(products)));
    }

    @Test
    public void testWithoutDiscountWithFourProduct() {
        addProduct(testProductA, 2);
        addProduct(testProductB, 2);
        assertEquals(0, BigDecimal.ZERO.compareTo(buy3Get1FeeOffer.discount(products)));
    }

    @Test
    public void testDiscountWithOneProductApplied() {
        buy3Get1FeeOffer.apply(testProductA);
        addProduct(testProductA, 3);
        assertEquals(0, testProductA.getPrice().compareTo(buy3Get1FeeOffer.discount(products)));
    }

    @Test
    public void testDiscountWithTwoProductsOnlyOneProductApplied() {
        buy3Get1FeeOffer.apply(testProductA);
        addProduct(testProductA, 3);
        addProduct(testProductB, 3);
        assertEquals(0, testProductA.getPrice().compareTo(buy3Get1FeeOffer.discount(products)));
    }

    @Test
    public void testDiscountWithTwoProductsBothApplied() {
        buy3Get1FeeOffer.apply(testProductA);
        buy3Get1FeeOffer.apply(testProductB);
        addProduct(testProductA, 4);
        addProduct(testProductB, 4);
        BigDecimal discountA = testProductA.getPrice();
        BigDecimal discountB = testProductB.getPrice();
        assertEquals(0, discountA.add(discountB).compareTo(buy3Get1FeeOffer.discount(products)));
    }

    @Test
    public void testEmptyProductsList() {
        buy3Get1FeeOffer.apply(testProductA);
        assertEquals(0, BigDecimal.ZERO.compareTo(buy3Get1FeeOffer.discount(null)));
        assertEquals(0, BigDecimal.ZERO.compareTo(buy3Get1FeeOffer.discount(new ArrayList<>(0))));
    }

    private void addProduct(Product product, int number) {
        for (int i = 0; i < number; i++) {
            products.add(product);
        }
    }

}
