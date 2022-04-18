import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

public class BuyPrompt extends InputPrompt
{
    private Asset asset;
    private final Label TITLE;
    private Label buyPrice;

    public BuyPrompt(Asset a)
    {
        super("Amount to buy", "Buy");
        asset = a;
        TITLE = GUI.createLabel(panel, "Buy " + asset.toString());
        finalisePrompt();
    }

    public void finalisePrompt()
    {
        buyPrice = GUI.createLabel(panel);
        updatePrice();

        addSubmitListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    double amount = Double.parseDouble(getInput());
                    if (amount <= 0 || amount >= 10000000) throw new NumberFormatException();
                    TradingApp.account.openTrade(asset, amount);
                } catch (SmallBalanceException | NumberFormatException e) {
                    if (e instanceof NumberFormatException) new WarningPrompt("Buy value must be a number greater than 0 and less than 10,000,000!");
                    else if (e instanceof SmallBalanceException) new WarningPrompt("Balance is too low! Balance: " + TradingApp.account.getConvertedBalance());
                }
            }
        });

        gui.displayPanel(panel);
    }

    public void updatePrice()
    {
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                buyPrice.setText("1 UNIT = " + asset.getCurrencyUnitValue());
            }
        }, 0, 1500);
    }
}
