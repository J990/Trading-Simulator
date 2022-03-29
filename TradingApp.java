import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TradingApp extends Frame
{
    private Label balanceText;
    private Button portfolioButton;
    private Button depositButton;
    private Button withdrawButton;

    public TradingApp()
    {
        super("Trading Simulator");
        Account account = new Account();
        account.addMoney(1000);
        System.out.println("1. Buy Stock\n2. Sell Stock\n3. View Portfolio\n4. Deposit\n5. Withdraw");

        this.setLayout(new FlowLayout());

        // Styles
        this.setBackground(new Color(0, 128, 128));

        balanceText = new Label("Balance: " + account.getConvertedBalance(), Label.CENTER);
        balanceText.setForeground(Color.WHITE);
        //balanceText.setPreferredSize(new Dimension(250, 100));
        this.add(balanceText);

        portfolioButton = new Button("View Portfolio");
        portfolioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                balanceText.setText("Portfolio");
            }
        });
        this.add(portfolioButton);

        depositButton = new Button("Deposit");
        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                balanceText.setText("Deposit");
            }
        });
        this.add(depositButton);

        withdrawButton = new Button("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                balanceText.setText("Withdraw");
            }
        });
        this.add(withdrawButton);

        Button test1 = new Button("See Balance");
        test1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Random rand = new Random();
                account.addMoney(100000);
                StockAsset a = new StockAsset("Bitcoin", "BTC", 40000 + rand.nextDouble() * 1000);
                balanceText.setText(a.toString() + ": " + a.getCurrencyUnitValue());
            }
        });

        this.add(test1);

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

}