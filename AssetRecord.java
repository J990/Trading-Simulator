import java.util.Date;

/**
 * A receipt for any assets that are bought
 * For a single purchase
 */
public final class AssetRecord extends Record implements StaticAsset
{
    private final Asset ASSET;
    private final double OPEN_UNIT_PRICE;  // Price per unit at time trade occurred
    private final double UNITS;
    private final Date DATE;

    public AssetRecord(Trade t)
    {
        ASSET = t.getAsset();
        OPEN_UNIT_PRICE = t.getOpenUnitPrice();
        UNITS = t.getUnits();
        DATE = t.getDate();
    }

    public Asset getAsset() {
        return ASSET;
    }

    public double getUnits() {
        return UNITS;
    }

    public double getOpenUnitPrice() {
        return OPEN_UNIT_PRICE;
    }

    public Date getDate() {
        return DATE;
    }

    public String toJSON() {
        return String.format(
            "{\"asset\": \"%s\",\"units\": \"%d\",\"unitPrice\": \"%d\"",
            ASSET.toString(),
            UNITS,
            OPEN_UNIT_PRICE
        );
    }

}
