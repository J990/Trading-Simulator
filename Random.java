import java.util.ArrayList;

// Sets out methods to easily get random numbers
// And random items in arrays and lists
public class Random
{
    // Returns a random integer within the range min, max including min and max
    //
    public static int randomInt(int min, int max)
    {
        return (int)Math.rint(Math.random() * (max - min)) + min;
    }

    // Returns a random double within the range min, max including min and max
    //
    public static double randomDouble(double min, double max)
    {
        return (double)(Math.random() * (max - min)) + min;
    }

    // Returns a random item in an array
    public static <T> T randomChoice(T[] array)
    {
        return array[randomInt(0, array.length - 1)];
    }

    // Returns a random item in an ArrayList
    public static <T> T randomChoice(ArrayList<T> list)
    {
        return list.get(randomInt(0, list.size() - 1));
    }
}
