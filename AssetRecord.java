/**
 * A receipt for any assets that are bought
 * For a single purchase
 */
public class AssetRecord extends Record
{
    private Asset asset;
    private double totalPrice;
    private double units;
    private double unitPrice;

    public AssetRecord(Asset a, double price)
    {
        unitPrice = a.getRawUnitValue();  // Done first to prevent price inconsistencies
        asset = a;
        totalPrice = price;
        units = price / unitPrice;
    }

    public Asset getAsset() {
        return asset;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getUnits() {
        return units;
    }

    public String toJSON() {
        return String.format(
            "{\"asset\": \"%s\",\"totalPrice\": \"%d\",\"units\": \"%d\",\"unitPrice\": \"%d\"",
            asset.toString(),
            totalPrice,
            units,
            unitPrice
        );
    }
}
