public class PaymentResilientAdapter implements PaymentProcessor {
    StripePaymentAdapter stripePaymentAdapter;
    PaypalPaymentAdapter paypalPaymentAdapter;
    private final int MAX_RETRY = 3;

    PaymentResilientAdapter(StripePaymentAdapter stripePaymentAdapter, PaypalPaymentAdapter paypalPaymentAdapter) {
        this.stripePaymentAdapter = stripePaymentAdapter;
        this.paypalPaymentAdapter = paypalPaymentAdapter;
    }

    public PaymentResult charge(Money amount, CardDetails card) {
        int retries = 0;

        while (retries < MAX_RETRY) {
            PaymentResult res = this.stripePaymentAdapter.charge(amount, card);
            if ("OK".equals(res.getStatus())) {
                return res;
            }
            retries++;
        }

        PaymentResult res = this.paypalPaymentAdapter.charge(amount, card);
        return res;
    }

    public PaymentResult refund(String transactionId, Money amount) {
        return null;
    }

    public PaymentStatus status(String transactionId) {
        return null;
    }
}
