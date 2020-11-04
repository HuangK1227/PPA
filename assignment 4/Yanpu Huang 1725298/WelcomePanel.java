import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Component;

/**
 * Write a description of class WelcomePanel here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WelcomePanel
{

    private JPanel welcome;
    private JLabel priceRange;
    /**
     * Constructor for objects of class WelcomePanel
     */
    public WelcomePanel()
    {
        welcome = new JPanel();
        welcome.setLayout(new BoxLayout(welcome,  BoxLayout.PAGE_AXIS));
        createWelcome();
    }

    public void updateWelcome(){
        priceRange.setText(Viewer.getFromValue() + " to " + Viewer.getToValue());
    }

    private void createWelcome(){

        JLabel welcomeText = new JLabel();

        welcomeText.setText("Welcome to London AirBnB!\n Your selected price range is :");
        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        priceRange = new JLabel();
        welcome.add(welcomeText);
        welcome.add(priceRange);
    }

    public JPanel getPanel(){
        return welcome;
    }
}
