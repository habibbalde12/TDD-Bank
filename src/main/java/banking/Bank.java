package banking;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Bank {
    private final LinkedHashMap<String, Account> accounts = new LinkedHashMap<>();
    private final LinkedHashMap<String, List<String>> history = new LinkedHashMap<>();

    public void addAccount(Account account) {
        accounts.put(account.getId(), account);
        history.putIfAbsent(account.getId(), new ArrayList<>());
    }

    public void addAccount(String type, String id, double aprPercent) {
        Account a;
        double aprDecimal = aprPercent / 100.0;
        if ("checking".equalsIgnoreCase(type)) {
            a = new Checkings(id, aprDecimal);
        } else if ("savings".equalsIgnoreCase(type)) {
            a = new Savings(id, aprDecimal);
        } else {
            throw new IllegalArgumentException();
        }
        addAccount(a);
    }

    public void addAccount(String type, String id, double aprPercent, double amount) {
        if (!"cd".equalsIgnoreCase(type)) {
            throw new IllegalArgumentException();
        }
        double aprDecimal = aprPercent / 100.0;
        addAccount(new CD(id, aprDecimal, amount));
    }

    public int getAccountCount() {
        return accounts.size();
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

    public void recordTransaction(String id, String raw) {
        history.putIfAbsent(id, new ArrayList<>());
        history.get(id).add(raw);
    }

    public void deposit(String id, double amount) {
        Account a = accounts.get(id);
        if (a != null) {
            a.deposit(amount);
        }
    }

    public void withdraw(String id, double amount) {
        Account a = accounts.get(id);
        if (a != null) {
            a.withdraw(amount);
        }
    }

    public void transfer(String fromId, String toId, double requested, String raw) {
        if (requested <= 0) {
            return;
        }
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (from == null || to == null || fromId.equals(toId)) {
            return;
        }
        double moved = Math.min(from.getBalance(), requested);
        if (moved <= 0) {
            return;
        }
        from.withdraw(requested);
        to.deposit(moved);
        recordTransaction(fromId, raw);
        recordTransaction(toId, raw);
    }

    public void transfer(String fromId, String toId, double requested) {
        if (requested <= 0) {
            return;
        }
        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);
        if (from == null || to == null || fromId.equals(toId)) {
            return;
        }
        double moved = Math.min(from.getBalance(), requested);
        if (moved <= 0) {
            return;
        }
        from.withdraw(requested);
        to.deposit(moved);
    }

    public void passTime(int months) {
        if (months <= 0) {
            return;
        }
        for (String id : new ArrayList<>(accounts.keySet())) {
            Account a = accounts.get(id);
            if (a != null && a.getBalance() == 0.0) {
                accounts.remove(id);
            }
        }
        for (Account a : accounts.values()) {
            double monthlyRate = a.getApr() / 12.0;
            BigDecimal interest = BigDecimal.valueOf(a.getBalance())
                    .multiply(BigDecimal.valueOf(monthlyRate))
                    .multiply(BigDecimal.valueOf(months))
                    .setScale(2, RoundingMode.HALF_UP);
            if (interest.doubleValue() > 0.0) {
                a.deposit(interest.doubleValue());
            }
            for (int i = 0; i < months; i++) {
                a.onMonthTick();
            }
        }
    }

    public static String floor2(double v) {
        return BigDecimal.valueOf(v).setScale(2, RoundingMode.FLOOR).toPlainString();
    }
}
