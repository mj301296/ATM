package assignment03;

import java.time.LocalDate;

public class ATMInternals {
	Account checkingAccount;
	Account savingsAccount;
	Account moneyMarketAccount; 
	Account mortgageAccount;
	Account carLoanAccount;
	Account personalLoanAccount;
	Account fromAccount;
	String fromAccountStr;
	Account toAccount;
	String toAccountStr;
	double toAccountPayment;
	double toAccountAPR;
	String amountStr = "_________$";
	Account mortgageBalance;
	Account carLoanBalance;
	Account personalLoanBalance;
	double mortgagePmt;
	double carLoanPmt;
	double personalLoanPmt; 
	double mortgageAPR;
	double carLoanAPR;
	double personalLoanAPR; 
	LocalDate nextPaymentDate;
	static String[] INIT_STRING = {"Deposit","Withdraw","Transfer","More...","Cancel"};
	String[] CANCEL_STRING = {"Do you wish to Cancel the session?","Press E to exit, any other keypad key to cancel","","",""};
	String[] LOGOUT_STRING = {"You have logged out","","","",""};
	String[] LOAN_PAYMENT_START = new String[5];
	String[] CHOOSE_FROM = new String[5];
	String[] CHOOSE_TRANSFER_AMOUNT = new String[5];
	String[] XFER = new String[5];
	String[] XFER_ERROR = new String[5];
	String[] XFER_RESULT = new String[5];
	void updateLOAN_PAYMENT_START() {
		LOAN_PAYMENT_START = new String[] {
				String.format("Pay to Mortgage, balance %.2f", mortgageAccount.getBalance()), 
				String.format("Pay to Car Loan, balance %.2f", carLoanAccount.getBalance()), 
				String.format("Pay to Personal Loan, balance %.2f", personalLoanAccount.getBalance()),
				"", "Cancel"}; 
	}
	void updateCHOOSE_FROM() {
		CHOOSE_FROM = new String[] {
				String.format("Transfer to " + toAccountStr +" (%.2f) from:", toAccount.getBalance()),
				String.format("Checking, balance %.2f", checkingAccount.getBalance()), 
				String.format("Savings, balance %.2f", savingsAccount.getBalance()), 
				String.format("Money Market, balance %.2f", moneyMarketAccount.getBalance()),
		"Cancel"}; 
	}
	void updateCHOOSE_TRANSFER_AMOUNT() {
		CHOOSE_TRANSFER_AMOUNT = new String[] {
				String.format("Transfer amount: " + amountStr),
				String.format(""),
				String.format(toAccountStr + ", balance %.2f", toAccount.getBalance()), 
				String.format(fromAccountStr + ", balance %.2f", fromAccount.getBalance()), 
		"Cancel"}; 
	}
	void updateXFER() {
		XFER = new String[] {
				String.format("Transfer amount: " + amountStr),
				String.format(""),
				String.format(toAccountStr + ", balance %.2f", toAccount.getBalance()), 
				String.format(fromAccountStr + ", balance %.2f", fromAccount.getBalance()), 
		"Cancel"}; 
	}
	void updateXFER_ERROR() {
		String temp = amountStr;
		while(temp.indexOf('_') >= 0) temp = temp.replace("_", "");
		XFER_ERROR = new String[] {
				String.format("Not sufficient funds for transfer: " + temp),
				String.format(toAccountStr + ", balance %.2f", toAccount.getBalance()), 
				String.format(fromAccountStr + ", balance %.2f", fromAccount.getBalance()), 
				String.format("OK, start over"),
		"Cancel"}; 
	}
	void updateXFER_RESULT() {
	//	TODO
		/*
		 here is where you calculate the remainder of the loan:
		 the number of months left to pay (num)
		 the last payment date (finalDate)
		 the final payment due on that date (temp)
		 
		  finalDate starts as nextPaymentDate (one of the fields)
		  temp starts as the current balance of toAccount (one of the fields)
		  you loop while temp is at least toAccountPayment (one of the fields)
		  in the loop you 
		  increase num by 1,
		  you add temp*toAccountAPR/12/100 to temp (the interest for a month)
		  --note that is not exactly correct in the first month because a bank
		  --should adjust for where in the month the payment is made but DO NOT
		  --WORRY about that detail
		  you subtract toAccountPayment from temp
		  you change finalDate to finalDate.plusMonths(1)
		  --note how friendly Java's LocalDate is
		 */
		int num = 0;
		LocalDate finalDate = nextPaymentDate;
		double temp = toAccount.getBalance();
		while(temp >= toAccountPayment) {
			temp *= 1 + toAccountAPR/12/100;
			temp -= toAccountPayment;
			finalDate = finalDate.plusMonths(1);
			num++;
		}

		XFER_RESULT = new String[] {
				String.format(toAccountStr + ", balance %.2f, payment plan:", toAccount.getBalance()), 
				String.format("%d payments of %.2f and", num, toAccountPayment),
				String.format("final payment of approximately %.2f on %s", temp, finalDate), 
				String.format("OK"),
		"Cancel"}; 
	}

