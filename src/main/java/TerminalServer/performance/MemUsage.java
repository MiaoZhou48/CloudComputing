package TerminalServer.performance;

import java.io.*;

/**
 * 采集内存使用率
 */
public class MemUsage{

    private static MemUsage INSTANCE = new MemUsage();

    private MemUsage(){

    }

    public static MemUsage getInstance(){
        return INSTANCE;
    }

    /**
     * Purpose:采集内存使用率
     * @return float,内存使用率,小于1
     */
    public float get() {
        //System.out.println("开始收集memory使用率");
        float memUsage = 0.0f;
        Process pro = null;
        Runtime r = Runtime.getRuntime();
        try {
            String command = "cat /proc/meminfo";
            pro = r.exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line = null;
            int count = 0;
            long totalMem = 0, freeMem = 0;
            while((line=in.readLine()) != null){
                //System.out.println(line);
                String[] memInfo = line.split("\\s+");
                if(memInfo[0].startsWith("MemTotal")){
                    totalMem = Long.parseLong(memInfo[1]);
                }
                if(memInfo[0].startsWith("MemFree")){
                    freeMem = Long.parseLong(memInfo[1]);
                }
                memUsage = 1- (float)freeMem/(float)totalMem;
                //System.out.println("本节点内存使用率为: " + memUsage);
                if(++count == 2){
                    break;
                }
            }
            in.close();
            pro.destroy();
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            //System.out.println("MemUsage发生InstantiationException. " + e.getMessage());
            //System.out.println(sw.toString());
        }
        return memUsage;
    }

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        while(true){
            //System.out.println(MemUsage.getInstance().get());
            Thread.sleep(5000);
        }
    }
}
