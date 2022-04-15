import java.util.ArrayList;

public class AssetManager extends ArrayList<Asset>
{
    public Asset getAssetByName(String n)
    {
        for (Asset a: this)
            if (n.toLowerCase().equals(a.getName().toLowerCase())) return a;
        return null;
    }

    public Asset getAssetBySymbol(String s)
    {
        for (Asset a: this)
            if (s.toUpperCase().equals(a.getSymbol())) return a;
        return null;
    }

    public AssetManager getAssetsOfType(String t)
    {
        AssetManager m = new AssetManager();
        for (Asset a: this)
            if (t.toLowerCase().equals(a.getAssetType())) m.add(a);
        return m;
    }
}
