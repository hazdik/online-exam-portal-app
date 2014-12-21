package com.onlineportal.http.server.util;

import com.onlineportal.http.server.HttpServer;
import com.onlineportal.http.server.api.HttpHandler;
import com.onlineportal.http.server.impl.ComputeResult;
import com.onlineportal.http.server.impl.LoadFromSecondTillLastButOneQuestion;
import com.onlineportal.http.server.impl.LoadLastQuestion;
import com.onlineportal.http.server.impl.LoadQuestion1;
import com.onlineportal.http.server.HttpRequest;
//import com.zinnia.http.server.impl.Servlet2;
//import com.zinnia.http.server.impl.Servlet1;
//import com.zinnia.http.server.impl.Servlet3Prev;
//import com.zinnia.http.server.impl.Servlet4;
import java.io.*;
import java.util.HashMap;

public class HttpResponseUtil {


    //private static final int BUFFER_SIZE = 1024;

    static HashMap<String,HttpHandler> mappings = new HashMap<String,HttpHandler>();

    static {
    mappings.put("/computeResult",new ComputeResult());
    mappings.put("/loadLastQuestion", new LoadLastQuestion());
    mappings.put("/loadFromSecondToLastButOne", new LoadFromSecondTillLastButOneQuestion());
    mappings.put("/getFormValue", new LoadQuestion1());
    }

    public  static void sendStaticResource(HttpRequest httpRequest,OutputStream outputStream) throws IOException {


        //check whether its mapped

        System.out.println(httpRequest.getRequestURI());

        String completeURI = httpRequest.getRequestURI();

        String uri = completeURI.contains("?")?completeURI.substring(0,completeURI.indexOf("?")):completeURI;

        System.out.println("uri is"+uri);

        if (mappings.get(uri)!=null){


            System.out.println("inside if condition"+httpRequest.getRequestMethod());

            HttpHandler httpHandler = mappings.get(uri);

            if(httpRequest.getRequestMethod().equals("GET")){
                httpHandler.doGet(httpRequest, outputStream);
                outputStream.close();
                return;
            }
            else if(httpRequest.getRequestMethod().equals("POST")){
                httpHandler.doPost(httpRequest, outputStream);
                outputStream.close();
                return;
            }


        }

        int returncode = 0;

        FileInputStream fis = null;
        try

        {
            System.out.println(httpRequest.getRequestURI());
            File requestedfile  = new File(HttpServer.documentRoot, httpRequest.getRequestURI());
            if (requestedfile.exists())
            {
                fis    = new FileInputStream(requestedfile);
                System.out.println("Requested File : "+requestedfile);



                PrintWriter out = new PrintWriter(outputStream);

                out.println("HTTP/1.1 200 ok");
                out.println(("Content-Type:  " + httpRequest.getRequestContentType()));
                out.println(("Content-Length: " + requestedfile.length()));
                out.println();
                out.flush();

               // outputStream.write(("Content-Length: " + bytes.length + "\r\n").getBytes());
                byte [] buffer = new byte[1024];
                BufferedInputStream bufferedInputStreamStream = new BufferedInputStream(fis);

                while (bufferedInputStreamStream.read(buffer)!=-1)

                {
                    outputStream.write(buffer);
                }
            }
            else
            {
                // file not found
                returncode = 404;
            }
        }
        catch (Exception e)
        {
            // thrown if cannot instantiate a File object
            returncode = 404;
            e.printStackTrace();
        }
        finally
        {
            if (fis != null)
                fis.close();

        }


        //send error code
        String errorNumber;
        String errorDetail;


           if(returncode>0){

            switch (returncode)
            {
                case 404:
                    errorNumber = "HTTP/1.1 404 File Not Found\r\n";
                    errorDetail = "<h1>WebServer is reporting an error with your"
                    +"request.</h1><h2>Error 404 File Not Found.</h2>";
                    break;
                case 501:
                    errorNumber = "HTTP/1.1 501 Method Not Supported\r\n";
                    errorDetail = "<h1>WebServer is reporting an error with your"+
                    "request..</h1><h2>Error 501 - Requested Method is not supported by this HTTP"+
                    "Server.</h2>";
                    break;
                default:
                    errorNumber = "HTTP/1.1 Unknown Error Number\r\n";
                    errorDetail = "<h1>WebServer is reporting an error with your"
                    +"request.</h1><h2>Sorry, Server has encountered an unexpected"
                    +"error.</h2>";
                    break;
            }
            String errorMessage = errorNumber +
                    "Content-Type: text/html\r\n" +
                    "Content-Length: " + errorDetail.length() + "\r\n" +
                    "\r\n" + errorDetail;
            outputStream.write(errorMessage.getBytes());



    }

        outputStream.close();
    }

    }











