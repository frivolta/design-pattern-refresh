import java.util.Map;
import java.util.UUID;

public class PayPalSdk {
    public PayPalOrder capturePayment(double amount, String currencyCode, Map<String, String> payerInfo) {
        // Simulate API call
        String orderId = "order_" + UUID.randomUUID().toString().substring(0, 8);
        return new PayPalOrder(orderId, amount, currencyCode, "COMPLETED");
    }

    public boolean voidTransaction(String orderId, double amount) {
        // Simulate API call - return true if successful
        return true;
    }

    public PayPalOrder getOrder(String orderId) {
        // Simulate API call
        return new PayPalOrder(orderId, 0.0, "USD", "COMPLETED");
    }
}

class PayPalOrder {
    private String id;
    private double amount;
    private String currency;
    private String status;

    public PayPalOrder(String id, double amount, String currency, String status) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }
}