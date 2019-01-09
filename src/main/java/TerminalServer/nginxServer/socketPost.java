package TerminalServer.nginxServer;

import TerminalServer.nginx.MainRun;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;

/**
 * 中心节点向服务器集群发送应答response
 * */
public class socketPost extends Thread {
    public void run(){
        System.out.println("post is running");
        InputStreamReader isr;
        BufferedReader br;
        OutputStreamWriter osw;
        BufferedWriter rw;

        try {
            while (true){
                Thread.sleep(5000);
                //遍历MainRun中的服务器队列
                for (Object o : MainRun.standards.entrySet()) {
                    Map.Entry entry = (Map.Entry) o;
                    //ip
                    Object key = entry.getKey();
                    Object val = entry.getValue();
                    Socket socket = new Socket(key.toString(), 4447);
                    osw = new OutputStreamWriter(socket.getOutputStream());
                    rw = new BufferedWriter(osw);
                    rw.write("ok" + "\n");
                    rw.close();
                    socket.close();
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
    public static void main(String[] args) {

    }

}
