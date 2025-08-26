package banking;

public abstract class Account {
	protected String id;
	protected double balance;
	protected double apr;

	public Account(String id, double apr) {
		this.id = id;
		this.apr = apr;
		this.balance = 0.0;
	}

	public String getId() {
		return id;
	}

	public double getBalance() {
		return balance;
	}

	public double getApr() {
		return apr;
	}

	public void deposit(double amount) {
		if (amount <= 0) return;
		balance += amount;
	}

	public void withdraw(double amount) {
		if (amount <= 0) return;
		balance = Math.max(0.0, balance - amount);
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}
