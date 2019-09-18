package com.espweb.chronos.network.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Connection {
    public static boolean isConnected() {
        try {
            final String command = "ping -c 1 cronos.vizzarconsultoria.com";
            return Runtime.getRuntime().exec(command).waitFor() == 0;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isOnline() {
        try {
            int timeoutMs = 1500;
            Socket sock = new Socket();
            SocketAddress sockaddr = new InetSocketAddress("107.190.135.122", 53);

            sock.connect(sockaddr, timeoutMs);
            sock.close();

            return true;
        } catch (IOException e) { return false; }
    }
}
