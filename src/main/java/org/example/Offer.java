package org.example;

import java.math.BigDecimal;
import java.util.List;

public interface Offer {
    BigDecimal discount(List<Product> products);

    void apply(Product product);
}
