package com.keyin.javalang.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @description: blocking 在连接，或者write
 * @author: yinke
 * @create: 2021-04-25 22:51
 **/
public class BIOSocketServer {
    
    private static Logger log = LoggerFactory.getLogger(BIOSocketServer.class);


    public static void handler(Socket clientSocket) throws IOException {
        byte[] bytes = new byte[1024];
        int read = clientSocket.getInputStream().read(bytes);
        log.info("finish reading");
        if (read != -1) {
            log.info("receive data :{}", bytes);
        }

    }
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            log.info("waiting connection");
            Socket clientSocket = serverSocket.accept();
            log.info("has some con");
            handler(clientSocket);
        }

    }
    
    
    
    
    
}
