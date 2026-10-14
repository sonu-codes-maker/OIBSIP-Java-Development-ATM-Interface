import java.util.Scanner;

public class ATM {

    private Bank bank;
    private Scanner sc;
    private Account currentAccount;

    public ATM() {
        bank = new Bank();
        sc = new Scanner(System.in);
    }

    public void start() {
        System.out.println("========================================");
        System.out.println("      WELCOME TO JAVA ATM SIMULATOR");
        System.out.println("========================================");

        boolean loggedIn = login();

        if (loggedIn) {
            showMenu();
        } else {
            System.out.println("Too many incorrect attempts. Access denied.");
        }
    }

    private boolean login() {
        int attempts = 0;

        while (attempts < 3) {
            System.out.print("\nEnter User ID: ");
            String userId = sc.nextLine();

            System.out.print("Enter PIN: ");
            String pin = sc.nextLine();

            Account acc = bank.authenticate(userId, pin);

            if (acc != null) {
                currentAccount = acc;
                System.out.println("\nLogin successful. Welcome, " + acc.getUserId() + "!");
                return true;
            } else {
                attempts++;
                int remaining = 3 - attempts;
                if (remaining > 0) {
                    System.out.println("Incorrect User ID or PIN. Attempts remaining: " + remaining);
                } else {
                    System.out.println("Incorrect User ID or PIN.");
                }
            }
        }
        return false;
    }

    private void showMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n============ MAIN MENU ============");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");

            String choiceText = sc.nextLine();
            int choice = readMenuChoice(choiceText);

            switch (choice) {
                case 1:
                    currentAccount.showTransactionHistory();
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
                    System.out.println("\nThank you for using Java ATM. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please choose 1-5.");
            }
        }
    }

    private int readMenuChoice(String text) {
        if (text.length() != 1) {
            return -1;
        }
        char c = text.charAt(0);
        if (c < '0' || c > '9') {
            return -1;
        }
        return Integer.parseInt(text);
    }

    private double readAmount() {
        System.out.print("Enter amount: Rs.");
        String input = sc.nextLine();

        if (!isValidAmount(input)) {
            System.out.println("Invalid amount entered.");
            return -1;
        }

        double amount = Double.parseDouble(input);

        if (amount <= 0) {
            System.out.println("Amount must be greater than zero.");
            return -1;
        }

        return amount;
    }

    private boolean isValidAmount(String input) {
        if (input.length() == 0) {
            return false;
        }

        int digitCount = 0;
        int dotCount = 0;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '.') {
                dotCount++;
            } else if (c >= '0' && c <= '9') {
                digitCount++;
            } else {
                return false;
            }
        }

        if (dotCount > 1) {
            return false;
        }
        if (digitCount == 0) {
            return false;
        }

        return true;
    }

    private void withdraw() {
        double amount = readAmount();
        if (amount == -1) {
            return;
        }

        boolean success = currentAccount.withdraw(amount);

        if (success) {
            System.out.println("Withdrawal successful. New balance: Rs." + currentAccount.getBalance());
        } else {
            System.out.println("Insufficient Funds");
        }
    }

    private void deposit() {
        double amount = readAmount();
        if (amount == -1) {
            return;
        }

        currentAccount.deposit(amount);
        System.out.println("Deposit successful. New balance: Rs." + currentAccount.getBalance());
    }

    private void transfer() {
        System.out.print("Enter recipient User ID: ");
        String recipientId = sc.nextLine();

        double amount = readAmount();
        if (amount == -1) {
            return;
        }

        boolean success = bank.transfer(currentAccount, recipientId, amount);

        if (success) {
            System.out.println("Transfer successful. New balance: Rs." + currentAccount.getBalance());
        } else {
            Account recipient = bank.findAccount(recipientId);
            if (recipient == null) {
                System.out.println("Recipient account not found.");
            } else if (recipient.getUserId().equals(currentAccount.getUserId())) {
                System.out.println("You cannot transfer to your own account.");
            } else {
                System.out.println("Insufficient Funds");
            }
        }
    }
}
