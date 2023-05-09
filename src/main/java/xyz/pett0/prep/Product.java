package xyz.pett0.prep;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public abstract class Product {
    private final String name;
    private static final List<Product> productList = new ArrayList<>();
    public Product(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public static void clearProducts() {
        productList.clear();
    }
    public static void addProducts(Function<Path, Product> fromCsv, Path dirPath) {
        File directory = new File(dirPath.toUri());
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            productList.add(fromCsv.apply(file.toPath()));
        }
    }
    public static Product getProducts(String productName) throws AmbigiousProductException {
        List<Product> selectedProducts = new ArrayList<>();
        for (Product product : productList) {
            if (product.getName().startsWith(productName)) {
                selectedProducts.add(product);
            }
        }
        if (selectedProducts.size() == 0) {
            throw new IndexOutOfBoundsException("No such product.");
        }
        if (selectedProducts.size() > 1) {
            throw new AmbigiousProductException(selectedProducts.stream()
                    .map(Product::getName)
                    .toList());
        }
        return selectedProducts.get(0);
    }
    abstract double getPrice(int year, int month);
}
