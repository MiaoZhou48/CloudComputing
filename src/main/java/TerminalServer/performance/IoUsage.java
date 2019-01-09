package TerminalServer.performance;

import java.io.*;


/**
 * 采集磁盘IO使用率
 */
public class IoUsage {

    private static IoUsage INSTANCE = new IoUsage();

    private IoUsage(){

    }

    public static IoUsage getInstance(){
        return INSTANCE;
    }

    /**
     * @Purpose:采集磁盘IO使用率
     * @return float,磁盘IO使用率,小于1
     */
    public float get() {
        //System.out.println("开始收集磁盘IO使用率");
        float ioUsage = 0.0f;
        Process pro = null;
        Runtime r = Runtime.getRuntime();
        try {
            String command = "iostat -d -x";
            pro = r.exec(command);
            BufferedReader in = new BufferedReader(new InputStreamReader(pro.getInputStream()));
            String line = null;
            int count =  0;
            while((line=in.readLine()) != null){
                if(++count >= 4){
//					log.info(line);
                    String[] temp = line.split("\\s+");
                    if(temp.length > 1){
                        float util =  Float.parseFloat(temp[temp.length-1]);
                        ioUsage = (ioUsage>util)?ioUsage:util;
                    }
                }
            }
            if(ioUsage > 0){
                ioUsage /= 100;
                ////System.out.println("本节点磁盘IO使用率为: " + ioUsage);
            }
            in.close();
            pro.destroy();
        } catch (IOException e) {
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            //System.out.println("IoUsage发生InstantiationException. " + e.getMessage());
            //System.out.println(sw.toString());
        }
        return ioUsage;
    }

    /**
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        while(true){
            //System.out.println(IoUsage.getInstance().get());
            Thread.sleep(5000);
        }
    }

}
