import java.io.Serializable;

public class Income extends Transaction implements Serializable {
    private String description;
    private String location;
    private String payer;

    public Income(double amount, Category category, String description, String location, String payer) {
        super(amount, category);
        this.description = description;
        this.location = location;
        this.payer = payer;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getPayer() {
        return payer;
    }
}
