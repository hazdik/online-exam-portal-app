package com.onlineportal.http.server.api;

import com.onlineportal.http.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;

public interface HttpHandler {

    public  void  doGet(HttpRequest httpRequest, OutputStream outputStream) throws IOException;

    public  void  doPost(HttpRequest httpRequest, OutputStream outputStream)throws  IOException;



}
