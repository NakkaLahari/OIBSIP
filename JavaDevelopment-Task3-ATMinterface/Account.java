import java.util.ArrayList;
import java.io.Serializable;

public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private int pin;
    private String name;
    private double balance;
    private ArrayList<Transaction> history;

    public Account(String userId, int pin, String name, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.name = name;
        this.balance = balance;
        history = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public int getPin() {
        return pin;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void addTransaction(Transaction t) {
        history.add(t);
    }

    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        System.out.println("\n----- TRANSACTION HISTORY -----");

        for (Transaction t : history) {
            System.out.println(t);
        }
    }
}