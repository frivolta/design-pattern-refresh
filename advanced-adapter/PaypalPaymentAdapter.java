import java.util.Map;

public class PaypalPaymentAdapter implements PaymentProcessor {
    private final PayPalSdk payPalSdk;

    public PaypalPaymentAdapter(PayPalSdk payPalSdk) {
        this.payPalSdk = payPalSdk;
    }

    public PaymentResult charge(Money amount, CardDetails card) {
        PayPalOrder order = this.payPalSdk.capturePayment(amount.amount().doubleValue(), amount.currency(),
                Map.ofEntries(
                        Map.entry("holder", card.getName())));
        return new PaymentResult(order.getStatus());
    }

    public PaymentResult refund(String transactionId, Money amount) {
        payPalSdk.voidTransaction(transactionId, amount.amount().longValue());
        return new PaymentResult("OK");
    };

    public PaymentStatus status(String transactionId) {
        PayPalOrder order = payPalSdk.getOrder(transactionId);
        return new PaymentStatus(order.getStatus());
    };
}
