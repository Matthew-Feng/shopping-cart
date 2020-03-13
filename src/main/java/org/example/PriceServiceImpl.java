package org.example;

import java.math.BigDecimal;

public class PriceServiceImpl implements PriceService {
    private int scale;
    private int roundingMode;
    private BigDecimal taxRate;

    public PriceServiceImpl() {
    }

    public PriceServiceImpl(int scale, int roundingMode, BigDecimal taxRate) {
        this.scale = scale;
        this.roundingMode = roundingMode;
        this.taxRate = taxRate;
    }


    @Override
    public int getScale() {
        return scale;
    }

    @Override
    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public int getRoundingMode() {
        return this.roundingMode;
    }

    @Override
    public void setRoundingMode(int roundingMode) {
        this.roundingMode = roundingMode;
    }

    @Override
    public BigDecimal getTaxRate() {
        return this.taxRate;
    }

    @Override
    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public BigDecimal calculateTotalPrice(Cart cart) {
        return getTotalNetPriceWithoutRound(cart).multiply(this.taxRate.add(BigDecimal.ONE)).setScale(this.scale, this.roundingMode);
    }

    @Override
    public BigDecimal calculateSaleTax(Cart cart) {

        return getTotalNetPriceWithoutRound(cart).multiply(this.taxRate).setScale(this.scale, this.roundingMode);
    }

    @Override
    public BigDecimal calculateTotalPrice(Cart cart, Offer offer) {
        return getTotalNetPriceWithoutRound(cart).subtract(offer.discount(cart.getProducts()))
                .multiply(this.taxRate.add(BigDecimal.ONE)).setScale(this.scale, this.roundingMode);
    }

    private BigDecimal getTotalNetPriceWithoutRound(Cart cart) {
        return cart.getProducts().stream().map(Product::getPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
