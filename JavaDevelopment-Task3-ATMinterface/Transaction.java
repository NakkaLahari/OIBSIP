import java.time.LocalDateTime;
import java.io.Serializable;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private double amount;
    private String details;
    private LocalDateTime date;

    public Transaction(String type, double amount, String details) {
        this.type = type;
        this.amount = amount;
        this.details = details;
        this.date = LocalDateTime.now();
    }

    public String toString() {
        return type + " | Rs. " + amount +
               " | " + details + " | " + date;
    }
}
