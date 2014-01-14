/*
 * Access Level guide:
 * -1 = banned
 *  0 = Admin
 *  1 = Normal
 *  2 = Spectator / Guest 
 *  Note: for access Levels beyond 2 please specify their purpose.
 */
package chatapp.resource;

public class User {
private String name;
private char[] pass;
private int accessLevel;
    public User(String n,char[] p,int a){
        name=n;
        pass=p;
        accessLevel=a;
    }
    public User(String n,char[] p) {
        name=n;
        pass=p;
        accessLevel=1;
    }
    public User(String n){
        name=n;
        pass="password1".toCharArray();
        accessLevel=1;
    }
    public boolean isUser(User u){
        boolean rpass=true;
        String otherName=u.getUserName();
        char[] otherPass=u.getPassword();
        //System.out.println(toString()+u.toString());
        int length=0;
        if(otherPass.length!=0&&pass.length!=0){
            for(int x=0;x<pass.length&&rpass;x++){
                if(pass[x]==otherPass[x])
                    rpass=true;
                else
                    rpass=false;
                length++;
            }
        return rpass&&(length==pass.length)&&name.equalsIgnoreCase(otherName);
        }else return false;
    }
    public String getUserName(){return name;}
    char[] getPassword(){return pass;}
    public int getStatus(){return accessLevel;}
    public void setStatus(int l){accessLevel=l;}
    private String concatenate(char [] a){
        String ret="";
        for(char b:a){
            ret+=b+"";
        }
        return ret;
    }
    public String toString(boolean a){
        String passwod=concatenate(pass);
        if(a)
            return "<"+name+", "+passwod+", "+accessLevel+">";
        else
            return "<"+name+", "+accessLevel+">";
    }
    
}
