import java.awt.*;
import java.awt.event.*;

public class InputPrompt extends Prompt
{
    private String inputLabelName;
    private String submitLabelName;
    private TextField input;
    private Button submit;
    
    public InputPrompt(String inputLabelName, String submitLabelName)
	{
        super();
        this.inputLabelName = inputLabelName;
        this.submitLabelName = submitLabelName;
		initialisePrompt();
    }

    @Override
    public void initialisePrompt()
    {
        panel = GUI.createPanel(0, 2);

        panel.add(new Label(inputLabelName));
        input = GUI.createTextField(panel, 20);
		submit = GUI.createButton(panel, submitLabelName);

        // Default listener for the submit button
        // Closes the window when pressed
		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
		    	dispose();
			}
	    });

        this.add(panel);
        gui.initialiseWindow();
    }

    public String getInput() {
        return input.getText();
    }

    public void addSubmitListener(ActionListener listener)
	{
		submit.addActionListener(listener);
    }    
}
