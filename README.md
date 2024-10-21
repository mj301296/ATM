# ATM Simulation Project

## Overview

This project simulates an ATM system with a graphical user interface (GUI) using Java's Swing framework. It models various banking operations such as loan payments, fund transfers, and account management. The project is structured using state machines to represent different stages of the ATM operations and integrates with multiple bank accounts such as savings, checking, loans, and mortgages.

## Features

- **ATM GUI**: A user-friendly interface where users can select various options to interact with their accounts.
- **Account Management**: Users can manage different types of accounts like Checking, Savings, Money Market, and Loans (Personal, Car, Mortgage).
- **Fund Transfers**: The system supports transferring funds between accounts.
- **Loan Payments**: Users can make payments toward various loan accounts, including car, mortgage, and personal loans.
- **State Machine**: A state machine handles different states such as account selection, transfer amount input, and transaction processing.
- **Customizable Window Closing Logic**: Through the `WindowListenerFactory`, you can define custom behavior when the ATM window is closed.

## Project Structure

- **`Account.java`**: Represents a bank account with basic operations such as deposit, withdraw, and balance retrieval.
- **`DisplayPanel.java`**: Custom Swing component for rendering the ATM interface and displaying user options and feedback.
- **`Driver.java`**: The main driver of the project that initializes the ATM system and sets up the initial account data.
- **`State.java`**: Enum that implements the state machine logic for handling user interactions and transitions between ATM states.
- **`ATMInternals.java`**: Handles internal ATM data and state transitions, including account management and transfer logic.
- **`ATMgui.java`**: The graphical user interface that integrates with the state machine and facilitates interaction between the user and the system.
- **`WindowListenerFactory.java`**: Provides a utility to handle window closing events with a customizable action.
  
## Installation

1. **Clone the repository:**
    ```bash
    git clone https://github.com/mj301296/ATM.git
    ```
   
2. **Navigate to the project directory:**
    ```bash
    cd atm-simulation
    ```

3. **Compile the project:**
    ```bash
    javac -d bin src/assignment03/*.java
    ```

4. **Run the project:**
    ```bash
    java -cp bin assignment03.Driver
    ```

## How to Use

### ATM Operations:
- On starting the ATM, a GUI will appear where the user can interact with different accounts and perform operations such as making loan payments and transferring funds.
  
- The available operations are presented as buttons, and the user can navigate through the different stages using the provided options.
  
### State Machine Transitions:
- The ATM system transitions through various states such as:
  - **INIT**: Initial state where the ATM awaits user interaction.
  - **LOAN_PAYMENT_START**: Allows users to select a loan account (e.g., Mortgage, Car Loan) for payments.
  - **CHOOSE_FROM**: Prompts the user to select a source account for transfers.
  - **CHOOSE_TRANSFER_AMOUNT**: Users input the amount to transfer.
  - **XFER_END**: Finalizes the transaction.

### Window Closing:
- The ATM GUI is equipped with a custom window closing event, allowing the system to perform specific actions when the window is closed (e.g., saving logs, releasing resources). This is handled via the `WindowListenerFactory` class.

## Dependencies

- **Java**: Make sure you have Java JDK 8 or later installed.

## Customization

You can modify the initial values of accounts and loan configurations by adjusting the builder pattern in the `Driver.java` file.

```java
var builder = new ATMInternals.Builder();
builder = builder.withCarLoan(30000);
builder = builder.withChecking(300);
builder = builder.withSavings(2000);
// Customize other fields as necessary
