package xyz.pett0.prep;


import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class FoodProduct extends Product {
    private final HashMap<String, Double[]> priceMap;

    public FoodProduct(String name, HashMap<String, Double[]> priceMap) {
        super(name);
        this.priceMap = priceMap;
    }

    public static FoodProduct fromCsv(Path path) {
        String name;
        String region;
        HashMap<String, Double[]> priceMap = new HashMap<>();
        try {
            Scanner scanner = new Scanner(path);
            name = scanner.nextLine();
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                List<String> data = new ArrayList<>(Arrays.asList(scanner.nextLine().split(";")));
                region = data.get(0);
                data.remove(0);
                priceMap.put(region, data.stream()
                        .map(str -> str.replace(",", "."))
                        .map(Double::valueOf)
                        .toArray(Double[]::new));
            }
            scanner.close();
            return new FoodProduct(name, priceMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getPrice(int year, int month) {
        if (year < 2010 || year > 2022 || month < 1 || month > 12 || (year == 2022 && month > 3)) {
            throw new IndexOutOfBoundsException("Date out of bounds.");
        }

        return priceMap.values()
                .stream()
                .map(prices -> prices[(year - 2010) * 12 + month - 1])
                .mapToDouble(Double::doubleValue)
                .average().orElse(-1.f);
    }
    public double getPrice(int year, int month, String province) {
        if (year < 2010 || year > 2022 || month < 1 || month > 12 || (year == 2022 && month > 3)) {
            throw new IndexOutOfBoundsException("Date out of bounds.");
        }
        if (!priceMap.containsKey(province)) {
            throw new IndexOutOfBoundsException("No such province.");
        }

        return priceMap.get(province)[(year - 2010) * 12 + month - 1];
    }
}
