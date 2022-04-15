import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.Port;
import javax.swing.*;


public class TradingApp extends JFrame
{
    public static Account account;  // User account

    private static JFrame frame;  // Main frame which displays all panels
    private static GUI gui; 
    private static final Button BACK_BUTTON = new Button("Back");

    private static Stack<String> panelStack;
    private static final String MENU = "menu";
    private static final String ACCOUNT = "account";
    private static final String PORTFOLIO = "portfolio";
    private static final String MARKET = "market";

    private static Button menuAccButton;
    private static Button menuPortfolioButton;
    private static Button menuStocksButton;

    private static Label accountHeader;
    private static Label accountBalance;
    private static Button accountDepositButton;
    private static Button accountMenuButton;

    private static Label portfolioProfit;

    private JPanel panel;
    private JButton portfolioButton;
    private Button depositButton;
    private Button withdrawButton;
    private Label portfolioContainer;
    private ArrayList<Label> assetPrices = new ArrayList<Label>();
    static ArrayList<Asset> assets = new ArrayList<Asset>();

    public TradingApp()
    {
        /*
        super("Trading Simulator");
        initialiseAssets();
        Account account = new Account();
        account.addMoney(100000);
        System.out.println("1. Buy Stock\n2. Sell Stock\n3. View Portfolio\n4. Deposit\n5. Withdraw");

        //this.setLayout(new FlowLayout());
        panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setLayout(new GridLayout(0, 1));
        this.add(panel, BorderLayout.CENTER);
        
        getAssetLabels();

        for (Asset a: assets)
        {
            try {
                account.openTrade(a, 1000);
            } catch (SmallBalanceException e) {}
        }

        balanceText = new Label("Bank Balance: " + account.getConvertedBalance(), Label.CENTER);
        balanceText.setSize(1280, 100);
        //balanceText.setPreferredSize(new Dimension(250, 100));
        panel.add(balanceText);

        portfolioContainer = new Label("Profit: " + CurrencyConverter.convert(account.getPortfolio().getPortfolioProfit().getProfit()));
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                for (int i = 0; i < assets.size(); i++)
                {
                    Asset a = assets.get(i);
                    assetPrices.get(i).setText(a.toString() + ": " + a.getCurrencyUnitValue());
                }
                portfolioContainer.setText("Profit: " + CurrencyConverter.convert(account.getPortfolio().getPortfolioProfit().getProfit()));
                TradingApp.this.revalidate();
            }
        }, 0, 1000);

        portfolioButton = new JButton("View Portfolio");
        portfolioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //balanceText.setText("Portfolio");
                portfolioContainer.setText("Profit: " + CurrencyConverter.convert(account.getPortfolio().getPortfolioProfit().getProfit()));
                portfolioContainer.setVisible(true);
                portfolioContainer.revalidate();
                //TradingApp.this.repaint();
                //TradingApp.this.add(new Label("Profit: " + CurrencyConverter.convert(account.getPortfolio().getPortfolioProfit().getProfit())));
            }
        });
        panel.add(portfolioButton);
        TextField tField = new TextField(20);
        tField.setVisible(false);
        panel.add(tField);
        depositButton = new Button("Deposit");
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tField.setVisible(true);
                account.addMoney(10000);
                balanceText.setText("Deposited £10,000.00");
                tField.revalidate();
            }
        });
        panel.add(depositButton);

        Asset a = new Asset("Bitcoin", "BTC", 40000, Asset.CRYPTOCURRENCY);
        withdrawButton = new Button("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    account.openTrade(a, 1000.0);
                    balanceText.setText(account.getConvertedBalance());
                } catch (SmallBalanceException err) {
                    balanceText.setText(account.getConvertedBalance());
                }
            }
        });
        panel.add(withdrawButton);

        
        account.getPortfolio().addTrade(a.createTrade(0.1));
        Button test1 = new Button("See Balance");
        test1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                account.addMoney(100000);
                balanceText.setText(a.toString() + ": " + a.getCurrencyUnitValue());
                TradingApp.this.revalidate();
                //TradingApp.this.repaint();
            }
        });

        panel.add(test1);
        panel.add(portfolioContainer);
        for (Label l: assetPrices)
        {
            panel.add(l);
        }

        // Allows window to close
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
        
        this.pack();
        //this.setSize(1280, 720);
		this.setLocationRelativeTo(null); // Centers the window on the screen
        this.setVisible(true);*/
    }

    public static void initialiseApp()
    {
        // Opens the account for the session
        account = new Account();
        account.addMoney(100000);

        // Creates all the Asset objects
        initialiseAssets();

        try {
            account.openTrade(assets.get(0), 200);
            account.openTrade(assets.get(1), 200);
            account.openTrade(assets.get(2), 200);
            account.openTrade(assets.get(3), 200);
            account.openTrade(assets.get(4), 200);
            account.openTrade(assets.get(5), 200);
            account.openTrade(assets.get(6), 200);
            account.openTrade(assets.get(7), 200);
        } catch (SmallBalanceException e) {}

        // Sets up the back button feature
        panelStack = new Stack<String>();
        BACK_BUTTON.addActionListener(new ActionListener() { // Takes the user to the previous screen
            public void actionPerformed(ActionEvent ev) {
                panelStack.pop();  // Pops current panel
                panelHandler(panelStack.pop());  // Pop again because panelHandler pushes popped item to stack again
            }
        });

        // Builds the GUI and shows the menu screen
        frame = new JFrame("Trading App");
        gui = new GUI(frame, new SessionCloser());
        panelHandler(MENU);
        gui.initialiseWindow();
    }

