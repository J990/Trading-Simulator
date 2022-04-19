import java.util.ArrayList;

public class JSONObject
{
    private String jsonString;

    public JSONObject()
    {
        jsonString = "";
    }

    // Checks if key:value added is the first
    private boolean isFirst() {return jsonString.equals("");}
    private String keyValuePair(String key, String value) {return "\"" + key + "\": \"" + value + "\"";}
    private String keyObjectPair(String key, String value) {return "\"" + key + "\": " + value;}

    private void addPair(String key, String value)
    {
        if (isFirst())
            jsonString += "{" + keyValuePair(key, value);
        else
            jsonString += ", " + keyValuePair(key, value);
    }

    // If value is already an object, rather than a type
    private void addObjectPair(String key, String value)
    {
        if (isFirst())
            jsonString += "{" + keyObjectPair(key, value);
        else
            jsonString += ", " + keyObjectPair(key, value);
    }

    public void put(String key, Object obj)
    {
        String value = obj.toString();
        addPair(key, value);
    }

    public <T extends Number> void put(String key, T value)
    {
        addObjectPair(key, String.valueOf(value));
    }

    public <T> void put(String key, ArrayList<T> list)
    {
        if (list.size() != 0)
        {
            if (!(list.get(0) instanceof Number))
            {
                String listAsString = "[";
                for (T s: list)
                {
                    listAsString += "\"" + s + "\", ";
                }
                listAsString = listAsString.substring(0, listAsString.length() -2) + "]";
                addObjectPair(key, listAsString);
            } else {
                addObjectPair(key, list.toString());
            }
        } else {
            addObjectPair(key, "[]");
        }
    }

    public <T extends Record> void putRecordArray(String key, ArrayList<T> list)
    {
        if (list.size() != 0)
        {
            String listAsString = "[";
            for (Record r: list)
            {
                listAsString += r.toJSON() + ", ";
            }
            listAsString = listAsString.substring(0, listAsString.length() -2) + "]";
            addObjectPair(key, listAsString);
        } else {
            addObjectPair(key, "[]");
        }
    }

    public void put(String key, Record obj)
    {
        String value = obj.toJSON();
        addObjectPair(key, value);
    }

    public void put(String key, JSONObject obj)
    {
        String value = obj.toString();
        addObjectPair(key, value);
    }

    public String toString()
    {
        return jsonString + "}";
    }
}
