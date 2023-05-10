package xyz.pett0.prep;

import java.util.List;

public class AmbiguousProductException extends Exception {
    private final List<String> productList;
    public AmbiguousProductException(List<String> productList) {
        this.productList = productList;
    }
    @Override
    public String getMessage() {
        return "Ambiguous product found: " + productList;
    }

    @Override
    public void printStackTrace() {
        System.err.println("AmbiguousProductException: " + getMessage());
        for (StackTraceElement element : getStackTrace()) {
            System.err.println("\tat " + element);
        }
    }
}
