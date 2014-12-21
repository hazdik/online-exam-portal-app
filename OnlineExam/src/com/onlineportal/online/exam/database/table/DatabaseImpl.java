package com.onlineportal.online.exam.database.table;

import com.onlineportal.online.exam.database.TableNotFoundException;
import com.onlineportal.online.exam.database.IDatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DatabaseImpl implements IDatabase, Serializable{
	

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  List<ITable> tables;
    private  String databaseName;

    public DatabaseImpl(String databaseName){
         tables = new ArrayList<ITable>();
    }

    @Override
    public ITable createTable(String tableName) {
        TableImpl table = new TableImpl(tableName);
        tables.add(table);
        return table;
    }

    @Override
    public void deleteTable(ITable table) {
        tables.remove(table);
    }

    @Override
    public List<ITable> getAllTables() {
        return tables;
    }

    @Override
    public ITable getTableByName(String name) throws TableNotFoundException {
         for(ITable table: tables){
             if(table.getTableName().equals(name)){
                 return table;
             }
         }
        throw  new TableNotFoundException(" table with name "+name+" not found in database "+databaseName);
    }
    
    public void commit(){
    	try {
			FileOutputStream fileOutputStream = new FileOutputStream("commitContent.ser");
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(this);
			objectOutputStream.close();
			fileOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
