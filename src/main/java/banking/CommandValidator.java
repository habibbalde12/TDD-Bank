package banking;

public abstract class CommandValidator {
    protected final Bank bank;

    public CommandValidator(Bank bank) {
        this.bank = bank;
    }

    public abstract boolean supports(String commandType);

    public abstract boolean validate(String[] tokens);

    public boolean validate(String command) {
        if (command == null || command.isBlank()) {
            return false;
        }
        String[] tokens = command.trim().split("\\s+");
        return validate(tokens);
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

    protected boolean isPlainDecimal(String s) {
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
            else if (c == 'e' || c == 'E') {
                return false;
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
        return true;
    }

    protected boolean validInterestRate(String s) {
        if (!isPlainDecimal(s)) {
            return false;
        }
        double v = Double.parseDouble(s);
        if (v < 0.0 || v > 1.0) {
            return false;
        }
        return true;
    }

    protected boolean isValidAmount(String s) {
        if (!isPlainDecimal(s)) {
            return false;
        }
        double v = Double.parseDouble(s);
        if (v < 0.0) {
            return false;
        }
        return true;
    }
}



