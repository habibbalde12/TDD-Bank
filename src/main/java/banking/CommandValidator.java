package banking;

public class CommandValidator {
    protected final Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        if (command == null || command.isBlank()) {
            return false;
        }

        String[] tokens = command.trim().split("\\s+");
        return validate(tokens);
    }

    public boolean validate(String[] tokens) {
        if (tokens == null || tokens.length == 0) {
            return false;
        }

        String first = tokens[0];

        if (first.equalsIgnoreCase("create")) {
            return validateCreate(tokens);
        }
        else if (first.equalsIgnoreCase("deposit")) {
            return validateDeposit(tokens);
        }

        return false;
    }

    private boolean validateCreate(String[] tokens) {
        if (tokens.length != 4) {
            return false;
        }

        String type = tokens[1];
        String accountId = tokens[2];
        String rate = tokens[3];

        if (!type.equals("savings") && !type.equals("checking")) {
            return false;
        }

        if (!isEightDigitNumber(accountId)) {
            return false;
        }

        if (bank.getAccount(accountId) != null) {
            return false;
        }

        if (!validInterestRate(rate)) {
            return false;
        }

        return true;
    }

    private boolean validateDeposit(String[] tokens) {
        if (tokens.length != 3) {
            return false;
        }

        String accountId = tokens[1];
        String amount = tokens[2];

        if (!isEightDigitNumber(accountId)) {
            return false;
        }

        if (bank.getAccount(accountId) == null) {
            return false;
        }

        if (!isValidAmount(amount)) {
            return false;
        }

        return true;
    }

    protected boolean isEightDigitNumber(String s) {
        if (s == null || s.length() != 8) {
            return false;
        }

        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    protected boolean validInterestRate(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        boolean hasDecimal = false;
        boolean hasDigit = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '.') {
                if (hasDecimal) {
                    return false;
                }
                hasDecimal = true;
            }
            else if (Character.isDigit(c)) {
                hasDigit = true;
            }
            else {
                return false;
            }
        }

        if (!hasDigit) {
            return false;
        }

        double val = Double.parseDouble(s);
        return val >= 0 && val <= 1;
    }

    protected boolean isValidAmount(String s) {
        if (s == null || s.isEmpty()) {
            return false;
        }

        boolean hasDecimal = false;
        boolean hasDigit = false;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (c == '.') {
                if (hasDecimal) {
                    return false;
                }
                hasDecimal = true;
            }
            else if (Character.isDigit(c)) {
                hasDigit = true;
            }
            else {
                return false;
            }
        }

        if (!hasDigit) {
            return false;
        }

        double val = Double.parseDouble(s);
        return val > 0;
    }
}



