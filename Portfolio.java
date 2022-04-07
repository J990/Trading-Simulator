import java.util.ArrayList;
import java.util.Iterator;

public class Portfolio
{
    private ArrayList<Trade> trades;  // Keeps track of all the trades made
    private ArrayList<AssetRecord> receipts;
    private Profit portfolioProfit;

    public Portfolio()
    {
        trades = new ArrayList<Trade>();
        portfolioProfit = new Profit();
    }

    public void addTrade(Trade t)
    {
        trades.add(t);
        portfolioProfit.addTrade(t);
    }

    public void removeTrade(Trade t) throws UnknownTradeException
    {
        if (hasTrade(t) != null)
        {
            trades.remove(t);
            receipts.add(t.generateReceipt());
        }
        else {
            throw new UnknownTradeException(t);
        }
    }

    private Trade hasTrade(Trade t)
    {
        for (Trade trade: trades)
            if (t.getID() == trade.getID()) return trade;
        return null;
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

    public ArrayList<Trade> filterByAssetType(String assetType)
    {
        ArrayList<Trade> filteredTrades = new ArrayList<Trade>();
        for (Trade t: trades)
            if (t.getAsset().getAssetType().equals(assetType.toLowerCase()))
                filteredTrades.add(t);
        return filteredTrades;
    }

}
