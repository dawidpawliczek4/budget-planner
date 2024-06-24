import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserManager userManager = new UserManager();
    private static User user;
    private static CsvExporter csvExporter = new CsvExporter();

    public static void main(String[] args) {
        boolean notLogged = true;
        while (notLogged) {
            System.out.println("1. Zaloguj\n2. Zarejestruj\n3. Wyjście");
            System.out.println("Wybierz opcję: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    user = login();
                    if (user == null) {
                        System.out.println("Niepoprawne dane logowania");
                        break;
                    }
                    notLogged = false;
                    break;
                case 2:
                    register();
                    notLogged = false;
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Niepoprawna opcja");
                    break;
            }
        }
        boolean run = true;
        while (run) {
            System.out.println("""
                    
                    0. Zarządzaj kategoriami
                    1. Dodaj transakcję
                    2. Usuń transakcję
                    3. Pokaż wszystkie transakcje
                    4. Ustaw przewidywane wydatki
                    5. Suma wydatków wg. kategori
                    6. Suma wydatków
                    7. Suma przychodów
                    8. Bilans
                    9. Wyjście
                    10. Dodaj demo transakcje
                    11. Eksportuj do pliku CSV
                    """);
            System.out.println("Wybierz opcję: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.out.println("1. Dodaj kategorię\n2. Usuń kategorię");
                    System.out.println("Wybierz opcję: ");
                    int categoryChoice = scanner.nextInt();
                    switch (categoryChoice) {
                        case 1:
                            System.out.println("Podaj nazwę kategorii: ");
                            String category = scanner.next();
                            System.out.println("Podaj limit wydatków:");
                            float limit = scanner.nextFloat();
                            user.categoryManager.addCategory(category, limit);
                            break;
                        case 2:
                            System.out.println("Podaj nazwę kategorii: ");
                            String categoryToRemove = scanner.next();
                            user.categoryManager.removeCategory(categoryToRemove);
                            break;
                        default:
                            System.out.println("Niepoprawna opcja");
                            break;
                    }
                    break;
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
                    System.out.println("Wybierz kategorię: ");
                    user.categoryManager.getCategories().forEach(System.out::println);
                    String category = scanner.next();
                    for (Category c : user.categoryManager.getCategories()) {
                        if (c.getName().equals(category)) {
                            System.out.println("Podaj przewidywane wydatki: ");
                            c.setLimit(scanner.nextFloat());
                            break;
                        }
                    }
                    System.out.println("Niepoprawna kategoria");
                    break;
                case 5:
                    showSpendingByCategory();
                case 6:
                    System.out.println("Suma wydatków: " + user.budget.getExpenseTotal());
                    break;
                case 7:
                    System.out.println("Suma przychodów: " + user.budget.getIncomeTotal());
                    break;
                case 8:
                    System.out.println("Bilans: " + (user.budget.getIncomeTotal() - user.budget.getExpenseTotal()));
                    break;
                case 9:
                    run = false;
                    break;
                case 10:
                    addDemoTransactions();
                    System.out.println("Dodano demo transakcje");
                    break;
                case 11:
                    exportToCsv();
                    break;
                default:
                    System.out.println("Niezaimplementowane");
                    break;

            }
        }

        save();
    }

    private static void save() {
        userManager.saveUserBudgetToFile(user);
        userManager.saveUsersToFile();
    }


    private static User login() {
        System.out.println("Podaj login: ");
        String login = scanner.next();
        System.out.println("Podaj hasło: ");
        String password = scanner.next();
        return userManager.login(login, password);
    }

    private static void register() {
        System.out.println("Podaj login: ");
        String login = scanner.next();
        System.out.println("Podaj hasło: ");
        String password = scanner.next();
        user = userManager.addUser(login, password);
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
        System.out.println("Podaj kategorię z listy dostępnych: ");
        user.categoryManager.getCategories().forEach(System.out::println);
        String categoryString = scanner.next();
        Category category = user.categoryManager.getCategory(categoryString);
        if (category == null) {
            System.out.println("Niepoprawna kategoria");
            return;
        }
        System.out.println("Podaj opis: ");
        String description = scanner.next();
        System.out.println("Podaj lokalizację: ");
        String location = scanner.next();
        System.out.println("Podaj " + (type.equals("expense") ? "odbiorcę" : "płatnika"));
        String person = scanner.next();
        if (type.equals("expense")) {
            Expense expense = new Expense(amount, category, description, location, person);
            user.budget.addExpense(expense);
        } else {
            Income income = new Income(amount, category, description, location, person);
            user.budget.addIncome(income);
        }
    }

    private static void showTransactions() {
        System.out.println("Wydatki: ");
        for (Expense expense : user.budget.getExpenses()) {
            System.out.println(expense);
        }
        System.out.println("Przychody: ");
        for (Income income : user.budget.getIncomes()) {
            System.out.println(income);
        }
    }

    private static void addDemoTransactions() {
        Category category1 = user.categoryManager.addCategory("jedzenie", 1000);
        Category category2 = user.categoryManager.addCategory("wynagrodzenie", 3000);
        Expense expense1 = new Expense(100, category1,  "obiad w restauracji", "restauracja 1", "ja");
        Expense expense2 = new Expense(200, category1,  "kolacja w restauracji", "restauracja", "ja");
        Expense expense3 = new Expense(300, category1, "obiad w restauracji", "restauracja 2", "żona");
        Income income1 = new Income(1000, category2,  "pensja za styczeń", "praca", "firma");
        Income income2 = new Income(2000, category2,  "pensja za luty", "praca", "firma");
        user.budget.addExpense(expense1);
        user.budget.addExpense(expense2);
        user.budget.addExpense(expense3);
        user.budget.addIncome(income1);
        user.budget.addIncome(income2);
    }

    private static void deleteTransaction() {
        List<Transaction> transactions = user.budget.getAllTransactions();
        System.out.println("Wybierz którą transakcję chcesz usunąć:");
        for (int i = 0; i < transactions.size(); i++) {
            System.out.println(i + 1 + ". " + transactions.get(i));
        }
        int index = scanner.nextInt();
        user.budget.removeTransaction(transactions.get(index-1));
        System.out.println("Usunięto transakcję");
    }

    private static void showSpendingByCategory () {
        for (Category category : user.categoryManager.getCategories()) {
            double total = 0;
            for (Expense expense : user.budget.getExpenses()) {
                if (expense.getCategory().getName().equals(category.getName())) {
                    total += expense.getAmount();
                }
            }
            System.out.println(category.getName() + ": " + total + ", limit: " + category.getLimit());
        }
    }

    private static void exportToCsv() {
        System.out.println("Podaj ścieżkę do pliku: ");
        String filePath = scanner.next();
        List<Transaction> transactions = user.budget.getAllTransactions();
        csvExporter.exportTransactionsToCsv(transactions, filePath);
        System.out.println("Dane zostały wyeksportowane do pliku: " + filePath);
    }

}