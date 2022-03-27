import java.util.Date;

public interface StaticAsset
{
    Asset getAsset();  // To reference the asset traded and reference for the current price
    double getOpenUnitPrice();  // To get the price the asset was bought at
    double getUnits();  // Gets the number of units bought/sold
    Date getDate();  // Gets the date the asset was bought/sold
}
