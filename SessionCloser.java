import java.awt.event.*;

public class SessionCloser extends WindowAdapter
{
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
            // SAVE ASSET PRICES TO JSON
        }

        // SAVE PORTFOLIO TRADES TO JSON
        // SAVE BANK BALANCE TO JSON
    }
}
