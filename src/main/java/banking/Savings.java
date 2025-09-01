package banking;

public class Savings extends Account {
    private int withdrawalsLeft = 1;

    public Savings(String id, double apr) {
        super(id, apr);
    }

    @Override
    public String getTypeLabel() {
        return "Savings";
    }

    public int getWithdrawalsLeft() {
        return withdrawalsLeft;
    }

    @Override
    public void onMonthTick() {
        super.onMonthTick();
        withdrawalsLeft = 1;
    }

    @Override
    public void withdraw(double amount) {
        if (withdrawalsLeft <= 0) {
            return;
        }
        super.withdraw(amount);
        withdrawalsLeft--;
    }
}
