package NginxServer.nginxServer;

import NginxServer.entity.Standard;
import NginxServer.nginx.MainRun;
import NginxServer.util.JLoadBalance;
import net.sf.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class socketGet extends Thread {
    public void run(){
        System.out.println("get is running");
        InputStreamReader isr;
        BufferedReader br;
        HashMap<String,Integer> flagMap=new HashMap<String, Integer>();
        flagMap.put("192.168.43.250",0);
        flagMap.put("192.168.43.129",0);
        flagMap.put("192.168.43.143",0);
        try {
            ServerSocket serverSocket=new ServerSocket(4449);
            while(true)
            {
                //Thread.sleep(500);
                Socket socket = serverSocket.accept();
                isr=new InputStreamReader(socket.getInputStream());
                br=new BufferedReader(isr);
                String str=br.readLine();
                JSONObject object=JSONObject.fromObject(str);
                //judge 存在：
                System.out.println("返回节点IP： "+ object.get("ipAddress").toString());
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

                flagMap.put(object.get("ipAddress").toString(),1);
                Iterator iter2 = flagMap.entrySet().iterator();
                int f=0;
                while(iter2.hasNext()){
                    Map.Entry entry = (Map.Entry) iter2.next();
                    int val = (Integer) entry.getValue();
                    if(val==0){
                        f=1;
                        break;
                    }
                }
                if(f==0){
                    //更新

                    if(JLoadBalance.doJLoadBalance()){
                        JLoadBalance.getNewWeight();
                    }
                    Iterator iter0 = flagMap.entrySet().iterator();
                    while(iter0.hasNext()){
                        Map.Entry entry = (Map.Entry) iter0.next();
                        String key=(String)entry.getKey();
                        flagMap.put(key,0);
                    }

                }


                System.out.println(MainRun.standards);
                br.close();
                socket.close();
                //serverSocket.close();           //关闭socket

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
