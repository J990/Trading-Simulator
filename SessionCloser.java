import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SessionCloser extends WindowAdapter
{
    private final String BASE_PATH = "data/";

    public void windowClosing(WindowEvent ev)
    {
        saveState();
        System.exit(0);
    }

    public void saveState()
    {
        for (Asset a: TradingApp.assets)
        {
            System.out.println(a.toString());
        }
        saveAssets();
        saveTrades();
        saveBank();

        // SAVE PORTFOLIO TRADES TO JSON
        // SAVE BANK BALANCE TO JSON
    }

    private void saveAssets()
    {
        for (Asset a : TradingApp.assets)
            a.stopUpdate();
        write(BASE_PATH + "assets.json", TradingApp.assets.toJSON());
    }

    private void saveTrades()
    {
        write(BASE_PATH + "trades.json", TradingApp.account.getPortfolio().toJSON());
    }

    private void saveBank()
    {
        write(BASE_PATH + "bank.json", TradingApp.account.getBankAccount().toJSON());
    }

    private void write(String filePath, String data)
    {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write(data);
            writer.close();
        } catch(IOException e) {System.out.println(e);}
    }
}
