package NginxServer.GetMessge;

import NginxServer.entity.Standard;
import NginxServer.util.GetIp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ServerMessage {
    //获取服务器中文件夹的信息
    public static Standard readFile() {
        File file = new File("C:\\Users\\折一架铁飞机\\Desktop\\2.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            Standard standard=new Standard();
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                String[] a=tempString.split(":");
                //a[0]表示属性名
                //a[1]表示值
                StringBuffer sb = new StringBuffer();
                sb.append("set");
                sb.append(a[0].substring(0,1).toUpperCase());
                sb.append(a[0].substring(1));
                if(a[0]!=null){
                    Field field = standard.getClass().getDeclaredField("cpuURate");
                    Method method=Standard.class.getMethod(sb.toString(),field.getType());
                    method.invoke(standard, Double.parseDouble(a[1]));
                }

                line++;
            }

            String hostAddress= GetIp.getIpAddrByName();
            standard.setIpAddress(hostAddress);
            System.out.println(standard);
            reader.close();
            return standard;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ServerMessage.readFile();
    }
}
