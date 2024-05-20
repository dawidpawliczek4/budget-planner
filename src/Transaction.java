import java.io.Serializable;
import java.util.Date;

public class Transaction implements Serializable {
    private Date date;
    private double amount;
    //    private String account;
    private String category;
    private String subcategory;

    @Override
    public String toString() {
        return "Transaction{" +
                "date=" + date +
                ", amount=" + amount +
                ", category='" + category + '\'' +
                ", subcategory='" + subcategory + '\'' +
                '}';
    }

    public Transaction(double amount, String category, String subcategory) {
        this.date = new Date();
        this.amount = amount;
        this.category = category;
        this.subcategory = subcategory;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }


    public String getCategory() {
        return category;
    }

    public String getSubcategory() {
        return subcategory;
    }
}
