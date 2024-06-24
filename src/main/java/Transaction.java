import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private Date date;
    private double amount;
    //    private String account;
    private Category category;

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                '}';
    }

    public Transaction(double amount, Category category) {
        this.date = new Date();
        this.amount = amount;
        this.category = category;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }


    public Category getCategory() {
        return category;
    }

}
