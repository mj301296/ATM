package assignment03;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;

public class Driver {
	public static void main(String[] args) throws FileNotFoundException {
		ATMgui.TEST_LOG = new PrintWriter("testrun.txt"); // clears the old file.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				System.out.println("NOTE: initial values of account balances and");
				System.out.println("store of cash can be changed in the main method");
				var builder = new ATMInternals.Builder();
				builder = builder.withCarLoan(30000);
				builder = builder.withCarLoanPayment(430);
				builder = builder.withCarLoanRate(4.0);
				builder = builder.withChecking(300);
				builder = builder.withMoneyMarket(1300);
				builder = builder.withMortgage(200000);
				builder = builder.withMortgagePayment(1500);
				builder = builder.withMortgageRate(3.0);
				builder = builder.withPersonalLoan(15000);
				builder = builder.withPersonalLoanPayment(250);
				builder = builder.withPersonalLoanRate(6);
				builder = builder.withSavings(2000);
				builder = builder.withNextPaymentDate(LocalDate.of(2023, 4, 1));

				ATMgui atm = new ATMgui();
				atm.aTMInternals = builder.initialize();
				State.setATMInternals(atm.aTMInternals);
				State.setDisplayPanel(atm.displayPanel);
				State.setATMgui(atm);
				atm.createAndShowGUI();
			}
		});
	}
}
