import java.util.Timer;
import java.util.TimerTask;

public class Asset
{
    private String name;
    private String symbol;
    private double unitValue;
    private String assetType;
    private double maxChange;
    private double lastChange;  // Last change in price

    static final String COMMODITY = "commodity";
    static final String CRYPTOCURRENCY = "cryptocurrency";
    static final String CURRENCY = "currency";
    static final String STOCK = "stock";

    public Asset(String name, String symbol, double initialValue, String assetType) throws AssetTypeException
    {
        this.name = name;
        this.symbol = symbol.toUpperCase();
        this.unitValue = initialValue;

        // Throw error if asset type is not one of the given asset types
        assetType = assetType.toLowerCase();
        if (assetType != COMMODITY &&
            assetType != CRYPTOCURRENCY &&
            assetType != CURRENCY &&
            assetType != STOCK)
        {throw new AssetTypeException(assetType);}
        this.assetType = assetType;

        lastChange = 0;
        updateUnitPrice();
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getAssetType() {
        return assetType;
    }

    public double getLastChange() {
        return lastChange;
    }

    public double getRawUnitValue() {
        return unitValue;
    }

    public String getCurrencyUnitValue() {
        return CurrencyConverter.convert(this.unitValue);
    }

    // Returns a receipt of a share bought
    public Trade createTrade(double units) {
        return new Trade(this, getRawUnitValue(), units);
    }

    public String toString()
    {
        return String.format("%s (%s)", this.name, this.symbol);
    }

    // Updates the asset price
    private void updateUnitPrice()
    {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                maxChange = unitValue / 5000.0;  // Max change per second is 0.02%
                lastChange = Random.randomDouble(-maxChange, maxChange);
                unitValue += lastChange;
            }
        }, 0, 1000);
    }

}
