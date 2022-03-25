public class StockAsset extends Asset
{
    

    public StockAsset(String name, String symbol, double initialValue)
    {
        super(name, symbol, initialValue);
    }

    public void updateUnitPrice() {}

    public AssetRecord buyShare(double value)
    {
        
        return new AssetRecord(this, value);
    }
}
