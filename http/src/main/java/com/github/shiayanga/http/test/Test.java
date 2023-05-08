package com.github.shiayanga.http.test;

import java.net.*;
import java.util.Enumeration;

public class Test {

    public static void main(String[] args) {
        try {
            InetAddress localHost = Inet4Address.getLocalHost();
            String hostAddress = localHost.getHostAddress();
            String hostName = localHost.getHostName();
            System.out.println("hostAddress is "+ hostAddress +";hostName is " + hostName);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
