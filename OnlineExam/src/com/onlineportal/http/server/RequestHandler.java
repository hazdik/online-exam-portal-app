package com.onlineportal.http.server;


import com.onlineportal.http.server.util.HttpRequestUtil;
import com.onlineportal.http.server.util.HttpResponseUtil;

import java.io.IOException;
import java.net.Socket;

public class RequestHandler implements  Runnable{
    Socket socket;



    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
           // InputStream inputStream= socket.getInputStream();
            HttpRequest httpRequest = HttpRequestUtil.parseRequest(socket.getInputStream());
            //Print the information got from request
//            OutputStream outputStream = socket.getOutputStream();
            HttpResponseUtil.sendStaticResource(httpRequest,socket.getOutputStream());
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
