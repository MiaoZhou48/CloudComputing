package TerminalServer.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class FileCreator {
    public void writeFile(String filePath, List<String> strList) {
        File file = new File(filePath);
        try{
            if(file.exists()){
                //文件已经存在
                System.out.println("file is already exist!");
            }else{
                //文件不存在需创建
                if(file.createNewFile())
                    System.out.println("create file success!");
                else
                    System.out.println("create file fail!");
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

    public static void main(String[] args) {
        FileCreator fileCreator = new FileCreator();
        List<String> tmp = Arrays.asList(new String[]{"1", "2"});
        fileCreator.writeFile("src\\statics\\performance.txt",tmp);
    }
}