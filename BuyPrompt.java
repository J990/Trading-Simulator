import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class BuyPrompt extends InputPrompt implements DynamicPrompt
{
    private Account account;
    private Asset asset;
    private Label buyPrice;

    public BuyPrompt(Asset a)
    {
        super("Amount to buy", "Buy");
        account = TradingApp.account;
        asset = a;
        finalisePrompt();
    }

    // Adds to the initialisePrompt method that has been written in InputPrompt
    public void finalisePrompt()
    {
        buyPrice = GUI.createLabel(panel);
        update();

        addSubmitListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    double amount = Double.parseDouble(getInput());
                    if (!account.openTrade(asset, amount)) throw new NumberFormatException();
                    new WarningPrompt("Purchase of " + account.getRecentTradeUnits() + " units successful!");
                } catch (SmallBalanceException | NumberFormatException e) {
                    if (e instanceof NumberFormatException) new WarningPrompt("Buy value must be a number greater than 0 and less than 10,000,000!");
                    else if (e instanceof SmallBalanceException) new WarningPrompt("Balance is too low! Balance: " + TradingApp.account.getConvertedBalance());
                }
            }
        });
        GUI.createLabel(panel, "Buy " + asset.toString());
        gui.displayPanel(panel);
    }

    public void update()
    {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                buyPrice.setText("1 UNIT = " + asset.getCurrencyUnitValue());
            }
        }, 0, 1500);
    }
}
