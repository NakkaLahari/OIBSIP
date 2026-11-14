 
    
import java.util.Scanner;

public class ATM {

    private Bank bank;
    private Scanner sc;
    private Account currentAccount;

    public ATM(Bank bank) {
        this.bank = bank;
        sc = new Scanner(System.in);
    }

    public void start() {

        System.out.println("===== WELCOME TO ATM =====");

        int attempts = 0;

        while (attempts < 3) {

            System.out.print("Enter User ID: ");
            String id = sc.nextLine();

            System.out.print("Enter PIN: ");
            int pin;

            try {
                pin = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid PIN!");
                attempts++;
                continue;
            }

            Account account = bank.getAccount(id);

            if (account != null && account.getPin() == pin) {

                currentAccount = account;

                System.out.println("\nLogin Successful!");
                System.out.println("Welcome " + account.getName());

                showMenu();
                return;
            }

            attempts++;

            System.out.println("Invalid User ID or PIN!");
            System.out.println("Attempts left: " + (3 - attempts));
        }

        System.out.println("Access Denied! Too many incorrect attempts.");
    }

    private void showMenu() {

        int choice = 0;

        do {
            System.out.println("\n===== ATM MENU =====");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");

            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice!");
                continue;
            }

            switch (choice) {

                case 1:
                    currentAccount.showHistory();
                    break;

                case 2:
                    withdraw();
                    break;

                case 3:
                    deposit();
                    break;

                case 4:
                    transfer();
                    break;

                case 5:
                    bank.saveData();
                    System.out.println("Thank you for using ATM!");
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 5);
    }

    private void withdraw() {

        System.out.print("Enter withdrawal amount: ");

        double amount;

        try {
            amount = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount!");
            return;
        }

        if (amount <= 0) {
            System.out.println("Enter a valid positive amount.");
        }
        else if (currentAccount.withdraw(amount)) {

            currentAccount.addTransaction(
                new Transaction("WITHDRAW", amount,
                    "Cash withdrawn"));

            bank.saveData();

            System.out.println("Withdrawal Successful!");
            System.out.println("Remaining Balance: Rs. "
                + currentAccount.getBalance());
        }
        else {
            System.out.println("Insufficient Funds!");
        }
    }

    private void deposit() {

        System.out.print("Enter deposit amount: ");

        double amount;

        try {
            amount = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount!");
            return;
        }

        if (amount <= 0) {
            System.out.println("Enter a valid positive amount.");
            return;
        }

        currentAccount.deposit(amount);

        currentAccount.addTransaction(
            new Transaction("DEPOSIT", amount,
                "Money deposited"));

        bank.saveData();

        System.out.println("Deposit Successful!");
        System.out.println("Current Balance: Rs. "
            + currentAccount.getBalance());
    }

    private void transfer() {

        System.out.print("Enter recipient Account ID: ");
        String receiverId = sc.nextLine();

        System.out.print("Enter transfer amount: ");

        double amount;

        try {
            amount = Double.parseDouble(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid amount!");
            return;
        }

        if (amount <= 0) {
            System.out.println("Enter a valid positive amount.");
            return;
        }

        if (currentAccount.getBalance() < amount) {
            System.out.println("Insufficient Funds!");
            return;
        }

        if (!bank.accountExists(receiverId) ||
            receiverId.equals(currentAccount.getUserId())) {

            System.out.println("Invalid recipient Account ID!");
            return;
        }

        if (bank.transfer(currentAccount, receiverId, amount)) {

            System.out.println("Transfer Successful!");

            System.out.println("Remaining Balance: Rs. "
                + currentAccount.getBalance());
        }
    }
}
