import java.util.ArrayList;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class UserManager {
    private List<User> users = new ArrayList<>();

    public UserManager() {
        loadUsersFromFile();
    }

    public User addUser(String login, String password) {
        User user = new User(login, password);
        users.add(user);
        return user;
    }

    public User login(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.checkPassword(password)) {
                return user;
            }
        }
        return null;
    }

    public void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.ser"))) {
            oos.writeObject(users);
            System.out.println("Zapisano użytkowników do pliku");
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku");
        }
    }

    public void loadUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.ser"))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd odczytu użytkowników z pliku");
        }
    }

    public void saveUserBudgetToFile(User user) {
        String filepath = user.getFilepath();
        Budget budget = user.getBudget();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(budget);
            System.out.println("Zapisano budżet do pliku");
        } catch (IOException e) {
            System.err.println("Błąd zapisu do pliku");
        }
    }

    public Budget loadUserBudgetFromFile(User user) {
        String filepath = user.getFilepath();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            return (Budget) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd odczytu z pliku");
            return null;
        }
    }

}