    public static void panelHandler(String destination)
    {
        String subDest = "";
        int partitionIdx = destination.indexOf("-");
        if (partitionIdx != -1)
            subDest = destination.substring(partitionIdx + 1);
        switch (destination)
        {
            case MENU:
                showMenu();
                break;
            case ACCOUNT:
                showAccountScreen();
                break;
            case PORTFOLIO:
                showPortfolioScreen();
                if (!subDest.equals(""))
                break;
            case MARKET:
                showAssets();
                break;
            default:
                throw new DestinationError(destination);  // Invalid panel destination name
        }
        panelStack.push(destination);
    }

    public static void showMenu()
    {
        JPanel p = GUI.createPanel(0, 1);

        p.add(new Label("Trading Simluator 1.0", Label.CENTER));
        menuAccButton = GUI.createNavigationButton(p, "View Account", ACCOUNT);
        menuPortfolioButton = GUI.createNavigationButton(p, "View Portfolio", PORTFOLIO);
        menuStocksButton = GUI.createNavigationButton(p, "Buy Assets", MARKET);

        //frame.add(p);
        gui.displayPanel(p);
    }

    public static void showAccountScreen()
    {
        JPanel p = GUI.createPanel(0, 1);

        p.add(new Label("Account Manager", Label.CENTER));
        accountBalance = new Label("Balance: " + account.getConvertedBalance(), Label.CENTER);
        p.add(accountBalance);
        accountDepositButton = GUI.createButton(p, "Deposit");
        accountMenuButton = GUI.createNavigationButton(p, "Menu", MENU);
        p.add(BACK_BUTTON);

        accountDepositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                InputPrompt depositPrompt = new InputPrompt("Enter amount: ", "Deposit");
                depositPrompt.addSubmitListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {
                        try {
                            Double amount = Double.parseDouble(depositPrompt.getInput());
                            if (amount > 1000000) {throw new NumberFormatException();}
                            if (account.addMoney(amount)) {accountBalance.setText("Balance: " + account.getConvertedBalance()); gui.resizeWindow();}
                            else {throw new NumberFormatException();}
                        } catch (NumberFormatException e) {
                            new WarningPrompt("Deposit value must be a number greater than 0 and less than 1,000,000!");
                        }
                    }
                });
            }
        });

        gui.displayPanel(p);
    }

    public static void showPortfolioScreen()
    {
        JPanel p = GUI.createPanel(0, 1);

        Portfolio portfolio = account.getPortfolio();
        ArrayList<Asset> portfolioAssets = portfolio.getAssets();
        ArrayList<Button> assetButtons = new ArrayList<Button>();

        p.add(new Label("Your Portfolio", Label.CENTER));
        portfolioProfit = new Label(portfolio.toString(), Label.CENTER);
        p.add(portfolioProfit);

        for (Asset a: portfolioAssets)
        {
            String title = a.toString() + " " + Converter.convert(portfolio.getCurrentAssetValue(a));
            Button b = GUI.createButton(p, title);
            Profit assetProfit = portfolio.generateAssetProfit(a);
            if (assetProfit.getProfit() >= 0) b.setForeground(new Color(0, 180, 0));
            else b.setForeground(new Color(200, 0, 0));
            b.addActionListener(new NavigationAction(PORTFOLIO + "-" + a.getSymbol()));
            assetButtons.add(b);
        }

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                portfolioProfit.setText(portfolio.toString());
                for (int i = 0; i < portfolioAssets.size(); i++)
                {
                    Asset a = portfolioAssets.get(i);
                    Button b = assetButtons.get(i);
                    Profit assetProfit = portfolio.generateAssetProfit(a);
                    if (assetProfit.getProfit() >= 0) b.setForeground(new Color(0, 180, 0));
                    else b.setForeground(new Color(200, 0, 0));
                    String title = a.toString() + " " + Converter.convert(portfolio.getCurrentAssetValue(a));
                    b.setLabel(title);
                }
            }
        }, 0, 1500);

        p.add(BACK_BUTTON);
        gui.displayPanel(p);
    }

    public static void showAssets()
    {
        System.out.println("l");
    }

    public static void main(String[] args)
    {
        //new TradingApp();
        initialiseApp();
    }

    private void getAssetLabels()
    {
        for (Asset a: assets)
        {
            assetPrices.add(new Label(a.toString() + ": " + a.getCurrencyUnitValue()));
        }
    }

    static void initialiseAssets()
    {
        assets.add(new Asset("Bitcoin", "BTC", 40000, Asset.CRYPTOCURRENCY));
        assets.add(new Asset("Ethereum", "ETH", 3000, Asset.CRYPTOCURRENCY));
        assets.add(new Asset("Amazon.com, Inc.", "AMZN", 3000, Asset.STOCK));
        assets.add(new Asset("Alphabet Inc.", "GOOG", 3000, Asset.STOCK));
        assets.add(new Asset("United States Dollar", "USD", 1.3, Asset.CURRENCY));
        assets.add(new Asset("Euro", "EUR", 1.2, Asset.CURRENCY));
        assets.add(new Asset("Oil", "OIL", 100, Asset.COMMODITY));
        assets.add(new Asset("Gold", "GOLD", 2000, Asset.COMMODITY));
    }

}