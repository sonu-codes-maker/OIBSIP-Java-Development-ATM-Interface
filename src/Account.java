import java.util.ArrayList;

public class Account {

    private String userId;
    private String pin;
    private double balance;
    private ArrayList<Transaction> transactionHistory;

    public Account(String userId, String pin, double balance) {
        this.userId = userId;
        this.pin = pin;
        this.balance = balance;
        this.transactionHistory = new ArrayList<Transaction>();
    }

    public String getUserId() {
        return userId;
    }

    public String getPin() {
        return pin;
    }

    public double getBalance() {
        return balance;
    }

    public ArrayList<Transaction> getTransactionHistory() {
        return transactionHistory;
    }

    public boolean checkPin(String enteredPin) {
        return this.pin.equals(enteredPin);
    }

    public void deposit(double amount) {
        balance = balance + amount;
        Transaction t = new Transaction("Deposit", amount, "Cash deposited");
        transactionHistory.add(t);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance = balance - amount;
        Transaction t = new Transaction("Withdrawal", amount, "Cash withdrawn");
        transactionHistory.add(t);
        return true;
    }

    public boolean hasSufficientBalance(double amount) {
        return balance >= amount;
    }

    public void deductForTransfer(double amount, String recipientId) {
        balance = balance - amount;
        Transaction t = new Transaction("Transfer", amount, "Transferred to " + recipientId);
        transactionHistory.add(t);
    }

    public void receiveTransfer(double amount, String senderId) {
        balance = balance + amount;
        Transaction t = new Transaction("Transfer In", amount, "Received from " + senderId);
        transactionHistory.add(t);
    }

    public void showTransactionHistory() {
        if (transactionHistory.size() == 0) {
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("===== Transaction History =====");
        for (int i = 0; i < transactionHistory.size(); i++) {
            Transaction t = transactionHistory.get(i);
            t.display(i + 1);
        }
    }
}