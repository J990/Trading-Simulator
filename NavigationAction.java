import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class NavigationAction implements ActionListener
{
    String destination;
    public NavigationAction(String destination) {this.destination = destination;}
    public void actionPerformed(ActionEvent ev) {TradingApp.panelHandler(destination);}
}
