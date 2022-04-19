import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import java.awt.Button;

public class CloseAction implements ActionListener
{
    public void actionPerformed(ActionEvent ev) {
        Button b = ((Button)ev.getSource());
        ((JFrame)SwingUtilities.getRoot(b)).dispose();
    }
}
