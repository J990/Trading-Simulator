import java.util.Timer;
import java.util.TimerTask;

public class Asset implements Comparable<Asset>
{
    private String name;
    private String symbol;
    private double unitValue;
    private String category;
    private double maxChange;
    private double lastChange;  // Last change in price

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
                maxChange = unitValue / 2500.0d;  // Max change per second is 0.02%
                lastChange = Random.randomDouble(-maxChange, maxChange);
                unitValue += lastChange;
            }
        }, 0, 1000);
    }

    public int compareTo(Asset o) {
        return name.compareTo(o.getName());
    }

}
