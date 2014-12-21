    package com.onlineportal.http.server;

    public class HttpRequest {

        private  String requestMethod;

        private String  requestURI;

        private  String requestHostName;

        private  String  requestProtocol;

        private String requestContentType;

        private int errorCode;

        public String getRequestMethod() {
            return requestMethod;
        }


        public String getRequestContentType() {
            return requestContentType;
        }

        public void setRequestContentType(String requestContentType) {
            this.requestContentType = requestContentType;
        }

        public void setRequestMethod(String requestMethod) {
            this.requestMethod = requestMethod;
        }

        public String getRequestURI() {
            return requestURI;
        }

        public void setRequestURI(String requestURI) {
            this.requestURI = requestURI;
        }

        public String getRequestHostName() {
            return requestHostName;
        }

        public int getErrorCode() {
            return errorCode;
        }

        public void setErrorCode(int errorCode) {
            this.errorCode = errorCode;
        }

        public void setRequestHostName(String requestHostName) {
            this.requestHostName = requestHostName;
        }

        public String getRequestProtocol() {
            return requestProtocol;
        }

        public void setRequestProtocol(String requestProtocol) {
            this.requestProtocol = requestProtocol;
        }
    }
