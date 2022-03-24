import java.util.Date;
import java.util.ArrayList;

public class Bank
{
    private double balance;
    private ArrayList<TransferRecord> deposits;
    private ArrayList<TransferRecord> withdrawals;

    public Bank()
    {
        balance = 0.0f;
        deposits = new ArrayList<TransferRecord>();
        withdrawals = new ArrayList<TransferRecord>();
    }

    public Bank(TransferRecord tr)
    {
        balance = getAmount(tr);
        deposits.add(tr);
        withdrawals = new ArrayList<TransferRecord>();
    }

    public void deposit(double amount) throws SmallDepositException
    {
        if (amount <= 0)  // Amount is too small
            throw new SmallDepositException("Deposit too small: '" + amount + "'' is less than or equal to 0.");
        else // Adds money
        {
            balance += amount;
            TransferRecord tr = createTransferRecord(amount);
            deposits.add(tr);
        }
    }

    public void withdraw(double amount) throws SmallWithdrawalException
    {
        if (amount > balance)  // Ampunt is greater than balance
            throw new SmallWithdrawalException("Withdrawal (" + amount + ") is greater than balance (" + balance + ")");
        else // Takes out money
        {
            balance -= amount;
            TransferRecord tr = createTransferRecord(amount);
            withdrawals.add(tr);
        }
    }

    public static class TransferRecord
    {
        Date date;
        double amount;
    }

    public static Date getDate(TransferRecord tr) {return tr.date;}
    public static double getAmount(TransferRecord tr) {return tr.amount;}
    public static void setDate(TransferRecord tr, Date d) {tr.date = d;}
    public static void setAmount(TransferRecord tr, double a) {tr.amount = a;}
    public static TransferRecord createTransferRecord(double a)
    {
        TransferRecord tr = new TransferRecord();
        setDate(tr, new Date());
        setAmount(tr, a);
        return tr;
    }

}