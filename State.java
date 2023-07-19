package assignment03;

import javax.swing.JOptionPane;

public enum State {
	INIT {
		State buttonA() {
			JOptionPane.showMessageDialog(displayPanel, "Not implemented in this assignment");
			return this;
		}
		State buttonB() {
			JOptionPane.showMessageDialog(displayPanel, "Not implemented in this assignment");
			return this;
		}
		State buttonC() {
			JOptionPane.showMessageDialog(displayPanel, "Not implemented in this assignment");
			return this;
		}
		State buttonD() {
			aTMInternals.updateLOAN_PAYMENT_START();
			displayPanel.setCurrentButtonString(aTMInternals.LOAN_PAYMENT_START);
			return LOAN_PAYMENT_START;
		}		
	}, LOAN_PAYMENT_START {
		State buttonA() {
			aTMInternals.toAccount = aTMInternals.mortgageAccount;
			aTMInternals.toAccountStr = "Mortgage";
			aTMInternals.toAccountAPR = aTMInternals.mortgageAPR;
			aTMInternals.toAccountPayment = aTMInternals.mortgagePmt;
			aTMInternals.updateCHOOSE_FROM();
			displayPanel.setCurrentButtonString(aTMInternals.CHOOSE_FROM);
			return CHOOSE_FROM;
		}		
		//TODO complete simialar code for buttons B and C (Car Loan and Personal Loan)
		State buttonB() {
			aTMInternals.toAccount = aTMInternals.carLoanAccount;
			aTMInternals.toAccountStr = "Car Loan";
			aTMInternals.toAccountAPR = aTMInternals.carLoanAPR;
			aTMInternals.toAccountPayment = aTMInternals.carLoanPmt;
			aTMInternals.updateCHOOSE_FROM();
			displayPanel.setCurrentButtonString(aTMInternals.CHOOSE_FROM);
			return CHOOSE_FROM;
		}
		State buttonC() {
			aTMInternals.toAccount = aTMInternals.personalLoanAccount;
			aTMInternals.toAccountStr = "Personal Loan";
			aTMInternals.toAccountAPR = aTMInternals.personalLoanAPR;
			aTMInternals.toAccountPayment = aTMInternals.personalLoanPmt;
			aTMInternals.updateCHOOSE_FROM();
			displayPanel.setCurrentButtonString(aTMInternals.CHOOSE_FROM);
			return CHOOSE_FROM;
		}
	}, CHOOSE_FROM {
		State buttonB() {
			aTMInternals.fromAccount = aTMInternals.checkingAccount;
			aTMInternals.fromAccountStr = "Checking";
			aTMInternals.updateCHOOSE_TRANSFER_AMOUNT();
			displayPanel.setCurrentButtonString(aTMInternals.CHOOSE_TRANSFER_AMOUNT);
			return CHOOSE_TRANSFER_AMOUNT;
		}		
		//TODO complete similar code for buttons C and D (Savings and Money Market)
		State buttonC() {
			aTMInternals.fromAccount = aTMInternals.savingsAccount;
			aTMInternals.fromAccountStr = "Savings";
			aTMInternals.updateCHOOSE_TRANSFER_AMOUNT();
			displayPanel.setCurrentButtonString(aTMInternals.CHOOSE_TRANSFER_AMOUNT);
			return CHOOSE_TRANSFER_AMOUNT;
		}
		State buttonD() {
			aTMInternals.fromAccount = aTMInternals.moneyMarketAccount;
			aTMInternals.fromAccountStr = "Money Market";
			aTMInternals.updateCHOOSE_TRANSFER_AMOUNT();
			displayPanel.setCurrentButtonString(aTMInternals.CHOOSE_TRANSFER_AMOUNT);
			return CHOOSE_TRANSFER_AMOUNT;
		}
	}, CHOOSE_TRANSFER_AMOUNT {
		State acceptKey(String string) {
			if(string.equals("E")) {
				String temp = aTMInternals.amountStr;
				temp = temp.replace("$", "");
				while(temp.indexOf('_') >= 0) temp = temp.replace("_", "");
				double amount = Double.parseDouble(temp);
				if(amount > aTMInternals.fromAccount.getBalance()) {
					aTMInternals.updateXFER_ERROR();
					displayPanel.setCurrentButtonString(aTMInternals.XFER_ERROR);
					return XFER_END;
				}
				aTMInternals.fromAccount.withdraw(amount);
				aTMInternals.toAccount.withdraw(amount);
				System.out.println("Transfer amount $" + amount);
				System.out.println(aTMInternals.fromAccount + " " + aTMInternals.toAccount);
				aTMInternals.amountStr = "_________$";
				aTMInternals.updateXFER_RESULT();
				displayPanel.setCurrentButtonString(aTMInternals.XFER_RESULT);
				return XFER_END;
			}
			if(aTMInternals.amountStr.contains(".")) {
				if(string.equals(".")) {
					// ignore only can have one "."
				} 
				else if(aTMInternals.amountStr.indexOf(".") + 3 == aTMInternals.amountStr.length()) {
					// ignore, cannot add more than 2 decimal places for cents
				} 
				else {
					aTMInternals.amountStr += string; // concatenate the entry
					String temp = "_________" + aTMInternals.amountStr;
					aTMInternals.amountStr = temp.substring("_________$".length());
				}
			} else if(string.equals("0") && aTMInternals.amountStr.equals("_________$")) {
				// ignore--do not allow leading 0's
			} 
			else { 
				aTMInternals.amountStr += string;
				String temp = "_________" + aTMInternals.amountStr;
				aTMInternals.amountStr = temp.substring("_________$".length());
			}
			aTMInternals.updateXFER();
			displayPanel.setCurrentButtonString(aTMInternals.XFER);
			return this;
		}
	}, XFER_END {
		State buttonD() {
			displayPanel.setCurrentButtonString(ATMInternals.INIT_STRING);
			aTMInternals.amountStr = "_________$";
			return INIT;
		}				
	}, CANCEL {
		State acceptKey(String string) {
			if(string.equals("E")) {
				displayPanel.setCurrentButtonString(aTMInternals.LOGOUT_STRING);
				return LOGGED_OUT;
			}
			aTMInternals.amountStr = "_________$";
			displayPanel.setCurrentButtonString(ATMInternals.INIT_STRING);
			return INIT;
		}
	}, LOGGED_OUT {
		State buttonA() {
			return commonExitCode();
		}
		State buttonB() {
			return commonExitCode();
		}
		State buttonC() {
			return commonExitCode();
		}		
		State buttonD() {
			return commonExitCode();
		}
		State buttonE() {
			return commonExitCode();
		}
		State acceptKey(String string) {
			return commonExitCode();
		}
	};
	static ATMInternals aTMInternals;
	static DisplayPanel displayPanel;
	static ATMgui gui;
	static void setATMInternals (ATMInternals internalsIn) {
		aTMInternals = internalsIn;
	}
	static void setDisplayPanel (DisplayPanel panelIn) {
		displayPanel = panelIn;
	}
	static void setATMgui (ATMgui guiIn) {
		gui = guiIn;
	}
	static State commonExitCode() {
		if(gui!= null) gui.exit();
		return null;					
	}
	State buttonA() {
		return this;
	}
	State buttonB() {
		return this;
	}
	State buttonC() {
		return this;
	}
	State buttonD() {
		return this;
	}
	State buttonE() {
		displayPanel.setCurrentButtonString(aTMInternals.CANCEL_STRING);
		return CANCEL;
	}
	State acceptKey(String string) {
		return this;
	}
}
