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
                ", subcategory='" + getSubcategory() + '\'' +
                '}';
    }

    public Expense(double amount, String category, String subcategory, String description, String location, String payee) {
        super(amount, category, subcategory);
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
