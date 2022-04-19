import java.util.ArrayList;
import java.util.Comparator;

public class AssetManager extends ArrayList<Asset> implements Comparator<Asset>, Record
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

    public AssetManager getAssetsOfCategory(String t)
    {
        AssetManager m = new AssetManager();
        for (Asset a: this)
            if (t.toLowerCase().equals(a.getCategory())) m.add(a);
        return m;
    }

    public void sortByName() {sort(this);}

    public int compare(Asset o1, Asset o2) {
        return o1.compareTo(o2);
    }

    public String toString()
    {
        String s = "[";
        for (Asset a: this)
        {
            s += a.toJSON() + ", ";
        }
        return s.substring(0, s.length() -2) + "]";
    }

    public String toJSON()
    {
        JSONObject obj = new JSONObject();
        obj.putRecordArray("assets", this);
        return obj.toString();
    }
}
