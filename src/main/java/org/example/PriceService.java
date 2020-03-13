package org.example;

import java.math.BigDecimal;

public interface PriceService {

    int getScale();

    void setScale(int scale);

    int getRoundingMode();

    void setRoundingMode(int roundingMode);

    BigDecimal getTaxRate();

    void setTaxRate(BigDecimal taxRate);

    BigDecimal calculateTotalPrice(Cart cart);

    BigDecimal calculateSaleTax(Cart cart);

    BigDecimal calculateTotalPrice(Cart cart, Offer offer);
}
