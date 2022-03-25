import java.util.ArrayList;

public class Bank
{
    private double balance;
    private ArrayList<TransferRecord> depositHistory;
    private ArrayList<TransferRecord> withdrawalHistory;

    public Bank()
    {
        balance = 0.0d;
        depositHistory = new ArrayList<TransferRecord>();
        withdrawalHistory = new ArrayList<TransferRecord>();
    }

    public Bank(double initialBalance)
    {
        this();
        balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    // Adds money to the bank
    // Throws an exception if the amount deposited is 0 or less
    public void deposit(double amount) throws SmallDepositException
    {
        if (amount <= 0)  // Amount is too small
            throw new SmallDepositException("Deposit too small: '" + amount + "'' is less than or equal to 0.");
        else // Adds money
        {
            balance += amount;
            depositHistory.add(new TransferRecord(amount));
        }
    }

    // Removes money from the bank
    // Throws an exception if the amount withdrawn is bigger than the bank balance
    public void withdraw(double amount) throws SmallWithdrawalException
    {
        if (amount > balance)  // Ampunt is greater than balance
            throw new SmallWithdrawalException("Withdrawal (" + amount + ") is greater than balance (" + balance + ")");
        else // Takes out money
        {
            balance -= amount;
            withdrawalHistory.add(new TransferRecord(amount));
        }
    }

    public ArrayList<TransferRecord> getDepositHistory() {
        return depositHistory;
    }

    public ArrayList<TransferRecord> getWithdrawalHistory() {
        return withdrawalHistory;
    }

}