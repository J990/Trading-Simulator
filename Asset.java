public abstract class Asset
{
    private String name;
    private String symbol;
    private float unitValue;

    public Asset(String name, String symbol, float initialValue)
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

    public float getRawUnitValue() {
        return unitValue;
    }

    public String getCurrencyUnitValue() {
        return CurrencyConverter.convert(this.unitValue);
    }

    public String toString()
    {
        return String.format("%s (%s): %s", this.name, this.symbol, getCurrencyUnitValue());
    }

    // Updates the asset price
    public void updateUnitPrice()
    {
        unitValue += 1;
    }

}
