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

    // Used to calculate profit for trades of only one asset
    public Profit(Asset a)
    {
        this();
        asset = a;
    }

    public ArrayList<Trade> getTrades() {
        return trades;
    }

    public double getProfit()
    {
        update();
        return profit;
    }

    public double getPercentageProfit()
    {
        update();
        return percentageProfit;
    }

    public String getPercentageString()
    {
        double roundedProfit = Math.round(getPercentageProfit() * 100.0d) / 100.0d;
        return ((roundedProfit > 0) ? "+" : "") + roundedProfit + "%";
    }

    public void addTrade(Trade t) throws AssetCategoryException
    {
        if (asset != null)
        {
            if (t.getAsset().equals(asset))  // Points to the same asset
                trades.add(t);
            else
                throw new AssetCategoryException(asset, t.getAsset());
        }
        else
            trades.add(t);
        
        profit += t.profit();
        percentageProfit += t.percentageProfit();
    }

    public void removeTrade(Trade t) throws UnknownTradeException
    {
        if (trades.contains(t))
            trades.remove(t);
        else {throw new UnknownTradeException(t);}
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

    @Override
    // In the format:
    // +£10.43 (+24.32%)
    // -£235.07 (-8.93%)
    public String toString()
    {
        update();
        return ((profit > 0) ? "+" : "") + Converter.convert(profit) + " (" + getPercentageString() + ")";
    }

}
