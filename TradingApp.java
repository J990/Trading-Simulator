import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TradingApp extends Frame
{
    private Label balanceText;
    private Button portfolioButton;
    private Button depositButton;
    private Button withdrawButton;
    private Label portfolioContainer;
    private ArrayList<Label> assetPrices = new ArrayList<Label>();
    static ArrayList<Asset> assets = new ArrayList<Asset>();

    public TradingApp()
    {
        super("Trading Simulator");
        initialiseAssets();
        Account account = new Account();
        account.addMoney(100000);
        System.out.println("1. Buy Stock\n2. Sell Stock\n3. View Portfolio\n4. Deposit\n5. Withdraw");

        this.setLayout(new FlowLayout());

        // Styles
        this.setBackground(new Color(0, 128, 128));
        
        getAssetLabels();

        for (Asset a: assets)
        {
            try {
                account.openTrade(a, 1000);
            } catch (SmallBalanceException e) {System.out.println("Oof");}
        }

        balanceText = new Label("Balance: " + account.getConvertedBalance(), Label.CENTER);
        balanceText.setSize(1280, 100);
        balanceText.setForeground(Color.WHITE);
        //balanceText.setPreferredSize(new Dimension(250, 100));
        this.add(balanceText);

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

        portfolioButton = new Button("View Portfolio");
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
        this.add(portfolioButton);
        TextField tField = new TextField(20);
        tField.setVisible(false);
        this.add(tField);
        depositButton = new Button("Deposit");
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tField.setVisible(true);
                account.addMoney(10000);
                balanceText.setText("Deposited £10,000.00");
                tField.revalidate();
            }
        });
        this.add(depositButton);

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
        this.add(withdrawButton);

        
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

        this.add(test1);
        this.add(portfolioContainer);
        for (Label l: assetPrices)
        {
            this.add(l);
        }

        // Allows window to close
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent ev) {
                System.exit(0);
            }
        });
        
        this.setSize(1280, 720);
		this.setLocationRelativeTo(null); // Centers the window on the screen
        this.setVisible(true);
    }

    public static void main(String[] args)
    {
        new TradingApp();
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
        assets.add(new Asset("Alphabet Inc.", "GOOG", 40000, Asset.STOCK));
        assets.add(new Asset("United States Dollar", "USD", 1.3, Asset.CURRENCY));
        assets.add(new Asset("Euro", "EUR", 1.2, Asset.CURRENCY));
        assets.add(new Asset("Oil", "", 100, Asset.COMMODITY));
        assets.add(new Asset("Gold", "", 2000, Asset.COMMODITY));
    }

}