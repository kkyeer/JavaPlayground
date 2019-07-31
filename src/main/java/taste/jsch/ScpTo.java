package taste.jsch;

/**
 * @Author: kkyeer
 * @Description: 使用Scp来上传文件
 * @Date:Created in 14:12 2019/7/31
 * @Modified By:
 */
import com.jcraft.jsch.*;

import java.io.*;

public class ScpTo{
    public static void main(String[] args){
        FileInputStream fis=null;
        try{
            String sourceFile="D:\\tmp\\1.txt";
            String user = "root";
            String host = "101.37.244.168";
            String targetFile = "/tmp/3.txt";

            JSch jsch=new JSch();
            Session session=jsch.getSession(user, host, 22);
            session.setPassword("Lexue611");

            // username and password will be given via UserInfo interface.
            UserInfo ui=new MyUserInfo();
            session.setUserInfo(ui);
            session.connect();

            // exec 'scp -t rfile' remotely
            targetFile = targetFile.replace("'", "'\"'\"'");
            targetFile="'"+targetFile+"'";
            String command="scp " +" -t "+targetFile;
            Channel channel=session.openChannel("exec");
            ((ChannelExec)channel).setCommand(command);

            // get I/O streams for remote scp
            OutputStream out=channel.getOutputStream();
            InputStream in=channel.getInputStream();

            channel.connect();

            if(checkAck(in)!=0){
                throw new RuntimeException("Error while transfer");
            }

            File _lfile = new File(sourceFile);

            // send "C0644 filesize filename", where filename should not include '/'
            long fileSize = _lfile.length();
            command="C0644 "+fileSize+" ";
            if(sourceFile.lastIndexOf('/')>0){
                command+=sourceFile.substring(sourceFile.lastIndexOf('/')+1);
            }
            else{
                command+=sourceFile;
            }
            command+="\n";
            out.write(command.getBytes()); out.flush();
            if(checkAck(in)!=0){
                System.exit(0);
            }

            // send a content of lfile
            fis=new FileInputStream(sourceFile);
            byte[] buf=new byte[1024];
            while(true){
                int len=fis.read(buf, 0, buf.length);
                if(len<=0) break;
                out.write(buf, 0, len); //out.flush();
            }
            fis.close();
            fis=null;
            // send '\0'
            buf[0]=0; out.write(buf, 0, 1); out.flush();
            if(checkAck(in)!=0){
                System.exit(0);
            }
            out.close();

            channel.disconnect();
            session.disconnect();

            System.exit(0);
        }
        catch(Exception e){
            System.out.println(e);
            try{if(fis!=null)fis.close();}catch(Exception ee){}
        }
    }

    static int checkAck(InputStream in) throws IOException{
        int b=in.read();
        // b may be 0 for success,
        //          1 for error,
        //          2 for fatal error,
        //          -1
        if(b==0) return b;
        if(b==-1) return b;

        if(b==1 || b==2){
            StringBuffer sb=new StringBuffer();
            int c;
            do {
                c=in.read();
                sb.append((char)c);
            }
            while(c!='\n');
            if(b==1){ // error
                System.out.print(sb.toString());
            }
            if(b==2){ // fatal error
                System.out.print(sb.toString());
            }
        }
        return b;
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
