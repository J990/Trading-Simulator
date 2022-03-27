public abstract class Asset
{
    private String name;
    private String symbol;
    private double unitValue;

    public Asset(String name, String symbol, double initialValue)
    {
        this.name = name;
        this.symbol = symbol.toUpperCase();
        this.unitValue = initialValue;
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

    // Returns a receipt of a share bought
    public Trade createTrade(double units) {
        return new Trade(this, getRawUnitValue(), units);
    }

    public String toString()
    {
        return String.format("%s (%s)", this.name, this.symbol);
    }

    // Updates the asset price
    public abstract void updateUnitPrice();

}
