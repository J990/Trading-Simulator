import java.util.ArrayList;
import java.util.Iterator;

public class Portfolio
{
    ArrayList<AssetRecord> assetReceipts;  // Keeps track of all the assets that have been bought
    ArrayList<AssetRecord> assets;
    ArrayList<AssetRecord> soldAssets;  // Keeps track of the assets that have been sold and in what quantities

    public Portfolio()
    {
        assetReceipts = new ArrayList<AssetRecord>();
        assets = new ArrayList<AssetRecord>();
        soldAssets = new ArrayList<AssetRecord>();
    }

    public void addAsset(AssetRecord ar)
    {
        assetReceipts.add(ar);
        Iterator<AssetRecord> it = assets.iterator();
        while (it.hasNext())
        {
            AssetRecord record = it.next();
            if (record.getAsset().getSymbol().equals(ar.getAsset().getSymbol()))  // Same asset
            {
                //
            }
        }
        // Merge repeated assets by adding the units together and removing repeated AssetRecords
    }

    public void removeAsset(Asset a, double value)
    {
        double unitsSold = value / a.getRawUnitValue();
        System.out.println(unitsSold);
        // Do some validation to check if the portfolio has enough units to sell
        // Remove 
    }
}
