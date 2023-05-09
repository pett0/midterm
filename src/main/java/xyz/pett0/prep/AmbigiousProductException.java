package xyz.pett0.prep;

import java.util.List;

public class AmbigiousProductException extends Exception {
    private final List<String> productList;
    public AmbigiousProductException(List<String> productList) {
        this.productList = productList;
    }
    @Override
    public String getMessage() {
        return "Ambigious product found: " + productList;
    }

    @Override
    public void printStackTrace() {
        System.err.println("AmbigiousProductException: " + getMessage());
        for (StackTraceElement element : getStackTrace()) {
            System.err.println("\tat " + element);
        }
    }
}
