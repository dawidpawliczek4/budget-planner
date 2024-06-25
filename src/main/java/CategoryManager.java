import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager implements Serializable {
    private List<Category> categories = new ArrayList<Category>();

//    public CategoryManager() {
//        categories.add(new Category("jedzenie", 1000));
//        categories.add(new Category("mieszkanie", 3000));
//        categories.add(new Category("transport", 200));
//        categories.add(new Category("rozrywka", 1000));
//    }

    public List<Category> getCategories() {
        return categories;
    }

    public Category addCategory(String category, float limit) {
        Category newCategory = new Category(category.toLowerCase(), limit);
        categories.add(newCategory);
        return newCategory;
    }

    public Category getCategory(String category) {
        for (Category c : categories) {
            if (c.getName().equals(category.toLowerCase())) {
                return c;
            }
        }
        return null;
    }

    public void removeCategory(String category, Budget budget) {
        for (Category c : categories) {
            if (c.getName().equals(category.toLowerCase())) {
                categories.remove(c);
                for (Transaction t : budget.getAllTransactions()) {
                    if (t.getCategory().getName().equals(category.toLowerCase())) {
                        budget.removeTransaction(t);
                    }
                }
                break;
            }
        }
    }
}
