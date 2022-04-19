import java.awt.*;

public class WarningPrompt extends Prompt
{
    private String warningText;
    private Button okButton;

    public WarningPrompt(String warningText)
    {
        super();
        this.warningText = warningText;
        initialisePrompt();
    }

    @Override
    public void initialisePrompt()
    {
        panel = GUI.createPanel(0, 1);
        panel.add(new Label(warningText));
        okButton = GUI.createButton(panel, "OK");

        okButton.addActionListener(new CloseAction());

        this.add(panel);
        gui.initialiseWindow();
    }
}
