package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class PriceServiceTest {

    private final static int PRICE_SCALE = 2;

    private final static int ROUNDING_MODE = BigDecimal.ROUND_HALF_UP;

    private final static BigDecimal TAX_RATE = BigDecimal.valueOf(0.125);

    private PriceService priceService;
    
    private Cart cart;

    private Product doveSoap;

    private Product axeDeo;

    @Before
    public void setup() {
        priceService = new PriceServiceImpl(PRICE_SCALE, ROUNDING_MODE, TAX_RATE);
        cart = new Cart();
        doveSoap = new Product("Dove Soap", new BigDecimal("39.99"));
        axeDeo = new Product("Axe Deo", new BigDecimal("99.99"));
    }

    @After
    public void tearDown() {
        priceService = null;
        cart = null;
        doveSoap = null;
        axeDeo = null;
    }

    @Test
    public void priceServiceShouldCalculateTotalPriceForEmptyCart() {
        BigDecimal totalPrice = priceService.calculateTotalPrice(cart);
        assertEquals(new BigDecimal("0.00"), totalPrice);
    }

    @Test
    public void shouldCalculateTotalPriceForCartWithProducts() {
        cart.add(doveSoap);
        priceService.setTaxRate(BigDecimal.ZERO);
        BigDecimal totalPrice = priceService.calculateTotalPrice(cart);
        assertEquals(doveSoap.getPrice(), totalPrice);
        cart.add(doveSoap, 4);
        assertEquals(new BigDecimal("199.95"), priceService.calculateTotalPrice(cart));
    }

    @Test
    public void testAddingAnotherTreeProductsAfterAddingFiveProducts() {
        cart.add(doveSoap, 5);
        cart.add(doveSoap, 3);
        priceService.setTaxRate(BigDecimal.ZERO);
        assertEquals(new BigDecimal("319.92"), priceService.calculateTotalPrice(cart));
    }

    @Test
    public void testAddingTwoDoveSoapsAndTwoAxeDeos() {
        cart.add(doveSoap, 2);
        cart.add(axeDeo, 2);
        long doveSoapCount = cart.getProducts().stream().filter(product -> product.getName().equals(doveSoap.getName()) && product.getPrice().equals(doveSoap.getPrice())).count();
        long axeDeoCount = cart.getProducts().stream().filter(product -> product.getName().equals(axeDeo.getName()) && product.getPrice().equals(axeDeo.getPrice())).count();
        assertEquals(2, doveSoapCount);
        assertEquals(2, axeDeoCount);
        assertEquals("Incorrect Total Sales Tax", new BigDecimal("35.00"), priceService.calculateSaleTax(cart));
        assertEquals("Incorrect Total Price", new BigDecimal("314.96"), priceService.calculateTotalPrice(cart));
    }

    @Test
    public void testThreeDoveSoapsWithOfferTotalPrice() {
        cart.add(doveSoap, 3);
        Offer offer = new Buy3Get1FeeOffer();
        offer.apply(doveSoap);
        assertEquals(new BigDecimal("89.98"), priceService.calculateTotalPrice(cart, offer));
    }

    @Test
    public void testThreeDoveSoapsTreeAxeDeosWithOffers() {
        cart.add(doveSoap, 3);
        cart.add(axeDeo, 3);
        Offer offer = new Buy3Get1FeeOffer();
        offer.apply(doveSoap);
        offer.apply(axeDeo);
        assertEquals(new BigDecimal("314.96"), priceService.calculateTotalPrice(cart, offer));
    }

    @Test
    public void testTwoDoveSoapsWithOffer() {
        cart.add(doveSoap, 2);
        Offer offer = new Buy3Get1FeeOffer();
        offer.apply(doveSoap);
        assertEquals(new BigDecimal("89.98"), priceService.calculateTotalPrice(cart, offer));
    }
}
