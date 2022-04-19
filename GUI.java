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
        resizeWindow();
        FRAME.setVisible(true);
    }

    // Removes the current panel and displays the new panel p
    public void displayPanel(JPanel p)
    {
        FRAME.getContentPane().removeAll();
        FRAME.getContentPane().add(p);
        resizeWindow();
        FRAME.revalidate();
    }

    // Makes all components fit inside the window
    // Centers the window on the screen
    public void resizeWindow()
    {
        FRAME.pack();
        FRAME.setLocationRelativeTo(null);
    }

    // Creates and returns a panel with a consistent border, and a grid layout
    public static JPanel createPanel(int rows, int columns)
    {
        JPanel p = new JPanel();
        p.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        p.setLayout(new GridLayout(rows, columns));
        return p;
    }

    public static Label createLabel(JPanel p)
    {
        Label l = new Label();
        l.setAlignment(Label.CENTER);
        p.add(l);
        return l;
    }

    public static Label createLabel(JPanel p, String text)
    {
        Label l = new Label(text, Label.CENTER);
        p.add(l);
        return l;
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
