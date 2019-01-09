package NginxServer.nginx;

import NginxServer.entity.Standard;
import NginxServer.nginxServer.socketGet;
import NginxServer.nginxServer.socketPost;

import java.util.HashMap;
import java.util.Map;


public class MainRun {
    //ip + standard对象
    public static Map<String, Standard> standards = new HashMap<String, Standard>();

    //初始的各服务器节点性能信息(计算负载均衡)
    public static Map<String,Standard> orgStandards = new HashMap<String, Standard>();
    public static void main(String[] args) {

        socketGet sGet = new socketGet();
        sGet.start();

        socketPost sPost = new socketPost();
        sPost.start();
    }
}
