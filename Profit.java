import java.util.ArrayList;

// Gets the profit calculations for all the trades of a particular asset
// Or multiple different assets
public class Profit
{
    private Asset asset;
    private ArrayList<Trade> trades;
    private double profit;
    private double percentageProfit;

    // Use to calculate profit for multiple different assets
    public Profit()
    {
        asset = null;
        trades = new ArrayList<Trade>();
        profit = 0.0d;
        percentageProfit = 0.0d;
    }

    // Use to calculate profit for one type of asset
    public Profit(Asset a)
    {
        this();
        asset = a;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public double getProfit() {
        return profit;
    }

    public double getPercentageProfit() {
        return percentageProfit;
    }

    public void addTrade(Trade t) throws AssetTypeException
    {
        if (asset != null)
            if (t.getAsset().equals(asset))  // Points to the same asset
                trades.add(t);
            else
                throw new AssetTypeException(asset, t.getAsset());
        else
            trades.add(t);
        
        profit += t.profit();
        percentageProfit += t.percentageProfit();
    }

    public void update()
    {
        profit = 0.0d;
        percentageProfit = 0.0d;
        for (Trade t: trades)
        {
            profit += t.profit();
            percentageProfit += t.percentageProfit();
        }
    }
    
    public static double calculateProfit(ArrayList<Trade> trades)
    {
        double profit = 0.0d;
        for (Trade t: trades)
            profit += t.profit();
        return profit;
    }

    public static double calculatePercentageProfit(ArrayList<Trade> trades)
    {
        double profit = 0.0d;
        for (Trade t: trades)
            profit += t.percentageProfit();
        return profit;
    }

}
