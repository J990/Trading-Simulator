import java.util.Timer;
import java.util.TimerTask;

public class Asset implements Comparable<Asset>, Record
{
    private String name;
    private String symbol;
    private double unitValue;
    private String category;
    private double maxChange;
    private double lastChange;  // Last change in price
    private Timer updater;

    static final String COMMODITY = "commodity";
    static final String CRYPTOCURRENCY = "cryptocurrency";
    static final String CURRENCY = "currency";
    static final String STOCK = "stock";

    public Asset(String name, String symbol, double initialValue, String category) throws AssetCategoryException
    {
        this.name = name;
        this.symbol = symbol.toUpperCase();
        this.unitValue = initialValue;

        // Throw error if asset category is not one of the given asset categories
        category = category.toLowerCase();
        if (category != COMMODITY &&
            category != CRYPTOCURRENCY &&
            category != CURRENCY &&
            category != STOCK)
        {throw new AssetCategoryException(category);}
        this.category = category;

        lastChange = 0;
        updateUnitPrice();
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCategory() {
        return category;
    }

    public double getLastChange() {
        return lastChange;
    }

    public double getRawUnitValue() {
        return unitValue;
    }

    public String getCurrencyUnitValue() {
        return Converter.convert(this.unitValue);
    }

    // Opens a trade referencing this asset
    public Trade createTrade(double units) {
        return new Trade(this, getRawUnitValue(), units);
    }

    // Updates the asset price
    private void updateUnitPrice()
    {
        updater = new Timer();
        updater.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                maxChange = unitValue / 2500.0d;  // Max change per second is 0.04% of current price
                lastChange = Random.randomDouble(-maxChange, maxChange);
                unitValue += lastChange;
            }
        }, 0, 1000);
    }

    // Stops the asset price from updating
    public void stopUpdate() {
        updater.cancel();
        updater.purge();
    }

    public int compareTo(Asset o) {
        return name.compareTo(o.getName());
    }

    public String toString()
    {
        return String.format("%s (%s)", this.name, this.symbol);
    }

    public String toJSON()
    {
        JSONObject obj = new JSONObject();
        obj.put("name", name);
        obj.put("symbol", symbol);
        obj.put("category", category);
        obj.put("value", unitValue);
        return obj.toString();
    }

}
