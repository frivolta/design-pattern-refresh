import java.util.UUID;

public class StripeSdk {
    public StripeCharge createCharge(long amountInCents, String currency, String cardToken) {
        // Simulate API call
        String chargeId = "ch_" + UUID.randomUUID().toString().substring(0, 8);
        return new StripeCharge(chargeId, amountInCents, currency, "succeeded");
    }

    public StripeRefund createRefund(String chargeId, long amountInCents) {
        // Simulate API call
        String refundId = "rf_" + UUID.randomUUID().toString().substring(0, 8);
        return new StripeRefund(refundId, chargeId, amountInCents, "succeeded");
    }

    public String getChargeStatus(String chargeId) {
        // Simulate API call - return "succeeded", "pending", "failed"
        return "succeeded";
    }
}

class StripeCharge {
    private String id;
    private long amount;
    private String currency;
    private String status;

    public StripeCharge(String id, long amount, String currency, String status) {
        this.id = id;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public long getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getStatus() {
        return status;
    }
}

class StripeRefund {
    private String id;
    private String chargeId;
    private long amount;
    private String status;

    public StripeRefund(String id, String chargeId, long amount, String status) {
        this.id = id;
        this.chargeId = chargeId;
        this.amount = amount;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getChargeId() {
        return chargeId;
    }

    public long getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}