package TerminalServer.terminalServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketGet extends Thread {

    public void run(){
        System.out.println("get is run!");
        InputStreamReader isr;
        BufferedReader br;

        while(true){
            try {
                    ServerSocket serverSocket=new ServerSocket(4447);
                    Socket socket=serverSocket.accept();
                    isr=new InputStreamReader(socket.getInputStream());
                    br=new BufferedReader(isr);
                    String str=br.readLine();
                    System.out.println(str);
                    if(str.equals("ok")){
                        SocketPost tmpPost = new SocketPost();
                        tmpPost.start();
                    }
                    br.close();
                    socket.close();
                    serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
