package com.tianxiao.faas.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IpUtils {

    public static String getLocalHostAddress() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
        }
        return addr.getHostAddress();
    }
}
