package xyz.pett0.prep;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

public class NonFoodProduct extends Product {
    Double[] prices;

    private NonFoodProduct(String name, Double[] prices) {
        super(name);
        this.prices = prices;
    }

    public static NonFoodProduct fromCsv(Path path) {
        String name;
        Double[] prices;

        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine();
            scanner.nextLine();
            prices = Arrays.stream(scanner.nextLine().split(";"))
                    .map(value -> value.replace(",","."))
                    .map(Double::valueOf)
                    .toArray(Double[]::new);

            scanner.close();

            return new NonFoodProduct(name, prices);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public double getPrice(int year, int month) {
        if (year < 2010 || year > 2022 || month < 1 || month > 12 || (year == 2022 && month > 3)) {
            throw new IndexOutOfBoundsException("Date out of bounds.");
        }
        return prices[(year - 2010) * 12 + month - 1];
    }
}
