import java.awt.*;
import java.awt.event.*;
import java.awt.BorderLayout;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton; 
import javax.swing.JTextField;  
import javax.swing.JPasswordField; 
import javax.swing.JOptionPane;
import java.util.HashMap;
/**
 * a panel which could let user to sign in or sign up
 *
 * @Xiangyi Zeng
 * @March 30th,2018
 */
public class SignIn_UpPanel{
    private JPanel signPanel = new JPanel();
    HashMap<String,String> userdata = new HashMap<>();
    private int i = 0;
    private String name,code;
    /**
     * Constructor of sign in/up panel.
     */
    public SignIn_UpPanel(){
        createUserDataSet();
        creatPanel();        
    }
    
    /**
     * Create the gui panel for the sign in/up panel.
     */
    private void creatPanel(){
        signPanel.setLayout(new BorderLayout());
        signPanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        
        JLabel username = new JLabel("user name:");
        signPanel.add(username);
        username.setSize(200,100);
        username.setLocation(50,20);
                
        JLabel password = new JLabel("password:"); 
        signPanel.add(password);
        password.setSize(200,100);
        password.setLocation(50,60);        
        
        JTextField text = new JTextField(); 
        username.add(text);
        text.setSize(190,30); 
        text.setLocation(160,55);        
        JPasswordField passText = new JPasswordField();
        password.add(passText);        
        passText.setSize(190,30);
        passText.setLocation(160,115);
        
        JButton register = new JButton("Sign up");  
        signPanel.add(register,BorderLayout.SOUTH);
        register.setSize(120, 40);
        register.setLocation(60, 180);
        JButton signUp = new JButton("Sign in"); 
        signPanel.add(signUp,BorderLayout.SOUTH);
        signUp.setSize(120, 40);                 
        signUp.setLocation(230, 180);
        register.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                name = text.getText();
                code = passText.getText();
                for(String user : userdata.keySet()){
                    if(!name.equals(user)){
                        userdata.put(name,code); 
                        JOptionPane.showMessageDialog(null,"Congratulation! Your user name is:"+name+
                        "\n"+"Your password is:"+code);
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"Sorry,this user name has been used.");
                    }
                }
            }
        });
        signUp.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                name = text.getText();
                code = passText.getText();
                for(String user : userdata.keySet()){
                    if((!name.equals(user)) || (!code.equals(userdata.get(user))) ){
                        JOptionPane.showMessageDialog(null,"Sorry,your user name or password is not correct.");
                        text.setText("");
                        passText.setText("");
                    }
                    else{
                        JOptionPane.showMessageDialog(null,"You are successful!");
                    }
                }
            }
        });
    }
    
    /**
     * add some user data which have been registered.
     */
    private void createUserDataSet(){
        userdata.put("MaidiXu","m123456");
        userdata.put("Betty","b123123");
        userdata.put("Xiaopiao","x321321");
        userdata.put("Mhjmhj","m132432");
    }
    
    /**
     * return the signPanel.
     */
    public JPanel getPanel(){
        return signPanel;
    }
}
