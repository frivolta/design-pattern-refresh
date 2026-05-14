public interface PaymentProcessor {
    PaymentResult charge(Money amount, CardDetails card);
    PaymentResult refund(String transactionId, Money amount);
    PaymentStatus status(String transactionId);
}