import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class GUI
{
    private final JFrame FRAME;
    private final WindowAdapter WINDOW_CLOSER;

    public GUI(JFrame f, WindowAdapter w)
    {
        FRAME = f;
        WINDOW_CLOSER = w;
    }

    // Sets the application window
    public void initialiseWindow()
    {
        FRAME.addWindowListener(WINDOW_CLOSER);
        FRAME.pack();
        FRAME.setLocationRelativeTo(null);  // Centers the window on the screen
        FRAME.setVisible(true);
    }

    // Removes the current panel and displays the new panel p
    public void displayPanel(JPanel p)
    {
        FRAME.getContentPane().removeAll();
        FRAME.getContentPane().add(p);
        FRAME.pack();
        FRAME.revalidate();
    }

    // Creates and returns a panel with a consistent border, and a grid layout
    public static JPanel createPanel(int rows, int columns)
    {
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        p.setLayout(new GridLayout(rows, columns));
        return p;
    }

    // Creates and returns a button with a NavigationAction listener
    public static Button createNavigationButton(String title, String destination)
    {
        Button b = new Button(title);
        b.addActionListener(new NavigationAction(destination));
        return b;
    }
    // Creates and returns a button with a NavigationAction listener
    // Adds button to a given panel 
    public static Button createNavigationButton(JPanel panel, String title, String destination)
    {
        Button b = createNavigationButton(title, destination);
        panel.add(b);
        return b;
    }

    // Creates and returns a button, and adds it to the given panel
    public static Button createButton(JPanel panel, String title)
    {
        Button b = new Button(title);
        panel.add(b);
        return b;
    }

    // Creates and returns a button, and adds it to the given panel
    public static TextField createTextField(JPanel panel, int length)
    {
        TextField tf = new TextField(length);
        panel.add(tf);
        return tf;
    }
}
