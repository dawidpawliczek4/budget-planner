import java.io.Serializable;

public class Expense extends Transaction implements Serializable {
    private String description;
    private String location;
    private String payee;

    @Override
    public String toString() {
        return "Expense{" +
                "description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", payee='" + payee + '\'' +
                ", date=" + getDate() +
                ", amount=" + getAmount() +
                ", category='" + getCategory() + '\'' +
                '}';
    }

    public Expense(double amount, Category category, String description, String location, String payee) {
        super(amount, category);
        this.description = description;
        this.location = location;
        this.payee = payee;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getPayee() {
        return payee;
    }
}
