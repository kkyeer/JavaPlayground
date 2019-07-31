package taste.jsch;/* -*-mode:java; c-basic-offset:2; indent-tabs-mode:nil -*- */
/**
 * This program will demonstrate remote exec.
 *   $ CLASSPATH=.:../build javac Exec.java
 *   $ CLASSPATH=.:../build java Exec
 * You will be asked username, hostname, displayname, passwd and command.
 * If everything works fine, given command will be invoked
 * on the remote side and outputs will be printed out.
 *
 */
import com.jcraft.jsch.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Exec {
    public static void main(String[] arg){
        try{
            JSch jsch=new JSch();

            Session session=jsch.getSession("root", "192.168.0.117", 22);

            UserInfo ui=new MyUserInfo();
            session.setUserInfo(ui);
            session.setPassword("mayuan");
            session.connect();
            String command="ls -al";
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);
            channel.setInputStream(null);
            ((ChannelExec)channel).setErrStream(System.err);
            InputStream in=channel.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            channel.connect();
            String out;
            while ((out = reader.readLine()) != null) {
                System.out.println(out);
            }

//            while(true){
//                while(in.available()>0){
//                    int i=in.read(buffer, 0, 1024);
//                    if(i<0)break;
//                    System.out.print(new String(buffer, 0, i));
//                }
//                if(channel.isClosed()){
//                    if(in.available()>0) continue;
//                    System.out.println("exit-status: "+channel.getExitStatus());
//                    break;
//                }
//                try{Thread.sleep(1000);}catch(Exception ee){}
//            }
            channel.disconnect();
            session.disconnect();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public static class MyUserInfo
            implements UserInfo, UIKeyboardInteractive {
        public String getPassword(){ return null; }
        public boolean promptYesNo(String str){ return true;}
        public String getPassphrase(){ return null; }
        public boolean promptPassphrase(String message){ return false; }
        public boolean promptPassword(String message){ return false; }
        public void showMessage(String message){ }
        public String[] promptKeyboardInteractive(String destination,
                                                  String name,
                                                  String instruction,
                                                  String[] prompt,
                                                  boolean[] echo){
            return null;
        }
    }
}
