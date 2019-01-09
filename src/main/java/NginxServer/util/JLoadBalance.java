package NginxServer.util;

import NginxServer.entity.Standard;
import NginxServer.nginx.MainRun;
import NginxServer.entity.Standard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static NginxServer.nginx.MainRun.orgStandards;
import static NginxServer.nginx.MainRun.orgStandards;
import static NginxServer.nginx.MainRun.standards;

public class JLoadBalance {
    //判断是否负载均衡

    public static List<Double> useStates=new ArrayList<Double>();

    public static List<Double> swo=new ArrayList<Double>();
    public static List<Double> uw=new ArrayList<Double>();
    public static List<Double> sw=new ArrayList<Double>();
    public static double t=0.1;
    public static double a=100000;
    public static boolean doJLoadBalance(){

        Iterator iter = MainRun.standards.entrySet().iterator();
        int i=0;
        double sum=0;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String)entry.getKey();
            Standard val = (Standard) entry.getValue();
            Standard orgVal=orgStandards.get(key);
            double s=0;
            s=val.getCpuURate()/orgVal.getCpuURate()+val.getDiskURate()/orgVal.getDiskURate()+val.getNetURate()/orgVal.getNetURate()+val.getRamURate()/orgVal.getRamURate();
            sum+=s;
            useStates.add(1-s);
            i++;
        }

        double u=0;
        u+=(double)(sum/useStates.size());
        //计算标准差
        double tip=0;
        for(int j=0;j<useStates.size();j++){
            tip+=(useStates.get(j)-u)*(useStates.get(j)-u);
        }
        tip=tip/(useStates.size()-1);
        tip= Math.sqrt(tip);

        if(tip>t){
            return true;
        }
        return false;
    }

    public static void getNewWeight(){
        Iterator iter = orgStandards.entrySet().iterator();
        double cpuSum=0;
        double ramSum=0;
        double diskSum=0;
        double netSum=0;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Standard val = (Standard) entry.getValue();
            cpuSum+=val.getCpuURate();
            ramSum+=val.getRamURate();
            netSum+=val.getNetURate();
            diskSum+=val.getDiskURate();
        }

        int i=0;
        Iterator iter1 = orgStandards.entrySet().iterator();
        while(iter1.hasNext()){
            Map.Entry entry = (Map.Entry) iter1.next();
            Standard val = (Standard) entry.getValue();
            double swoItem=a*(0.25*val.getCpuURate()/cpuSum+0.25*val.getRamURate()/ramSum+0.25*val.getNetURate()/netSum+0.25*val.getDiskURate()/diskSum);
            swo.add(swoItem);
            i++;
        }

        for(int j=0;j<swo.size();j++){
            double k=swo.get(j)*(1-useStates.get(j));
            uw.add(k);
        }


        Iterator iter2 = standards.entrySet().iterator();
        int y=0;
        while(iter2.hasNext()){
            Map.Entry entry = (Map.Entry) iter2.next();
            Standard val = (Standard) entry.getValue();
            double mc=1;
            double md=1;
            double mm=1;
            double mw=1;
            if(val.getCpuURate()>=0.3){
                mc=1;
            }else if(val.getCpuURate()<0.3&&val.getCpuURate()>0.1){
                mc=0.5;
            }else{
                mc=0.25;
            }

            if(val.getDiskURate()>0.7){
                md=1;
            }else if(val.getDiskURate()>0.6&&val.getDiskURate()<=0.7){
                md=0.5;
            }else{
                md=0.25;
            }

            if(val.getNetURate()>0.7){
                mw=1;
            }else{
                mw=0.5;
            }

            if(val.getRamURate()>0.7){
                mm=1;
            }else{
                mm=0.5;
            }

            sw.add(uw.get(y)*mc*md*mm*mw);
            y++;
        }

        //将变化写入文件
        //判断问价是否存在
        //写文件
        List<String> writeList=new ArrayList<String>();
        Iterator iter5 = standards.entrySet().iterator();
        int f=0;
        while(iter5.hasNext()){
            Map.Entry entry = (Map.Entry) iter5.next();
            String ipAddress=(String) entry.getKey();
            writeList.add(String.valueOf( sw.get(f).intValue()));
            f++;
        }

        FileCreator.writeFile("/home/zn/Desktop/weight.txt",writeList);
        //System.out.println("writeList"+writeList);
    }
}
