package chatapp.server;
import chatapp.resource.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
public class ChatServer extends ChatWindow {
    
    public ChatServer(){
        curr=new User("Guest",new char[0],2);
        repaint();
    }
    public final void recieveMessage() {
        while (true){
            try{
                Socket s = new Socket("localhost", 9090);
                BufferedReader input =
                    new BufferedReader(new InputStreamReader(s.getInputStream()));
                chat.append(input.readLine()+"\n");
            }catch (Exception e){}
        }
    }
    
    public void sendMessage(){
        try{
            try (ServerSocket listener = new ServerSocket(9090)) {
                while (true) {
                    try (Socket socket = listener.accept()) {
                        PrintWriter outp =new PrintWriter(socket.getOutputStream(), true);
                        outp.println(message.getText()+"\n");
                    }
                }
            }
        }catch (IOException i){}
    }
    
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
                sendMessage();
        }
        
    }
    
    class SendAction implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            sendMessage();
        }
        
    }
}
