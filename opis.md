### Main
Opis: 
Klasa główna, która inicjalizuje aplikację i zarządza jej działaniem poprzez interakcję z użytkownikiem. Obsługuje menu główne i wywołuje odpowiednie metody na podstawie wyborów użytkownika. 
Główne funkcje:
Inicjalizacja aplikacji.
Obsługa menu głównego.
Wywoływanie funkcji dodawania, usuwania, przeglądania transakcji, eksportu i importu CSV.
### Transaction
Opis: Klasa bazowa reprezentująca ogólną transakcję. Zawiera podstawowe atrybuty wspólne dla wszystkich typów transakcji.
Pola:
id: String - unikalny identyfikator transakcji.
kwota: double - kwota transakcji.
data: Date - data transakcji.
kategoria: Kategoria - kategoria transakcji.
Metody:
Gettery i settery dla pól. 
### Expense
Opis: Klasa reprezentująca wydatek. Rozszerza klasę Transaction o dodatkowe atrybuty specyficzne dla wydatków.
Pola:
description: String - opis wydatku.
location: String - lokalizacja wydatku.
payee: String - odbiorca wydatku. 
### Income
Opis: Klasa reprezentująca przychód. Rozszerza klasę Transaction o dodatkowe atrybuty specyficzne dla przychodów.
Pola:
description: String - opis przychodu.
location: String - lokalizacja przychodu.
payer: String - płatnik przychodu. 
### User
Opis: Klasa reprezentująca użytkownika aplikacji. Zawiera dane logowania oraz instancje budżetu i menedżera kategorii przypisane do użytkownika.
Pola:
login: String - nazwa użytkownika.
haslo: String - hasło użytkownika.
budget: Budget - budżet użytkownika.
categoryManager: CategoryManager - menedżer kategorii użytkownika.
Metody:
Konstruktor i metody do zarządzania danymi użytkownika.
### UserManager
Opis: Klasa zarządzająca użytkownikami. Umożliwia rejestrację, logowanie i zarządzanie danymi użytkowników.
Pola:
uzytkownicy: List<User> - lista zarejestrowanych użytkowników.
Metody:
zarejestrujUzytkownika(login: String, haslo: String): void
zalogujUzytkownika(login: String, haslo: String): User
### Category
Opis: Klasa reprezentująca kategorię transakcji.
Pola:
nazwa: String - nazwa kategorii.
limit: float - limit budżetowy dla kategorii.
Metody:
### CategoryManager
Opis: Klasa zarządzająca kategoriami. Umożliwia dodawanie, usuwanie i zarządzanie kategoriami.
Pola:
kategorie: List<Category> - lista kategorii.
Metody:
dodajKategorie(kategoria: Category): void
usunKategorie(kategoria: Category): void
znajdzKategorie(nazwa: String): Category
### Budget
Opis: Klasa zarządzająca budżetem użytkownika. Przechowuje listę transakcji oraz udostępnia metody do zarządzania nimi.
Pola:
transakcje: List<Transaction> - lista transakcji.
Metody:
addTransaction(transaction: Transaction): void
removeTransaction(transaction: Transaction): void
getExpenses(): List<Expense>
getIncomes(): List<Income>
getExpenseTotal(): double
getIncomeTotal(): double
getAllTransactions(): List<Transaction>
### CsvManager
Opis: Klasa zarządzająca eksportem i importem danych do/z pliku CSV. Umożliwia zapisywanie i odczytywanie danych budżetu oraz kategorii.
Metody:
exportTransactions(List<Transaction> transactions, path: String): void
importTransactions(user: User, path: String): void