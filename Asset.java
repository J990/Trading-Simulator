public abstract class Asset
{
    private String name;
    private String symbol;
    private double unitValue;
    private double unitsBought;

    public Asset(String name, String symbol, double initialValue)
    {
        this.name = name;
        this.symbol = symbol.toUpperCase();
        this.unitValue = initialValue;
        unitsBought = 0.0d;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getRawUnitValue() {
        return unitValue;
    }

    public String getCurrencyUnitValue() {
        return CurrencyConverter.convert(this.unitValue);
    }

    public double getUnitsBought() {
        return unitsBought;
    }
    
    public void addUnits(double units) {
        if (Math.abs(units) > unitsBought)
        {
            // Cancel add (raise exception?)
        }
        else
        {
            unitsBought += units;
        }
    }

    public String toString()
    {
        return String.format("%s (%s)", this.name, this.symbol);
    }

    // Updates the asset price
    public abstract void updateUnitPrice();
    // Returns a receipt of a share bought
    public abstract AssetRecord buyShare(double value);

}
