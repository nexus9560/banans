package chatapp.resource;
import chatapp.client.LoginWindow;
import chatapp.server.ChatServer;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.JButton.*;
import javax.swing.JTextArea.*;
import javax.swing.JTextField.*;
import javax.swing.JScrollBar.*;

public class ChatWindow extends JFrame{
    private String host="";
    private JButton sendText;
    protected JTextField message;
    protected JTextArea chat;
    protected User curr;
    private LoginWindow logs;
    protected final UserList THELIST=new UserList();
    protected String address="localhost";
    protected Socket serverListener;
    private boolean ts=false;

    public ChatWindow(User u,LoginWindow t) {
        curr=u;
        logs=t;
        defOPS();
        mySetLookAndFeel();
        addWidgets();
        repaint();
    }
    
    public ChatWindow(User u) {
        logs=new LoginWindow();
        logs.setVisible(false);
        curr=u;
        defOPS();
        mySetLookAndFeel();
        addWidgets();
        repaint();
    }

    public ChatWindow(){
        curr=new User("Guest",new char[0],2);
        logs=new LoginWindow();
        logs.setVisible(false);
        defOPS();
        mySetLookAndFeel();
        repaint();
        addWidgets();
        repaint();
    }
    
    private void defOPS(){
        setSize(400,600);
        setTitle("Chat");
        setLayout(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(new Color(238,238,238));
        setVisible(true);
        setupHostname();
    }
    
    private void setupHostname(){
        try{
            host=InetAddress.getLocalHost().getHostName();
        }catch(Exception e){host="potato";}
    }
    
    private void addWidgets(){
        {
            chat=new JTextArea();
            chat.setSize(getWidth()-15,getHeight()-100);
            chat.setFont(new Font("Consolas", 0, 11));
            chat.setLocation(5,5);
            chat.setFocusable(false);
            chat.setLineWrap(true);
            JScrollPane scroll = new JScrollPane(chat);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setSize(chat.getSize());
            scroll.setLocation(chat.getLocation());
            add(scroll);
        }
        {
            sendText = new JButton("Send");
            sendText.setSize(60,24);
            sendText.setLocation(getWidth()-68,getHeight()-52);
            sendText.addKeyListener(new EnterAction());
            sendText.addActionListener(new SendAction());
            add(sendText);
        }
        {
            message=new JTextField(256);
            message.setSize(getWidth()-73,22);
            message.setLocation(5,getHeight()-51);
            message.addKeyListener(new EnterAction());
            add(message);
        }
    }
    
    String timeStamp(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
    
    private void mySetLookAndFeel(){
        try{ 
            UIManager.setLookAndFeel(
            UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e){}
    }
    
    public void sendAction(){
        if(!message.getText().equalsIgnoreCase("")&&curr.getStatus()>-1){
            if(message.getText().charAt(0)=='/'||message.getText().charAt(0)=='\\'){
                commandBlock(message.getText());
                message.setText("");
        }
            else{
                chat.append("["+curr.getUserName()+" @ "+host+"]: "+message.getText()+"\n");
                chat.setForeground(Color.lightGray);
                if(ts)
                    chat.append(timeStamp()+"\n");
                chat.setForeground(Color.BLACK);
                message.setText("");
            }
        }else{
            message.setText("");
        }
    }
    
    private String concatenate(String[] s){
        String ret="";
        boolean first=true;
        for(String s1:s)
            if(!first)
                ret+=s1+" ";
            else
                first=false;
        return ret;
    }
    
    private void commandBlock(String com){
        String coms="";
            coms+="\"/logout\" - logs the user out\n";
            coms+="\"/exit\" - closes window\n";
            coms+="\"/me\" - refers to action of user, \n";
            coms+="\"/status [User] [Status]\" - Changes the status of a user\n";
            coms+="\"/status [User]\" - Checks the status of a\n\t\t    user admins only though\n";
            coms+="\"/server\" - starts a server under your hostname\n";
            coms+="\"/connect [IP]\" - connects to a server of this IP\n";
            coms+="\"/timestamp\" - toggles the timestamp, default off\n";
            coms+="\"/ts\" - see timestamp";
        com=com.replaceAll("\\/", "");
        String[] sent=com.split(" ");
        switch(sent[0]){
                case "logout":logs.setVisible(true);this.dispose();break;
                case "exit":System.exit(0);break;
                case "me":chat.append(curr.getUserName()+concatenate(sent));break;
                case "status":
                    if(checkYourPrivilege(0)){
                        if(THELIST.contains(sent[1])&&sent.length>2&&curr.getStatus()==0){
                            THELIST.setUserStatus(THELIST.get(sent[1]), Integer.parseInt(sent[2]));
                            chat.append("[SERVER]: "+sent[1]+"'s status has been changed\n");
                        }else{chat.append("[SERVER]: "+THELIST.get(sent[1]).toString(false)+"\n");}    
                    }else chat.append("[SERVER]: Sorry you don't have access to that.\n");
                    break;
                case "server":new ChatServer();dispose();break;
                case "connect":chat.append("[SERVER]: Sorry this has not been added yet,\nbut we have trained monkeys on it.\n");break;
                case "timestamp": ts=!ts;break;
                case "ts":ts=!ts;break;
                default: chat.append("Here are a list of commands\n"+coms+"\n");break;
       }
    }
    
    private boolean checkYourPrivilege(int reqd){
        return reqd<=curr.getStatus();
    }
    
    private class NetworkingRelated{}
    
    class EnterAction implements KeyListener{

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if(e.getKeyCode()== KeyEvent.VK_ENTER)
                sendAction();
        }
        
    }
    
    class SendAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            sendAction();
        }
    }
}