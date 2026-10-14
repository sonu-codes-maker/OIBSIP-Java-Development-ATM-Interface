public class Transaction {

    private String type;
    private double amount;
    private String description;

    public Transaction(String type, double amount, String description) {
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void display(int index) {
        String sign;
        if (type.equals("Deposit") || type.equals("Transfer In")) {
            sign = "+";
        } else {
            sign = "-";
        }
        System.out.println(index + ". " + type + "\t" + sign + "Rs." + amount + "\t" + description);
    }
}