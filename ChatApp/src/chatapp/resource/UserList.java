package chatapp.resource;
import java.util.*;
import java.io.*;
public class UserList {
    private HashMap<String,User> users;
    private HashSet<String> un;
    
    public UserList(){
        readUsers();
    }
    
    final void readUsers(){
        try{
            users=new HashMap<>();
            un=new HashSet<>();
            Scanner ofUsers=new Scanner(new File("lava.jpg"));
            while(ofUsers.hasNextLine()){
                String line=ofUsers.nextLine();
                line=line.replaceAll("[<>]", "");
                String[] buff=line.split(", ");
                //System.out.println(line);
                un.add(buff[0]);
                users.put(buff[0], new User(buff[0],buff[1].toCharArray(),Integer.parseInt(buff[2])));
            }
        }catch(FileNotFoundException f){}
    }
    
    public boolean contains(String s, User u){
        return users.containsKey(s)&&(users.containsKey(s)?users.get(s).isUser(u):false);
    }
    
    public boolean contains(User u){
        return users.containsKey(u.getUserName())&&
                (users.containsKey(u.getUserName())?users.get(u.getUserName()).isUser(u):false);
    }
    public boolean contains(String s){return users.containsKey(s);}
    
    public boolean addNewUser(User u){
        if(!users.containsKey(u.getUserName())&&!un.contains(u.getUserName())){
            users.put(u.getUserName(), u);
            un.add(u.getUserName());
        }
        else
            return false;
        dumpUsersToFile();
        readUsers();
        return true;
    }
    public User get(String s){
        return users.get(s);
    }
    public void setUserStatus(User u,int i){
        users.get(u.getUserName()).setStatus(i);
        dumpUsersToFile();
        readUsers();
    }
    
    private void dumpUsersToFile(){
        Iterator<String> it=un.iterator();
        try{
            PrintStream that=new PrintStream(new File("lava.jpg"));
            while(it.hasNext())
                that.println(users.get(it.next()).toString(true));
        }catch(Exception f){}
        finally{readUsers();}
    }
    
}