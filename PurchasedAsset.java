/*
** Holds the data of an asset that has been bought and the number of units bought
** Tracks the price of the asset bought and stores its initial value, name and symbol
*/
public class PurchasedAsset extends Asset
{
    /* Inhertied Properties
    ** String name, String symbol, float unitValue (final)
    */
    private double units;  // Number of units of the asset that have been bought
    private Asset asset;  // Asset that this object is referenced to for its current value

    public PurchasedAsset(Asset asset, double unitsBought)
    {
        super(asset.getName(), asset.getSymbol(), asset.getRawUnitValue());
        this.asset = asset;
        this.units = unitsBought;
    }

    public double getUnits() {
        return units;
    }

    // Returns original value of the bought asset
    public double getInitialValue()
    {
        return units * getRawUnitValue();
    }

    // Returns current value of the bought asset
    public double getCurrentValue()
    {
        return units * asset.getRawUnitValue();
    }

    public double getChange()
    {
        return getCurrentValue() - getInitialValue();
    }

    public double getPercentageChange()
    {
        return getChange() / getInitialValue() * 100.0d;
    }

    public void updateUnitPrice()
    {
        System.out.println("Ok");
    }
    public AssetRecord buyShare(double a) {return new AssetRecord(new StockAsset("", "", 1), 1);}

}
