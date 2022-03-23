import java.awt.*;
import java.awt.event.*;

public class TradingApp extends Frame
{

    public TradingApp()
    {

        Account account = new Account();

        System.out.println("1. Buy Stock\n2. Sell Stock\n3. View Portfolios\n4. Deposit\n5. Withdraw");
        this.setLayout(new FlowLayout());

        // Styles
        this.setBackground(new Color(0, 128, 128));

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