package TerminalServer.nginxServer;

import TerminalServer.entity.Standard;
import TerminalServer.nginx.MainRun;
import net.sf.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class socketGet extends Thread {
    public void run(){
        System.out.println("get is running");
        Scanner in=new Scanner(System.in);
        InputStreamReader isr;
        BufferedReader br;
        OutputStreamWriter osw;
        BufferedWriter rw;

        try {
            while(true)
            {
                ServerSocket serverSocket=new ServerSocket(4447);
                //Thread.sleep(500);
                Socket socket = serverSocket.accept();
                isr=new InputStreamReader(socket.getInputStream());
                br=new BufferedReader(isr);
                String str=br.readLine();
                JSONObject object=JSONObject.fromObject(str);

                //judge 存在：
                if(MainRun.standards.get(object.get("ipAddress").toString())!=null){
                    MainRun.standards.get(object.get("ipAddress").toString()).setCpuURate(Double.valueOf(object.get("cpuURate").toString()));
                    MainRun.standards.get(object.get("ipAddress").toString()).setRamURate(Double.valueOf(object.get("ramURate").toString()));
                    MainRun.standards.get(object.get("ipAddress").toString()).setDiskURate(Double.valueOf(object.get("diskURate").toString()));
                    MainRun.standards.get(object.get("ipAddress").toString()).setNetURate(Double.valueOf(object.get("netURate").toString()));
                }else{
                    Standard tmpStand = new Standard();
                    tmpStand.setIpAddress(object.get("ipAddress").toString());
                    tmpStand.setCpuURate(Double.valueOf(object.get("cpuURate").toString()));
                    tmpStand.setRamURate(Double.valueOf(object.get("ramURate").toString()));
                    tmpStand.setDiskURate(Double.valueOf(object.get("diskURate").toString()));
                    tmpStand.setNetURate(Double.valueOf(object.get("netURate").toString()));

                    //当前服务器队列
                    MainRun.standards.put(tmpStand.getIpAddress(),tmpStand);
                    //原有服务器信息队列添加
                    MainRun.orgStandards.put(tmpStand.getIpAddress(),tmpStand);
                }

                System.out.println(MainRun.standards.toString());
                br.close();
                socket.close();

                serverSocket.close();           //关闭socket
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
