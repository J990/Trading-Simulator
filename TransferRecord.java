import java.util.Date;

public class TransferRecord extends Record
{
    Date date;
    double amount;

    public TransferRecord(double a)
    {
        date = new Date();
        amount = a;
    }

    public Date getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }
}
