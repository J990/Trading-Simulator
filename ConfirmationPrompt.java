import java.awt.*;
import java.awt.event.*;

public class ConfirmationPrompt extends Prompt
{
    private String confirmationMessage;
    private Button approveButton;
    private Button rejectButton;
    private Label confirmationLabel;
    private DynamicPrompt dynamicPrompt;

    public ConfirmationPrompt(String text)
    {
        super();
        confirmationMessage = text;
        approveButton = new Button("Yes");
        rejectButton = new Button("No");
        dynamicPrompt = () -> {};
        initialisePrompt();
    }

    public ConfirmationPrompt(String text, String approveMsg, String rejectMsg)
    {
        this(text);
        approveButton.setLabel(approveMsg);
        rejectButton.setLabel(rejectMsg);
    }

    public void initialisePrompt()
    {
        panel = GUI.createPanel(0, 1);
        confirmationLabel = GUI.createLabel(panel, confirmationMessage);
        panel.add(approveButton);
        panel.add(rejectButton);
        rejectButton.addActionListener(new CloseAction());
        approveButton.addActionListener(new CloseAction());
        dynamicPrompt.update();
        this.add(panel);
        gui.initialiseWindow();
    }

    public Label getConfirmationLabel() {
        return confirmationLabel;
    }

    public void setDynamicPrompt(DynamicPrompt d) {
        dynamicPrompt = d;
        d.update();
    }

    public void addApproveListener(ActionListener l) {approveButton.addActionListener(l);}
    public void addRejectListener(ActionListener l) {rejectButton.addActionListener(l);}
}
