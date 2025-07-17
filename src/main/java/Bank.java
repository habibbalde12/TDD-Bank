import java.util.HashMap;
import java.util.Map;

public class Bank {
    private final Map<String, Account> accounts = new HashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
    }

    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public void deposit(String id, double amount) {
        Account account = accounts.get(id);
        if (account != null) {
            account.deposit(amount);
        }
    }

    public void withdraw(String id, double amount) {
        Account account = accounts.get(id);
        if (account != null) {
            account.withdraw(amount);
        }
    }

    public int getAccountCount() {
        return accounts.size();
    }
}