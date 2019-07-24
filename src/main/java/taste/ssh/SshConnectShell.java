package taste.ssh;

import com.jcraft.jsch.*;

/**
 * @Author: kkyeer
 * @Description: 试一试shell
 * @Date:Created in 15:53 2019/7/23
 * @Modified By:
 */
class SshConnectShell {
    public static void main(String[] args) throws JSchException {
        JSch jSch = new JSch();
        String host = "192.168.0.117";
        String user = "mayuan";
        Session session = jSch.getSession(user,host,22);
        session.setPassword("mayuan");
        session.setUserInfo(
                new MyUserInfo() {
                    @Override
                    public boolean promptYesNo(String str) {
                        return true;
                    }
                }
        );
        session.connect();
        Channel channel = session.openChannel("shell");
        channel.setInputStream(System.in);
        channel.setOutputStream(System.out);
        channel.connect();
    }



    public static abstract class MyUserInfo
            implements UserInfo, UIKeyboardInteractive{
        public String getPassword(){ return null; }
        public boolean promptYesNo(String str){ return false; }
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
