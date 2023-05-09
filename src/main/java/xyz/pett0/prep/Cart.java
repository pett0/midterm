package xyz.pett0.prep;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cart {
    private final List<Product> productList = new ArrayList<>();
    public void addProduct(Product product, int amount) {
        productList.addAll(Collections.nCopies(amount, product));
    }
    public double getPrice(int year, int month) throws IndexOutOfBoundsException {
        return productList.stream()
                .mapToDouble(product -> product.getPrice(year, month))
                .sum();
    }
    public double getInflation(int year1, int month1, int year2, int month2) {
        double price1 = getPrice(year1, month1);
        double price2 = getPrice(year2, month2);
        int months = (year2 - year1) * 12 + (month2 - month1);
        return (price2 - price1) / price1 * 100 / months * 12;
    }
}
