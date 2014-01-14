package chatapp.client;
import chatapp.resource.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class LoginWindow extends JFrame implements ActionListener, KeyListener{
    public final int INITWIDTH = 50;
    public final int COL1WIDTH = 30;
    public final int FIELDWIDTH = 130;
    public final int SPACEWIDTH = 10;
    public final int TEXTHEIGHT = 25;
    public final int ROW2HEIGHT = 40;
    public final int WINDOWWIDTH = 320;
    public final int WINDOWHEIGHT = 125;
    public boolean errorPainted = false;
    final UserList THELIST=new UserList();
    private JCheckBox rememberMe;
    private NewUserWindow newbs=new NewUserWindow();
    @Override
    public void actionPerformed(ActionEvent e) {
        loginAction();
    }
    
    final void loginAction(){
        User temp = new User(usernom.getText(), passwod.getPassword());
        //System.out.println(temp.toString());
        if(THELIST.contains(temp.getUserName(), temp)){
            if (!rememberMe.isSelected()){
                usernom.setText("");
                passwod.setText("");
            }
            new ChatWindow(THELIST.get(temp.getUserName()), this);
            newbs.dispose();
            dispose();
        }
        else{
            //usernom.setText("");
            passwod.setText("");
            LoginFailed();
        }
    }

    private JTextField usernom;
    private JLabel usernomTitle, passwodTitle, ErrorLabel;
    private JButton log,newUser;
    private JPasswordField passwod;
    public LoginWindow() {
        defOPS();
        addWidgets();
        setVisible(true);
    }
    
    private void defOPS(){
        setSize(WINDOWWIDTH,WINDOWHEIGHT);
        setTitle("Login");
        setLocationRelativeTo(null);
        setLayout(null);
        mySetLookAndFeel();
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    private void addWidgets(){
        {
            usernom=new JTextField();
            usernom.setSize(FIELDWIDTH,TEXTHEIGHT);
            usernom.setLocation(INITWIDTH + COL1WIDTH, SPACEWIDTH);
            usernom.addKeyListener(this);
            usernom.setToolTipText("Enter your username here.");
            add(usernom);
        }
        {
            usernomTitle =new JLabel("Username:");
            usernomTitle.setSize(65,TEXTHEIGHT);
            usernomTitle.setLocation(SPACEWIDTH, SPACEWIDTH);
            usernomTitle.setToolTipText("Enter your username here.");
            add(usernomTitle);
        }
        {
            passwod=new JPasswordField(100);
            passwod.setSize(FIELDWIDTH,TEXTHEIGHT);
            passwod.setLocation(INITWIDTH + COL1WIDTH ,ROW2HEIGHT);
            passwod.addKeyListener(this);
            passwod.setToolTipText("Enter your password here.");
            add(passwod);
        }
        {
            passwodTitle=new JLabel("Password:");
            passwodTitle.setSize(65,TEXTHEIGHT);
            passwodTitle.setLocation(SPACEWIDTH ,ROW2HEIGHT);
            passwodTitle.setToolTipText("Enter your password here.");
            add(passwodTitle);
        }
        {
            rememberMe=new JCheckBox("Remember Me?",false);
            rememberMe.setSize(125,25);
            rememberMe.setLocation(30,70);
            rememberMe.setBackground(new Color(238,238,238));
            add(rememberMe);
        }
        {
            log=new JButton("Login");
            log.setSize(90,25);
            log.setLocation(INITWIDTH + 140 + COL1WIDTH,ROW2HEIGHT);
            log.addActionListener(this);
            log.addKeyListener(this);
            add(log);
        }
        {
            newUser=new JButton("New User");
            newUser.setSize(90,25);
            newUser.setLocation(INITWIDTH + 140 + COL1WIDTH,ROW2HEIGHT+30);
            newUser.addActionListener(new LaunchNewUser());
            newUser.addKeyListener(new LaunchNewUser());
            add(newUser);
        }
        {
            errorPainted = true;
            ErrorLabel=new JLabel("Login Failed!");
            ErrorLabel.setSize(75,25);
            ErrorLabel.setForeground(Color.red);
            ErrorLabel.setLocation(INITWIDTH + 150 + COL1WIDTH,SPACEWIDTH);
        }
    }
    private void mySetLookAndFeel(){
        try{ 
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){}
    }

    public void LoginFailed(){
       if(!errorPainted){
           add(ErrorLabel);
           repaint();
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER)
            loginAction();
    }
    class LaunchNewUser implements ActionListener, KeyListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            newbs.setVisible(true);
            dispose();
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            newbs.setVisible(true);
            dispose();
        }
        
    }
}
