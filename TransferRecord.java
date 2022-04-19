import java.util.Date;

public class TransferRecord implements Record
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

    public String toJSON()
    {
        // date.getTime() (use milliseconds to rebuild date later on)
        JSONObject obj = new JSONObject();
        obj.put("amount", amount);
        obj.put("date", date.getTime());
        return obj.toString();
    }
}
