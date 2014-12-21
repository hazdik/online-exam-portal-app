package com.onlineportal.online.exam.database;


public class TableNotFoundException extends Exception {
    public TableNotFoundException(String message) {
        super(message);
    }

    public TableNotFoundException() {
    }

    public TableNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TableNotFoundException(Throwable cause) {
        super(cause);
    }
}
