package banking;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Output {
    public List<String> print(Bank bank) {
        List<Account> accounts = new ArrayList<>(bank.getAccounts().values());
        accounts.sort(Comparator.comparing(Account::getId));
        List<String> result = new ArrayList<>();
        for (Account account : accounts) {
            String type = (account instanceof Checkings) ? "Checking" : (account instanceof Savings) ? "Savings" : "Cd";
            String line = type + " " + account.getId() + " " + Bank.floor2(account.getBalance()) + " " + Bank.floor2(account.getApr() * 100.0);
            result.add(line);
            for (String h : bank.getHistory(account.getId())) {
                result.add(h);
            }
        }
        return result;
    }
}
