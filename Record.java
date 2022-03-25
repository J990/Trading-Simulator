import java.lang.reflect.Field;

public class Record
{
    public String toJSON()
    {
        String s = "{";
        for (int i = 0; i < this.getClass().getDeclaredFields().length; i++)
        {
            Field field = this.getClass().getDeclaredFields()[i];
            try
            {
                field.setAccessible(true);
                s += String.format("\"%s\": \"%s\",", field.getName(), field.get(this));
                field.setAccessible(false);
            } catch (IllegalAccessException e) {System.err.println(e);}
        }
        return s.substring(0, s.length()  -1) + "}";  // Removes unnecessary comma and closes the bracket
    }
}