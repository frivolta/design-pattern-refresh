import java.math.BigDecimal;

public record Money(
        BigDecimal amount,
        String currency) {
    static public Money of(BigDecimal amount, String currency) {
        return new Money(amount, currency);
    }
}