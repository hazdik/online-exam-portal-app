package com.onlineportal.online.exam.database.table;



import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TableImpl implements ITable, Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Row> rowList;
    private  String tableName;

     public String getTableName() {
        return tableName;
    }

    @Override
    public void truncateTable() {
        rowList.clear();
    }

    TableImpl(String tableName){

        this.tableName = tableName;
        rowList = new ArrayList<Row>();
    }

    public Row createNewRow(){
        Row row = new Row();
        rowList.add(row);
        return row;

    }

    public void deleteRow(Row row){

       rowList.remove(row);
    }

    public List<Row> getAllRows(){
        return rowList;
    }

    public Row updateRow(Row row, String fieldName, String fieldValue){
    	row.setValue(fieldName, fieldValue);
    	return row;
    }



}
