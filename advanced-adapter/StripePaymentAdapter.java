
public class StripePaymentAdapter implements PaymentProcessor {
    private final StripeSdk stripeSdk;

    public StripePaymentAdapter(StripeSdk stripeSdk) {
        this.stripeSdk = stripeSdk;
    }

    public PaymentResult charge(Money amount, CardDetails card) {
        StripeCharge charge = this.stripeSdk.createCharge(amount.amount().longValue(), amount.currency(),
                card.getName());
        return new PaymentResult(charge.getStatus());
    }

    public PaymentResult refund(String transactionId, Money amount) {
        stripeSdk.createRefund(transactionId, amount.amount().longValue());
        return new PaymentResult("OK");
    };

    public PaymentStatus status(String transactionId) {
        String status = stripeSdk.getChargeStatus(transactionId);
        return new PaymentStatus(status);
    };
}
