package TerminalServer.terminalServer;

import TerminalServer.entity.Standard;
import TerminalServer.nginx.MainRun;
import TerminalServer.entity.Standard;
import net.sf.json.JSONObject;
import TerminalServer.nginx.MainRun;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class SocketPost extends Thread {

    public void run(){
        System.out.println("post is run!");

//       while(true){
           try {
               System.out.println("try");

               if (MainRun.standardNode.getIpAddress() == null) {
                   Thread.sleep(5000);
               }
               OutputStreamWriter osw;
               BufferedWriter rw;

               System.out.println("post !!");
               Socket socket = new Socket("192.168.43.128", 4449);
               Thread.sleep(5000);
               osw = new OutputStreamWriter(socket.getOutputStream());
               rw = new BufferedWriter(osw);
               Standard standard = MainRun.standardNode;
               JSONObject jsonObject = JSONObject.fromObject(standard);
               rw.write(jsonObject.toString() + "\n");
               rw.close();

               System.out.println("post: " + MainRun.standardNode);
           } catch (Exception e) {
               // TODO: handle exception
           }
//       }
    }

}
