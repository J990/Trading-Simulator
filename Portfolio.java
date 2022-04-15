import java.util.ArrayList;

public class Portfolio
{
    private ArrayList<Trade> trades;  // Keeps track of all the trades made
    private ArrayList<Asset> assets;
    private ArrayList<AssetRecord> receipts;
    private Profit portfolioProfit;

    public Portfolio()
    {
        trades = new ArrayList<Trade>();
        assets = new ArrayList<Asset>();
        receipts = new ArrayList<AssetRecord>();
        portfolioProfit = new Profit();
    }

    public ArrayList<Asset> getAssets() {
        return assets;
    }

    public double getProfit() {
        return portfolioProfit.getProfit();
    }

    public double getPercentageProfit() {
        return portfolioProfit.getPercentageProfit();
    }

    public double getInitialValue()
    {
        double total = 0.0d;
        for (Trade t: trades)
            total += t.getOpenValue();
        return total;
    }

    public double getCurrentValue()
    {
        double total = 0.0d;
        for (Trade t: trades)
            total += t.currentValue();
        return total;
    }

    public double getCurrentAssetValue(Asset a)
    {
        double total = 0.0d;
        for (Trade t: trades)
            if (t.getAsset().equals(a))
                total += t.currentValue();
        return total;
    }

    public void addTrade(Trade t)
    {
        trades.add(t);
        portfolioProfit.addTrade(t);
        if(!assets.contains(t.getAsset())) assets.add(t.getAsset());
    }

    public void removeTrade(Trade t) throws UnknownTradeException
    {
        if (hasTrade(t) != null)
        {
            trades.remove(t);
            removeAsset(t.getAsset());
            receipts.add(t.generateReceipt());
        }
        else {
            throw new UnknownTradeException(t);
        }
    }

    private void removeAsset(Asset a)
    {
        for (Trade trade: trades)
            if (trade.getAsset().equals(a)) return;
        assets.remove(a);
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

    @Override
    public String toString()
    {
        return Converter.convert(getCurrentValue()) + " (" + portfolioProfit.getPercentageString() + ")";
    }

}
