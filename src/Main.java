import java.util.List;
import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;


public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static Budget budget;

    public static void main(String[] args) {
        String filepath = "budget.ser";
        budget = loadBudgetFromFile(filepath);
        if (budget == null) {
            budget = new Budget();
        }
        boolean run = true;
        while (run) {
            System.out.println("""
                    
                    1. Dodaj transakcję
                    2. Usuń transakcję
                    3. Pokaż wszystkie transakcje
                    4. Suma wydatków
                    5. Wyjście
                    6. Dodaj demo transakcje
                    """);
            System.out.println("Wybierz opcję: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    dodajTransakcjeUI();
                    break;
                case 2:
                    deleteTransaction();
                    break;
                case 3:
                    showTransactions();
                    break;
                case 4:
                    System.out.println("Suma wydatków: " + budget.getExpenseTotal());
                    break;
                case 5:
                    saveBudgetToFile(budget, filepath);
                    run = false;
                    break;
                case 6:
                    addDemoTransactions();
                    System.out.println("Dodano demo transakcje");
                    break;
                default:
                    System.out.println("Niezaimplementowane");
                    break;

            }
        }
    }

    private static void dodajTransakcjeUI() {
        System.out.println("1. Dodaj wydatek\n2. Dodaj przychód");
        System.out.println("Wybierz opcję: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                dodajTransakcje("expense");
                break;
            case 2:
                dodajTransakcje("income");
                break;
            default:
                System.out.println("Ta opcja nie istnieje");
        }
    }

    private static void dodajTransakcje(String type) {
        System.out.println("Podaj kwotę: ");
        double amount = scanner.nextDouble();
        System.out.println("Podaj kategorię: ");
        String category = scanner.next();
        System.out.println("Podaj podkategorię: ");
        String subcategory = scanner.next();
        System.out.println("Podaj opis: ");
        String description = scanner.next();
        System.out.println("Podaj lokalizację: ");
        String location = scanner.next();
        System.out.println("Podaj " + (type.equals("expense") ? "odbiorcę" : "płatnika"));
        String person = scanner.next();
        if (type.equals("expense")) {
            Expense expense = new Expense(amount, category, subcategory, description, location, person);
            budget.addExpense(expense);
        } else {
            Income income = new Income(amount, category, subcategory, description, location, person);
            budget.addIncome(income);
        }
    }

    private static void showTransactions() {
        System.out.println("Wydatki: ");
        for (Expense expense : budget.getExpenses()) {
            System.out.println(expense);
        }
        System.out.println("Przychody: ");
        for (Income income : budget.getIncomes()) {
            System.out.println(income);
        }
    }

    private static void addDemoTransactions() {
        Expense expense1 = new Expense(100, "jedzenie", "obiad", "obiad w restauracji", "restauracja 1", "ja");
        Expense expense2 = new Expense(200, "jedzenie", "kolacja", "kolacja w restauracji", "restauracja", "ja");
        Expense expense3 = new Expense(300, "jedzenie", "obiad", "obiad w restauracji", "restauracja 2", "żona");
        Income income1 = new Income(1000, "wynagrodzenie", "pensja", "pensja za styczeń", "praca", "firma");
        Income income2 = new Income(2000, "wynagrodzenie", "pensja", "pensja za luty", "praca", "firma");
        budget.addExpense(expense1);
        budget.addExpense(expense2);
        budget.addExpense(expense3);
        budget.addIncome(income1);
        budget.addIncome(income2);
    }

    private static void deleteTransaction() {
        List<Transaction> transactions = budget.getAllTransactions();
        System.out.println("Wybierz którą transakcję chcesz usunąć:");
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(i + 1 + ". " + transactions.get(i));
        }
        int index = scanner.nextInt();
        budget.removeTransaction(transactions.get(index-1));
        System.out.println("Usnięto transakcję");
    }

    public static void saveBudgetToFile(Budget budget, String filepath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(budget);
            System.out.println("Zapisano budżet do pliku");
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku");
        }
    }

    public static Budget loadBudgetFromFile(String filepath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            return (Budget) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd odczytu z pliku");

            return null;
        }
    }

}

