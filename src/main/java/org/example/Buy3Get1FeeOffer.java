package org.example;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Buy3Get1FeeOffer implements Offer {

    private Set<Product> applicableProducts = new HashSet<>();

    @Override
    public BigDecimal discount(List<Product> products) {
        BigDecimal discount = BigDecimal.ZERO;
        if (products == null || products.size() == 0) {
            return discount;
        }
        HashMap<String, Integer> countMap = new HashMap<>(applicableProducts.size());
        for (Product product : products) {
            if (!applicableProducts.contains(product)) {
                continue;
            }
            Integer count = countMap.getOrDefault(product.getName(), 0);
            if (countMap.getOrDefault(product.getName(), 0) == 2) {
                countMap.put(product.getName(), 0);
                discount = discount.add(product.getPrice());
            } else {
                countMap.put(product.getName(), count + 1);
            }
        }

        return discount;
    }

    @Override
    public void apply(Product product) {
        applicableProducts.add(product);
    }
}
