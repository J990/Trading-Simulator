import java.util.Date;

public final class Trade implements StaticAsset
{
    private final Asset ASSET;
    private final double OPEN_UNIT_PRICE;  // Price per unit at time trade occurred
    private final double UNITS;
    private final double OPEN_VALUE;
    private final Date DATE;

    public Trade(Asset a, double o, double u)
    {
        ASSET = a;
        OPEN_UNIT_PRICE = o;
        UNITS = u;
        OPEN_VALUE = OPEN_UNIT_PRICE * UNITS;
        DATE = new Date();
    }

    public Asset getAsset() {
        return ASSET;
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
    
}
