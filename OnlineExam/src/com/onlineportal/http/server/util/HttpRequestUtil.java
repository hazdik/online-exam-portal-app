package com.onlineportal.http.server.util;

import com.onlineportal.http.server.HttpRequest;

import java.io.IOException;
import java.io.InputStream;


public class HttpRequestUtil {

    public static HttpRequest parseRequest(InputStream inputStream) throws IOException {
        //read the info

        byte[] buffer = new byte[2048];

        StringBuffer stringBuffer = new StringBuffer(2048);

        int i = inputStream.read(buffer);
        for (int j = 0; j < i; j++)

        {
            //copy the data to a string buffer to make manipulation easier
            stringBuffer.append((char) buffer[j]);
        }

        return getHttpRequest(stringBuffer);
    }

//    GET / HTTP/1.1
//    Host: localhost:9000
//    User-Agent: Mozilla/5.0 (X11; Ubuntu; Linux i686; rv:12.0) Gecko/20100101 Firefox/12.0
//    Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//    Accept-Language: en-us,en;q=0.5
//    Accept-Encoding: gzip, deflate
//   Connection: keep-alive

    static HttpRequest getHttpRequest(StringBuffer request) {

        String requestString = request.toString();

        HttpRequest httpRequest = new HttpRequest();
        int errorCode = -1;
        // Strip out the Request Method
        int index1, index2;

        // Locate the first Space in Request
        index1 = requestString.indexOf(' ');

        if (index1 != -1 && index1 > 0) {
            // Extract substring containing request method, read up to first space
            httpRequest.setRequestMethod(requestString.substring(0, index1));
            // Locate second space in request, signifies the end of the requested resource
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1) {
                // Extract substring containing URI
                httpRequest.setRequestURI(requestString.substring(index1 + 1, index2));


                index1 = index2 + 1;
                // Locate first CRLF
                index2 = requestString.indexOf("\r\n", index1);
                if (index2 > index1) {
                    // Extract substring containing Protocol
                    httpRequest.setRequestProtocol(requestString.substring(index1, index2));
                    index1 = index2 + 1;
                    // Locate Second CRLF
                    index2 = requestString.indexOf("\r\n", index1 + 1);
                    if (index2 > index1) {
                        // Extract substring containing Hostname and Port


                        httpRequest.setRequestHostName(requestString.substring(index1 + 1, index2));


                        index1 = index2 + 1;
                        //skip one line
                        index2 = requestString.indexOf("\r\n", index1 + 1);
                        index1 = index2 + 1;
                        index2 = requestString.indexOf("\r\n", index1 + 1);
                        if (index2 > index1) {
                            // Extract content type
                            index1 = requestString.indexOf(' ',index1+1);
                            httpRequest.setRequestContentType(requestString.substring(index1 + 1, requestString.indexOf(",",index1)));


                        }



                    }

                    else {
                        errorCode = 4; // Error in extracting Hostname and Port
                    }
                } else {
                    errorCode = 3; // Error in extracting Protocol Information
                }
            } else {
                errorCode = 2; // Error extracting URI from Request
            }
        } else {
            errorCode = 1; //Could not extract Request Method
        }

        httpRequest.setErrorCode(errorCode);

        return httpRequest;
    }


}


