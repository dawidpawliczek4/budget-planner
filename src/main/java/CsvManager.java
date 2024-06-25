import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvManager {

    public void exportTransactionsToCsv(List<Transaction> transactions, String filepath) {
        try {
            System.out.println("Zapisuje do pliku CSV: " + filepath);
            System.out.println("Transakcje: " + transactions);
            FileWriter out = new FileWriter(filepath);
            CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader("Kwota", "Data", "Kategoria", "Typ", "Opis", "Lokalizacja", "Osoba"));
            for (Transaction transaction : transactions) {
                if (transaction instanceof Expense) {
                    printer.printRecord(transaction.getAmount(), transaction.getDate(), transaction.getCategory().getName(), "Wydatek", ((Expense) transaction).getDescription(), ((Expense) transaction).getLocation(), ((Expense) transaction).getPayee());
                } else if (transaction instanceof Income) {
                    printer.printRecord(transaction.getAmount(), transaction.getDate(), transaction.getCategory().getName(), "Przychód", ((Income) transaction).getDescription(), ((Income) transaction).getLocation(), ((Income) transaction).getPayer());
                }
            }
            printer.flush();
        } catch (IOException e) {
            System.err.println("Bład zapisu do pliku CSV: " + e.getMessage());
        }
    }


    public void importTransactionsFromCsv(String filepath, User user) {
        try {
            user.budget.removeAllTransactions();
            FileReader in = new FileReader(filepath);
            CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT.withFirstRecordAsHeader());
            for (CSVRecord record : parser) {
                double amount = Double.parseDouble(record.get("Kwota"));
                String categoryName = record.get("Kategoria");
                String type = record.get("Typ");
                String description = record.get("Opis");
                String location = record.get("Lokalizacja");
                String person = record.get("Osoba");
                String date = record.get("Data");
                Category category = user.categoryManager.getCategory(categoryName);
                if (category == null) {
                    user.categoryManager.addCategory(categoryName, 1000);
                }
                if (type.equals("Wydatek")) {
                    Expense expense = new Expense(amount, category, description, location, person);
                    user.budget.addExpense(expense);
                } else if (type.equals("Przychód")) {
                    Income income = new Income(amount, category, description, location, person);
                    user.budget.addIncome(income);
                }
            }
        } catch (IOException e) {
            System.err.println("Bład odczytu z pliku CSV: " + e.getMessage());
        }
    }

}
