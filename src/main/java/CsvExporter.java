import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

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
                    printer.printRecord(transaction.getAmount(), transaction.getDate(), transaction.getCategory().getName(), "Przychód", ((Income) transaction).getDescription(), "", ((Income) transaction).getPayer());
                }
            }
        } catch (IOException e) {
            System.err.println("Bład zapisu do pliku CSV: " + e.getMessage());
        }
    }

}
