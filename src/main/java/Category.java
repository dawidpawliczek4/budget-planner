import java.io.Serializable;

public class Category implements Serializable {
    private String name;
    private float limit;

    @Override
    public String toString() {
        return name;
    }

    public Category(String name, float limit) {
        this.limit = limit;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public float getLimit() {
        return limit;
    }

    public void setLimit(float limit) {
        this.limit = limit;
    }

}
