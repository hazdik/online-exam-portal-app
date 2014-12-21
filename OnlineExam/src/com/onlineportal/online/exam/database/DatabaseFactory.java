package com.onlineportal.online.exam.database;

import com.onlineportal.online.exam.database.table.DatabaseImpl;

import java.util.HashMap;


public class DatabaseFactory {

    public  static DatabaseFactory databaseFactory;

    private HashMap<String,IDatabase> registeredDatabases;

    private  DatabaseFactory(){

        registeredDatabases = new HashMap<String, IDatabase>();


    }

    public  IDatabase createDatabase(String databaseName){
        if(registeredDatabases.get(databaseName)!=null){

            return  registeredDatabases.get(databaseName);

        }

        IDatabase iDatabase = new DatabaseImpl(databaseName);
        registeredDatabases.put(databaseName,iDatabase);

        return  iDatabase;

    }

    public  static DatabaseFactory getDatabaseFactory(){
        if(databaseFactory==null){
            databaseFactory = new DatabaseFactory();
        }
        return databaseFactory;
    }

}
