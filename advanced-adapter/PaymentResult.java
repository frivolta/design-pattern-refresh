public class PaymentResult {
    private String status;

    PaymentResult(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