	public String info() {
		return ": " 
				+ (fromAccount==null?"null/":String.format("%.2f/",fromAccount.getBalance()))
				+ (toAccount==null?"null/":String.format("%.2f/",toAccount.getBalance()))
				+ (checkingAccount==null?"null/":String.format("%.2f/",checkingAccount.getBalance()))
				+ (savingsAccount==null?"null/":String.format("%.2f/",savingsAccount.getBalance()))
				+ (moneyMarketAccount==null?"null/":String.format("%.2f/",moneyMarketAccount.getBalance()))
				+ (mortgageAccount==null?"null/":String.format("%.2f/",mortgageAccount.getBalance()))
				+ (carLoanAccount==null?"null/":String.format("%.2f/",carLoanAccount.getBalance()))
				+ (personalLoanAccount==null?"null/":String.format("%.2f/",personalLoanAccount.getBalance()));
	}
		
	private ATMInternals() {
	}

	public static class Builder {
		Account checkingAccount;
		Account savingsAccount;
		Account moneyMarketAccount;
		Account mortgageAccount;
		Account carLoanAccount;
		Account personalLoanAccount;
		double mortgagePmt;
		double carLoanPmt;
		double personalLoanPmt;
		double mortgageAPR;
		double carLoanAPR;
		double personalLoanAPR; 
		LocalDate nextPaymentDate;

		public Builder withCarLoan(double amount) {
			carLoanAccount= new Account(amount, "Car Loan");
			return this;
		}

		public Builder withCarLoanPayment(double amount) {
			carLoanPmt = amount;
			return this;
		}

		public Builder withCarLoanRate(double amount) {
			carLoanAPR = amount;
			return this;
		}

		public Builder withChecking(double amount) {
			checkingAccount = new Account(amount, "Checking");
			return this;
		}

		public Builder withMoneyMarket(double amount) {
			moneyMarketAccount = new Account(amount, "Money Market");
			return this;
		}
		public Builder withMortgage(double amount) {
			mortgageAccount = new Account(amount, "Mortgage");
			return this;
		}
		public Builder withMortgagePayment(double amount) {
			mortgagePmt = amount;
			return this;
		}
		public Builder withMortgageRate(double amount) {
			mortgageAPR = amount;
			return this;
		}
		public Builder withNextPaymentDate(LocalDate date) {
			nextPaymentDate = date;
			return this;
		}

		public Builder withPersonalLoan(double amount) {
			personalLoanAccount= new Account(amount, "Car Loan");
			return this;
		}

		public Builder withPersonalLoanPayment(double amount) {
			personalLoanPmt = amount;
			return this;
		}

		public Builder withPersonalLoanRate(double amount) {
			personalLoanAPR = amount;
			return this;
		}
		public Builder withSavings(double amount) {
			savingsAccount= new Account(amount, "Savings");
			return this;
		}
		public ATMInternals initialize() {
			var newObject = new ATMInternals();
			newObject.carLoanAccount = carLoanAccount;
			newObject.carLoanPmt = carLoanPmt;
			newObject.carLoanAPR = carLoanAPR;
			newObject.checkingAccount = checkingAccount;
			newObject.moneyMarketAccount = moneyMarketAccount;
			newObject.mortgageAccount = mortgageAccount;
			newObject.mortgagePmt = mortgagePmt;
			newObject.mortgageAPR = mortgageAPR;
			newObject.personalLoanAccount = personalLoanAccount;
			newObject.personalLoanPmt = personalLoanPmt;
			newObject.personalLoanAPR = personalLoanAPR;
			newObject.savingsAccount = savingsAccount;
			newObject.nextPaymentDate = nextPaymentDate;
			
			return newObject;
		}
	}
}
