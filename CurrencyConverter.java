import java.util.Locale;
import java.text.NumberFormat;

/*
** Converts numbers to a readable currency format
*/
public class CurrencyConverter
{
    private static Locale locale = new Locale("en", "GB");
    private static NumberFormat formatter = NumberFormat.getCurrencyInstance(CurrencyConverter.locale);

    public static void changeCurrency(String language, String country)
    {
        CurrencyConverter.locale = new Locale(language, country);
    }

    public static String convert(Number num)
    {
        return formatter.format(num);
    }
}
