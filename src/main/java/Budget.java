import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Budget implements Serializable {
    private List<Expense> expenses = new ArrayList<>();
    private List<Income> incomes = new ArrayList<>();

    public List<Expense> getExpenses() {
        return expenses;
    }

    public List<Income> getIncomes() {
        return incomes;
    }

    public double getExpenseTotal() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public double getIncomeTotal () {
        double total = 0;
        for (Income income : incomes) {
            total += income.getAmount();
        }
        return total;
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        transactions.addAll(expenses);
        transactions.addAll(incomes);
        return transactions;
    }

    public void addExpense(Expense expense) {
            expenses.add(expense);
    }

    public void addIncome(Income income) {
            incomes.add(income);
    }

    public void removeTransaction(Transaction transaction) {
        if (transaction instanceof Expense) {
            expenses.remove((Expense) transaction);
        } else if (transaction instanceof Income) {
            incomes.remove((Income) transaction);
        }
    }

}