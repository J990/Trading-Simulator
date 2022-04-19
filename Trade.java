import java.util.Date;

public final class Trade implements StaticAsset
{
    private final Asset ASSET;
    private final double OPEN_UNIT_PRICE;  // Price per unit at time trade occurred
    private final double UNITS;
    private final double OPEN_VALUE;
    private final Date DATE;
    private final int ID;
    static int idCounter = 0;

    public Trade(Asset a, double o, double u)
    {
        ASSET = a;
        OPEN_UNIT_PRICE = o;
        UNITS = u;
        OPEN_VALUE = OPEN_UNIT_PRICE * UNITS;
        DATE = new Date();
        ID = idCounter++;
    }

    public Asset getAsset() {
        return ASSET;
    }

    public int getID() {
        return ID;
    }

    public double getOpenUnitPrice() {
        return OPEN_UNIT_PRICE;
    }

    public double getUnits() {
        return UNITS;
    }

    public double getOpenValue() {
        return OPEN_VALUE;
    }

    public Date getDate() {
        return DATE;
    }

    public double currentValue() {
        return ASSET.getRawUnitValue() * UNITS;
    }

    public double profit() {
        return currentValue() - OPEN_VALUE;
    }

    public double percentageProfit() {
        return profit() / OPEN_VALUE * 100.0d;
    }

    public AssetRecord generateReceipt()
    {
        return new AssetRecord(this);
    }

    public void closeTrade()
    {
        
    }

    // In the format:
    // OPEN: 3.2456 UNITS @ £354.97 VALUE: £2,798.54 (+123.54%)
    public String toString() {
        Profit p = new Profit(ASSET);
        p.addTrade(this);
        String s = "OPEN: " + UNITS + " UNITS @ " + Converter.convert(OPEN_UNIT_PRICE) + " ";
        s += "VALUE: " + Converter.convert(currentValue()) + " (" + p.getPercentageString() + ")";
        return s;
    }
    
}
