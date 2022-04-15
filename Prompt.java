import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Prompt extends JFrame
{
    protected GUI gui;
    protected JPanel panel;

    public Prompt()
    {
        gui = new GUI(this, new WindowAdapter() {
			public void windowClosing(WindowEvent ev) {
		    	((JFrame)(ev.getSource())).dispose();
			}
	    });
    }

    public abstract void initialisePrompt();

}
