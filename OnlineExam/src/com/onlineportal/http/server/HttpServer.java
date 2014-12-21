package com.onlineportal.http.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer implements  Runnable{
	
     ServerSocket serverSocket;
     public  static final String documentRoot="../../../static-files";


    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(9000,1, InetAddress.getByName("127.0.0.1"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        while(true){
            try {
                Socket socket =  serverSocket.accept();
                RequestHandler requestHandler = new RequestHandler(socket);
                new Thread(requestHandler).start();

            } catch (IOException e) {
                e.printStackTrace();
            }


        }



    }

    public static void main (String args[]){
        HttpServer httpServer = new HttpServer();
        new Thread(httpServer).start();

    }







}
