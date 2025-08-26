package banking;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Bank {
    private final Map<String, Account> accounts = new LinkedHashMap<>();
    private final Map<String, List<String>> history = new LinkedHashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
        history.putIfAbsent(account.getId(), new ArrayList<>());
    }

    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public List<String> getHistory(String id) {
        return history.getOrDefault(id, Collections.emptyList());
    }

    public void recordTransaction(String id, String line) {
        history.putIfAbsent(id, new ArrayList<>());
        history.get(id).add(line);
    }

    public void deposit(String id, double amount) {
        Account a = accounts.get(id);
        if (a != null) a.deposit(amount);
    }

    public void withdraw(String id, double amount) {
        Account a = accounts.get(id);
        if (a != null) a.withdraw(amount);
    }

    public int getAccountCount() {
        return accounts.size();
    }
    public void transfer(String fromId, String toId, double amount) {
        if (amount <= 0) return;
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (from == null || to == null || fromId.equals(toId)) return;
        double before = from.getBalance();
        from.withdraw(amount);
        double moved = before - from.getBalance();
        if (moved > 0) {
            to.deposit(moved);
        }
    }
    public void passTime(int months) {
        if (months <= 0) {
            return;
        }

        for (Account a : accounts.values()) {
            double balance = a.getBalance();
            double monthlyRate = a.getApr() / 12.0;
            double interest = balance * monthlyRate * months;
            double rounded = Math.round(interest * 100.0) / 100.0;
            if (rounded > 0.0) {
                a.deposit(rounded);
            }
        }
    }

    public static String floor2(double v) {
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.FLOOR).toPlainString();
    }
}
