import java.io.Serializable;

public class User implements Serializable {
    private String login;
    private String password;
    protected Budget budget;
    private String filepath;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.budget = new Budget();
        this.filepath = login + ".ser";
    }

    public String getLogin() {
        return login;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public Budget getBudget() {
        return budget;
    }

    public String getFilepath() {
        return filepath;
    }
}
