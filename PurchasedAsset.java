/*
** Holds the data of an asset that has been bought and the number of units bought
** Tracks the price of the asset bought and stores its initial value, name and symbol
*/
public class PurchasedAsset extends Asset
{
    /* Inhertied Properties
    ** String name, String symbol, float unitValue (final)
    */
    private float units;  // Number of units of the asset that have been bought
    private Asset asset;  // Asset that this object is referenced to for its current value

    public PurchasedAsset(Asset asset, float unitsBought)
    {
        super(asset.getName(), asset.getSymbol(), asset.getRawUnitValue());
        this.asset = asset;
        this.units = unitsBought;
    }

    public float getUnits() {
        return units;
    }

    // Returns original value of the bought asset
    public float getInitialValue()
    {
        return units * getRawUnitValue();
    }

    // Returns current value of the bought asset
    public float getCurrentValue()
    {
        return units * asset.getRawUnitValue();
    }

    public float getChange()
    {
        return getCurrentValue() - getInitialValue();
    }

    public float getPercentageChange()
    {
        return getChange() / getInitialValue() * 100.0f;
    }

}
