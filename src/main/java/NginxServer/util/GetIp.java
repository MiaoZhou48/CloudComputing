package NginxServer.util;

import java.net.Inet4Address;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class GetIp {
    public static String getIpAddrByName() {
        try {
            for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) {
                NetworkInterface item = e.nextElement();
                for (InterfaceAddress address : item.getInterfaceAddresses()) {
                    if (address.getAddress() instanceof Inet4Address) {
                        Inet4Address inet4Address = (Inet4Address) address.getAddress();
                        if(!inet4Address.isLinkLocalAddress() && !inet4Address.isLoopbackAddress()&&!inet4Address.isMCGlobal()&&!inet4Address.isMulticastAddress())
                            //System.out.println("ip = "+inet4Address.getHostAddress());
                            return inet4Address.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
