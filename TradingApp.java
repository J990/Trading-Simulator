import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;

public class TradingApp
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

    private static Label accountBalance;
    private static Button accountDepositButton;

    private static Label portfolioProfit;

    public static AssetManager assets = new AssetManager();
    private static AssetManager shownAssets = assets;

    public static void panelHandler(String destination)
    {
        String subDest = "";
        int partitionIdx = destination.indexOf("-");
        if (partitionIdx != -1)
        {
            subDest = destination.substring(partitionIdx + 1);
            destination = destination.substring(0, partitionIdx);
        }
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
                if (!subDest.equals("")) showOwnedAsset(assets.getAssetBySymbol(subDest));
                break;
            case MARKET:
                showMarket();
                if (!subDest.equals("")) showAsset(assets.getAssetBySymbol(subDest));
                break;
            default:
                throw new DestinationError(destination);  // Invalid panel destination name
        }
        panelStack.push(destination);
    }

    // Shows the menu panel which is shown upon starting the app
    // Allows navigation to: Account, Portfolio, Asset Market
    public static void showMenu()
    {
        JPanel p = GUI.createPanel(0, 1);
        p.add(new Label("Trading Simluator 1.0", Label.CENTER));
        p.add(GUI.createNavigationButton(p, "View Account", ACCOUNT));
        p.add(GUI.createNavigationButton(p, "View Portfolio", PORTFOLIO));
        p.add(GUI.createNavigationButton(p, "Buy Assets", MARKET));
        gui.displayPanel(p);
    }

    // Shows the account management panel
    // Displays the user's account balance and lets them deposit money into the account
    public static void showAccountScreen()
    {
        JPanel p = GUI.createPanel(0, 1);
        p.add(new Label("Account Manager", Label.CENTER));
        accountBalance = GUI.createLabel(p, "Balance: " + account.getConvertedBalance());
        accountDepositButton = GUI.createButton(p, "Deposit");

        accountDepositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                InputPrompt depositPrompt = new InputPrompt("Enter amount: ", "Deposit");
                depositPrompt.addSubmitListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ev) {
                        try {
                            double amount = Double.parseDouble(depositPrompt.getInput());
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

        p.add(BACK_BUTTON);
        gui.displayPanel(p);
    }

    public static void showPortfolioScreen()
    {
        JPanel p = GUI.createPanel(0, 2);
        Portfolio portfolio = account.getPortfolio();
        ArrayList<Asset> portfolioAssets = portfolio.getAssets();
        ArrayList<Button> assetButtons = new ArrayList<Button>();
        ArrayList<Label> assetLabels = new ArrayList<Label>();

        p.add(new Label("My Portfolio", Label.CENTER));
        portfolioProfit = new Label(portfolio.toString(), Label.CENTER);
        p.add(portfolioProfit);

        for (Asset a: portfolioAssets)
        {
            Profit assetProfit = portfolio.generateAssetProfit(a);
            Button b = GUI.createButton(p, a.toString());
            Label l = GUI.createLabel(p, Converter.convert(portfolio.getCurrentAssetValue(a)) + "(" + Converter.convertWithSign(assetProfit.getProfit())+ ")");
            if (assetProfit.getProfit() >= 0) l.setForeground(new Color(0, 180, 64));
            else l.setForeground(new Color(200, 0, 0));
            b.addActionListener(new NavigationAction(PORTFOLIO + "-" + a.getSymbol()));
            assetButtons.add(b);
            assetLabels.add(l);
        }

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                portfolioProfit.setText(portfolio.toString());
                for (int i = 0; i < portfolioAssets.size(); i++)
                {
                    Asset a = portfolioAssets.get(i);
                    Label l = assetLabels.get(i);
                    Profit assetProfit = portfolio.generateAssetProfit(a);
                    if (assetProfit.getProfit() >= 0) l.setForeground(new Color(0, 180, 64));
                    else l.setForeground(new Color(200, 0, 0));
                    l.setText(Converter.convert(portfolio.getCurrentAssetValue(a)) + " (" + Converter.convertWithSign(assetProfit.getProfit())+ ")");
                }
            }
        }, 0, 1500);

        p.add(BACK_BUTTON);
        gui.displayPanel(p);
    }

    public static void showOwnedAsset(Asset a)
    {
        if (a == null) return;

        JPanel p = GUI.createPanel(0, 1);

        Portfolio portfolio = account.getPortfolio();
        Label investmentValue = GUI.createLabel(p);
        Label unitValue = GUI.createLabel(p);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                investmentValue.setText("My Investment: " + Converter.convert(portfolio.getCurrentAssetValue(a)));
                unitValue.setText(a.toString() + " @ " + a.getCurrencyUnitValue());
                gui.resizeWindow();
            }
        }, 0, 1500);

        Button buyButton = GUI.createButton(p, "Buy");
        Button sellButton = GUI.createButton(p, "Sell");

        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                new BuyPrompt(a);
            }
        });

        sellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                new SellPrompt(a);
            }
        });

        p.add(BACK_BUTTON);
        gui.displayPanel(p);
    }

    public static void showMarket()
    {
        JPanel p = GUI.createPanel(0, 2);
        
        ArrayList<Button> assetButtons = new ArrayList<Button>();
        ArrayList<Label> assetLabels = new ArrayList<Label>();

        p.add(new Label("Stock Market"));
        Label assetCategory = GUI.createLabel(p, "All Assets");
        final String all = "All";
        final String commodities = "Commodities";
        final String currencies = "Currencies";
        final String cryptocurrencies = "Cryptocurrencies";
        final String stocks = "Stocks";
        String[] filterOptions = { all, commodities, currencies, cryptocurrencies, stocks };
        JComboBox<String> filters = new JComboBox<>(filterOptions);

        filters.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                switch ((String)filters.getSelectedItem())
                {
                    case all:
                        shownAssets = assets;
                        assetCategory.setText("All Assets");
                        break;
                    case commodities:
                        shownAssets = assets.getAssetsOfCategory(Asset.COMMODITY);
                        assetCategory.setText("Commodities");
                        break;
                    case currencies:
                        shownAssets = assets.getAssetsOfCategory(Asset.CURRENCY);
                        assetCategory.setText("Currencies");
                        break;
                    case cryptocurrencies:
                        shownAssets = assets.getAssetsOfCategory(Asset.CRYPTOCURRENCY);
                        assetCategory.setText("Cryptocurrencies");
                        break;
                    case stocks:
                        shownAssets = assets.getAssetsOfCategory(Asset.STOCK);
                        assetCategory.setText("Stocks");
                        break;
                    default:
                        break;
                }
                showMarket();  // Refresh screen
            }
        });
        p.add(filters);
        p.add(new Label());  // To move assets to the correct line

        for (Asset a: shownAssets)
        {
            Button b = GUI.createButton(p, a.toString());
            Label l = GUI.createLabel(p, a.getCurrencyUnitValue());
            b.addActionListener(new NavigationAction(MARKET + "-" + a.getSymbol()));
            assetButtons.add(b);
            assetLabels.add(l);
        }

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                for (int i = 0; i < shownAssets.size(); i++)
                {
                    try {
                        Asset a = shownAssets.get(i);
                        Label l = assetLabels.get(i);
                        l.setText(a.getCurrencyUnitValue());
                        gui.resizeWindow();
                    } catch (Exception e) {}
                }
            }
        }, 0, 1500);

        p.add(BACK_BUTTON);
        gui.displayPanel(p);
    }

    public static void showAsset(Asset a)
    {
        JPanel p = GUI.createPanel(0, 1);

        p.add(new Label(a.toString()));
        Label unitValue = GUI.createLabel(p, "1 UNIT = " + a.getCurrencyUnitValue());

        Button buyButton = GUI.createButton(p, "Buy");
        Button sellButton = GUI.createButton(p, "Sell");

        buyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                new BuyPrompt(a);
            }
        });

        sellButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                new SellPrompt(a);
            }
        });

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                unitValue.setText("1 UNIT = " + a.getCurrencyUnitValue());
            }
        }, 0, 1500);

        p.add(BACK_BUTTON);
        gui.displayPanel(p);
    }

    public static void main(String[] args)
    {
        initialiseApp();
    }

    private static void initialiseApp()
    {
        // Opens the account for the session
        account = new Account();
        account.addMoney(10000);

        // Creates all the Asset objects
        initialiseAssets();

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

    private static void initialiseAssets()
    {
        // COMMODITIES
        assets.add(new Asset("Oil", "OIL", 100, Asset.COMMODITY));
        assets.add(new Asset("Gold", "GOLD", 2000, Asset.COMMODITY));
        assets.add(new Asset("Silver", "SILVER", 25, Asset.COMMODITY));
        assets.add(new Asset("Platinum", "PLATINUM", 1010, Asset.COMMODITY));

        // CURRENCIES
        assets.add(new Asset("United States Dollar", "USD", 0.77, Asset.CURRENCY));
        assets.add(new Asset("Euro", "EUR", 0.83, Asset.CURRENCY));

        // CRYPTOCURRENCIES
        assets.add(new Asset("Bitcoin", "BTC", 40000, Asset.CRYPTOCURRENCY));
        assets.add(new Asset("Ethereum", "ETH", 3000, Asset.CRYPTOCURRENCY));
        assets.add(new Asset("Solana", "SOL", 100, Asset.CRYPTOCURRENCY));
        assets.add(new Asset("Binance Coin", "BNB", 320, Asset.CRYPTOCURRENCY));
        assets.add(new Asset("Cardano", "ADA", 0.71, Asset.CRYPTOCURRENCY));

        // STOCKS
        assets.add(new Asset("Amazon.com, Inc.", "AMZN", 3000, Asset.STOCK));
        assets.add(new Asset("Alphabet Inc.", "GOOG", 3000, Asset.STOCK));
        assets.add(new Asset("Unity Software Inc.", "U", 66, Asset.STOCK));
        assets.add(new Asset("Tesla", "TSLA", 1000, Asset.STOCK));
        assets.add(new Asset("GameStop Corp.", "GME", 150, Asset.STOCK));
        
        assets.sortByName();
    }
}