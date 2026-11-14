import java.util.HashMap;
import java.io.*;

public class Bank {

    private HashMap<String, Account> accounts;
    private static final String FILE_NAME = "atm_data.dat";

    public Bank() {
        loadData();
    }

    @SuppressWarnings("unchecked")
    private void loadData() {

        File file = new File(FILE_NAME);

        if (file.exists()) {
            try (ObjectInputStream in =
                 new ObjectInputStream(new FileInputStream(file))) {

                Object data = in.readObject();

                if (data instanceof HashMap<?, ?>) {
                    accounts = (HashMap<String, Account>) data;
                    System.out.println("Saved account data loaded.");
                    return;
                }

            } catch (Exception e) {
                System.out.println("Could not load saved data.");
            }
        }

        accounts = new HashMap<>();

        accounts.put("1001",
            new Account("1001", 1234, "Rahul", 10000));

        accounts.put("1002",
            new Account("1002", 2222, "Priya", 8000));

        saveData();
    }

    public void saveData() {

        try (ObjectOutputStream out =
             new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            out.writeObject(accounts);

        } catch (IOException e) {
            System.out.println("Error saving account data.");
        }
    }

    public Account getAccount(String userId) {
        return accounts.get(userId);
    }

    public boolean accountExists(String userId) {
        return accounts.containsKey(userId);
    }

    public boolean transfer(Account sender,
                            String receiverId, double amount) {

        Account receiver = accounts.get(receiverId);

        if (receiver == null || sender == receiver ||
            amount <= 0 || sender.getBalance() < amount) {
            return false;
        }

        sender.withdraw(amount);
        receiver.deposit(amount);

        sender.addTransaction(
            new Transaction("TRANSFER", amount,
                "Sent to " + receiverId));

        receiver.addTransaction(
            new Transaction("RECEIVED", amount,
                "Received from " + sender.getUserId()));

        saveData();

        return true;
    }
}