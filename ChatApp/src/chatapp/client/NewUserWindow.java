package chatapp.client;
import chatapp.resource.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;
public class NewUserWindow extends JFrame implements ActionListener, KeyListener{
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
    private JTextField usernom;
    private JLabel usernomTitle, passwodTitle, ErrorLabel;
    private JButton create;
    private JPasswordField passwod1,passwod2;
    
    public NewUserWindow() {
        defOPS();
        addWidgets();
        setVisible(false);
    }
    
    public NewUserWindow(String s) {
        defOPS();
        addWidgets();
        usernom.setText(s);
        setVisible(false);
    }
    private void createUser(){
        User temp;
        if(samePass()&&usernom.getText().length()>0){
            temp=new User(usernom.getText(),passwod2.getPassword());
            if(!THELIST.addNewUser(temp)){
                errorPainted=true;
                LoginFailed("User Exists");
                return;
            }
            new ChatWindow(temp);
            dispose();
        }else
            LoginFailed("No Name / Pass");
        
    }
    
    private boolean samePass(){
        boolean same=true;
        char[] pass1=passwod1.getPassword();
        char[] pass2=passwod2.getPassword();
        if(pass1.length==pass2.length&&pass1.length>0&&pass2.length>0){
            for(int x=0;x<pass1.length&&same;x++){
                if(pass1[x]==pass2[x])
                    same=true;
                else
                    same=false;
            }
            return same;
        }
        else
            return false;
    }
    
    private void defOPS(){
        setSize(WINDOWWIDTH,WINDOWHEIGHT);
        setTitle("New User");
        setLocationRelativeTo(null);
        mySetLookAndFeel();
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private void mySetLookAndFeel(){
        try{ 
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){}
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
            passwod1=new JPasswordField(100);
            passwod1.setSize(FIELDWIDTH,TEXTHEIGHT);
            passwod1.setLocation(INITWIDTH + COL1WIDTH ,ROW2HEIGHT);
            passwod1.addKeyListener(this);
            passwod1.setToolTipText("Enter your password here.");
            add(passwod1);
        }
        {
            passwod2=new JPasswordField(100);
            passwod2.setSize(FIELDWIDTH,TEXTHEIGHT);
            passwod2.setLocation(INITWIDTH + COL1WIDTH ,ROW2HEIGHT+30);
            passwod2.addKeyListener(this);
            passwod2.setToolTipText("Enter your password again here.");
            add(passwod2);
        }
        {
            passwodTitle=new JLabel("Password:");
            passwodTitle.setSize(65,TEXTHEIGHT);
            passwodTitle.setLocation(SPACEWIDTH ,ROW2HEIGHT);
            passwodTitle.setToolTipText("Enter your password here.");
            add(passwodTitle);
        }
        {
            create=new JButton("Create");
            create.setSize(75,25);
            create.setLocation(INITWIDTH + 150 + COL1WIDTH,ROW2HEIGHT);
            create.addActionListener(this);
            create.addKeyListener(this);
            add(create);
        }
        {
            ErrorLabel=new JLabel("User Exists");
            ErrorLabel.setSize(100,25);
            ErrorLabel.setForeground(Color.red);
            ErrorLabel.setLocation(INITWIDTH + 150 + COL1WIDTH,SPACEWIDTH);
        }
    }

    public void LoginFailed(String s){
       if(errorPainted){
           ErrorLabel.setText(s);
           add(ErrorLabel);
           repaint();
           passwod1.setText("");
           passwod2.setText("");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        createUser();
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
            createUser();
    }
}
