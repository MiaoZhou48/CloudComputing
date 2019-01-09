package NginxServer.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileCreator {
    private String filePath;
    private List<String> strList;

    public static void writeFile(String filePath, List<String> strList) {

        File file = new File(filePath);
        try{
            if(file.exists()){

            }else{
                //文件不存在需创建
                file.createNewFile();

            }

            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (String tmp:strList
            ) {
                bufferedWriter.write(tmp+"\n");
            }
            //bufferedWriter.write("hello!");
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean FileExist(String filePath){
        File file=new File(filePath);
        if(file.exists()){
            //文件已经存在
          return true;
        }
        return false;
    }


}
