import java.util.ArrayList;
import java.util.Iterator;

public class Portfolio
{
    private ArrayList<Trade> trades;  // Keeps track of all the trades made
    private Profit portfolioProfit;

    public Portfolio()
    {
        trades = new ArrayList<Trade>();
        portfolioProfit = new Profit();
    }

    public void addTrade(Trade t)
    {
        trades.add(t);
        try {
            portfolioProfit.addTrade(t);
        } catch (AssetTypeException e) {/* Do nothing as it's a generic asset type */}
    }

    public Profit generateAssetProfit(Asset a)
    {
        Profit assetProfits = new Profit(a);
        for (Trade t: trades)
        {
            try {
                assetProfits.addTrade(t);
            } catch (AssetTypeException e) {/* Do nothing if wrong asset */}
        }
        return assetProfits;
    }

    public Profit getPortfolioProfit() {
        portfolioProfit.update();
        return portfolioProfit;
    }

    public ArrayList<Trade> filterByAsset(Asset a)
    {
        ArrayList<Trade> filteredTrades = new ArrayList<Trade>();
        for (Trade t: trades)
            if (t.getAsset().equals(a))
                filteredTrades.add(t);
        return filteredTrades;
    }

    public ArrayList<Trade> filterByAssetType(Asset a)
    {
        ArrayList<Trade> filteredTrades = new ArrayList<Trade>();
        for (Trade t: trades)
            if (t.getAsset().getClass() == a.getClass())
                filteredTrades.add(t);
        return filteredTrades;
    }

}
