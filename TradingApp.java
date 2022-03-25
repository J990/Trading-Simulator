import java.awt.*;
import java.awt.event.*;

public class TradingApp extends Frame
{
    private Label test;

    public TradingApp()
    {
        super("Trading Simulator");
        Account account = new Account();
        account.addMoney(1435.54);
        Bank b = new Bank();
        try{b.deposit(300);} catch (SmallDepositException err) {}
        System.out.println(b.getBalance());
        TransferRecord tr = new TransferRecord(100);
        System.out.println(tr.toJSON());

        System.out.println("1. Buy Stock\n2. Sell Stock\n3. View Portfolios\n4. Deposit\n5. Withdraw");
        this.setLayout(new FlowLayout());

        // Styles
        this.setBackground(new Color(0, 128, 128));

        test = new Label(account.getConvertedBalance(), Label.CENTER);
        test.setBackground(Color.RED);
        test.setForeground(Color.WHITE);
        this.add(test);

        Button test1 = new Button("Add 100 to balance");
        test1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                account.addMoney(100);
                test.setText(account.getConvertedBalance());
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