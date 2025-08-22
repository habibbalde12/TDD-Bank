package banking;

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

    public void transfer(String fromId, String toId, double amount) {
        if (fromId == null || toId == null) {
            return;
        }
        if (fromId.equals(toId)) {
            return;
        }
        Account from = getAccount(fromId);
        Account to = getAccount(toId);
        if (from == null || to == null) {
            return;
        }
        if (amount <= 0.0) {
            return;
        }

        double available = from.getBalance();
        double move = amount <= available ? amount : available;

        if (move <= 0.0) {
            return;
        }

        withdraw(fromId, move);
        deposit(toId, move);
    }


}

