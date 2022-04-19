import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class SellPrompt extends Prompt implements DynamicPrompt
{
    private Account account;
    private Portfolio portfolio;
    private Asset asset;

    private ArrayList<Button> tradeButtons;

    public SellPrompt(Asset a)
    {
        super();
        
        account = TradingApp.account;
        portfolio = account.getPortfolio();
        asset = a;
        tradeButtons = new ArrayList<Button>();

        initialisePrompt();
    }

    public void initialisePrompt()
    {
        panel = GUI.createPanel(0, 1);
        GUI.createLabel(panel, "Sell " + asset.toString());
        GUI.createLabel(panel, "Open Trades:");
        for (Trade t: portfolio.filterByAsset(asset))
        {
            Button b = GUI.createButton(panel, t.toString());
            b.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ConfirmationPrompt prompt = new ConfirmationPrompt("Are you sure you want to sell " + t.getUnits() + " units for " + Converter.convert(t.currentValue()) + "?");
                    prompt.setDynamicPrompt(new DynamicPrompt() {
                        public void update() {
                            Timer timer = new Timer();
                            timer.scheduleAtFixedRate(new TimerTask() {
                                public void run() {
                                    prompt.getConfirmationLabel().setText("Are you sure you want to sell " + t.getUnits() + " units for " + Converter.convert(t.currentValue()) + "?");
                                }
                            }, 0, 1000);
                        }
                    });
                    prompt.addApproveListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ev) {
                            try {
                                account.closeTrade(t);
                                new WarningPrompt("Trade closed successfully!");
                            } catch (UnknownTradeException e) {
                                new WarningPrompt("Unable to close the trade.");
                            }
                        }
                    });
                    prompt.addRejectListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ev) {
                            Button b = ((Button)ev.getSource());
                            ((JFrame)SwingUtilities.getRoot(b)).dispose();
                            new WarningPrompt("Trade closure cancelled.");
                        }
                    });
                }
            });
            tradeButtons.add(b);
            panel.add(b);
        }
        update();
        
        this.add(panel);
        gui.initialiseWindow();
    }

    public void update()
    {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                ArrayList<Trade> trades = account.getPortfolio().filterByAsset(asset);
                for (int i = 0; i < trades.size(); i++)
                {
                    Trade t = trades.get(i);
                    Button b = tradeButtons.get(i);
                    b.setLabel(t.toString());
                }
            }
        }, 0, 3000);
    }
}
