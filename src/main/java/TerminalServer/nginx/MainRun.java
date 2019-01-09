package TerminalServer.nginx;

import TerminalServer.entity.Standard;
import TerminalServer.performance.Perform;
import TerminalServer.terminalServer.SocketGet;
import TerminalServer.terminalServer.SocketPost;


import java.util.HashMap;
import java.util.Map;

public class MainRun {

    //dangqian jiedian xinxi s
    public static Standard standardNode = new Standard();

    //ip + standard对象
    public static Map<String,Standard> standards = new HashMap<String, Standard>();

    //初始的各服务器节点性能信息(计算负载均衡)
    public static Map<String,Standard> orgStandards = new HashMap<String, Standard>();

    public static void main(String[] args) {

        //geng xin dang qian standardNode:(Thread)
        Perform perform = new Perform();
        perform.start();

        //向队列中的服务器发送OK
        SocketPost spost = new SocketPost();
        spost.start();

        //jian ting nginx fuwuqi (Thread)
        SocketGet sget = new SocketGet();
        sget.start();
    }
}
