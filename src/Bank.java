import java.util.ArrayList;

public class Bank {

    private ArrayList<Account> accounts;

    public Bank() {
        accounts = new ArrayList<Account>();
        addSampleAccounts();
    }

    private void addSampleAccounts() {
        accounts.add(new Account("Keshav", "1111", 50000));
        accounts.add(new Account("Slayer", "2222", 30000));
        accounts.add(new Account("Ajay", "3333", 15000));
        accounts.add(new Account("Manish", "4444", 25000));
    }

    public Account findAccount(String userId) {
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            if (acc.getUserId().equals(userId)) {
                return acc;
            }
        }
        return null;
    }

    public Account authenticate(String userId, String pin) {
        Account acc = findAccount(userId);
        if (acc == null) {
            return null;
        }
        if (acc.checkPin(pin)) {
            return acc;
        }
        return null;
    }

    public boolean transfer(Account sender, String recipientId, double amount) {
        Account recipient = findAccount(recipientId);

        if (recipient == null) {
            return false;
        }

        if (recipient.getUserId().equals(sender.getUserId())) {
            return false;
        }

        if (!sender.hasSufficientBalance(amount)) {
            return false;
        }

        sender.deductForTransfer(amount, recipient.getUserId());
        recipient.receiveTransfer(amount, sender.getUserId());
        return true;
    }
}
