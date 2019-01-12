# 云计算CloudComputing
CloudComputing final project
云计算大作业
研究方向：云计算环境下动态负载均衡算法的改进与实现

小组名：不喝可乐组

小组成员：贺璐(组长)、周淼、刘伟杰、杜月莹

源文件说明：
1. 本文件包含了Nginx服务器和终端Tomcat服务器的两套源代码，分别是src/main/java/目录下的NginxServer和TerminalServer两个包文件。
2. NginxServer包下涵盖了Nginx服务器端的消息发的发送和接收线程。
3. TerminalServer包下是Tomcat服务器的源代码，包括如何获取Linux环境下的系统信息，如何向Nginx服务器发送Socket以及监听自身端口的源代码。
4. ngx_http_upstream_round_robin.c是我们的动态均衡算法的源代码，将Nginx源码文件替换/重新编译之后便可以运行。
