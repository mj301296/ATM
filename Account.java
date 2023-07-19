package assignment03;

public class Account {
	private double balance;
	private String type;
	
	public Account(double balance, String type) {
		this.balance = balance;
		this.type = type;
	}
	public void deposit(double amount) {
		balance += amount;
	}
	public void withdraw(double amount) {
		balance -= amount;
	}
	public double getBalance() {
		return balance;
	}
	@Override
	public String toString() {
		return String.format("%s $%.2f", type, balance);
	}	
}
