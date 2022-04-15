import java.util.Locale;
import java.text.NumberFormat;

/*
** Converts numbers to a readable currency format
*/
public class Converter
{
    private static Locale locale = new Locale("en", "GB");
    private static NumberFormat formatter = NumberFormat.getCurrencyInstance(Converter.locale);

    public static void changeCurrency(String language, String country)
    {
        Converter.locale = new Locale(language, country);
    }

    public static String convert(Number num)
    {
        return formatter.format(num);
    }

    public static String convertWithSign(Number num)
    {
        return ((num.toString().compareTo("0") > 0) ? "+" : "") + convert(num);
    }
}
