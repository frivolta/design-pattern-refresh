# Exercise 13 — Adapter

## Scenario
Your company is integrating two third-party payment gateways (Stripe and PayPal) into a unified checkout system. Each SDK has a completely different API shape. You need a common interface so the rest of your code doesn't care which gateway is behind the scenes.

## What to build

### Target interface
```java
public interface PaymentProcessor {
    PaymentResult charge(Money amount, CardDetails card);
    PaymentResult refund(String transactionId, Money amount);
    PaymentStatus status(String transactionId);
}
```

### Legacy / third-party classes (simulate these — do NOT modify them)
```java
// Stripe-like SDK
public class StripeApi {
    public StripeCharge createCharge(long amountInCents, String currency, String cardToken) { ... }
    public StripeRefund createRefund(String chargeId, long amountInCents) { ... }
    public String getChargeStatus(String chargeId) { ... } // returns "succeeded", "pending", "failed"
}

// PayPal-like SDK
public class PayPalSdk {
    public PayPalOrder capturePayment(double amount, String currencyCode, Map<String,String> payerInfo) { ... }
    public boolean voidTransaction(String orderId, double amount) { ... }
    public PayPalOrder getOrder(String orderId) { ... }
}
```

### Adapters
- `StripePaymentAdapter implements PaymentProcessor` — translates between your domain objects and the Stripe API
- `PayPalPaymentAdapter implements PaymentProcessor` — translates between your domain objects and the PayPal API

### Value objects
- `Money(BigDecimal amount, Currency currency)`
- `CardDetails(String number, int expMonth, int expYear, String cvv)`
- `PaymentResult(boolean success, String transactionId, String message)`
- `PaymentStatus` enum: `PENDING, COMPLETED, FAILED, REFUNDED`

## Twist
Add a `ResilientPaymentAdapter` that wraps any `PaymentProcessor` and adds:
1. Automatic retry (up to 3 attempts) on transient failures
2. Timeout tracking — if a call takes > 5 seconds, log a warning
3. Fallback — constructor accepts a primary and fallback `PaymentProcessor`; if primary fails all retries, try fallback once

This demonstrates adapter composition (adapter wrapping adapter).

## Verify
```
StripePaymentAdapter stripe = new StripePaymentAdapter(new StripeApi());
PayPalPaymentAdapter paypal = new PayPalPaymentAdapter(new PayPalSdk());
PaymentProcessor processor = new ResilientPaymentAdapter(stripe, paypal);

PaymentResult result = processor.charge(Money.of("49.99", "EUR"), card);
// → Stripe called first. If it fails 3 times, PayPal is used as fallback.
// → PaymentResult has a valid transactionId regardless of which gateway succeeded.
```
