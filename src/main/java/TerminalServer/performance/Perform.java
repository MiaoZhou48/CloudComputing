package TerminalServer.performance;

import TerminalServer.ip.IpGet;
import TerminalServer.nginx.MainRun;


import java.net.SocketException;

public class Perform extends Thread {

    /**
     * zm
     * gengxin xin neng wen jian
     * */
    public void run(){
        System.out.println("gengxin jiedian xinxi");

        while (true){
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            float cpuUsage = CpuUsage.getInstance().get();
            float memUsage = MemUsage.getInstance().get();
            float ioUsage = IoUsage.getInstance().get();
            float netUsage = NetUsage.getInstance().get();


            try {
                String ip = IpGet.getLinuxLocalIp();
                MainRun.standardNode.setIpAddress(ip);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            MainRun.standardNode.setCpuURate(1 - cpuUsage);
            MainRun.standardNode.setRamURate(1 - memUsage);
            MainRun.standardNode.setDiskURate(1 - ioUsage);
            MainRun.standardNode.setNetURate(1 - netUsage);

            System.out.println("performUpdate: "+MainRun.standardNode);
        }
    }

}
