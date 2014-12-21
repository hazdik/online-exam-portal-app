package com.onlineportal.online.exam.database.table;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Row implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Field> fieldList;

    public Row(){
        fieldList = new ArrayList<Field>();
    }
    
    public  Field  setValue(String fieldName,Object value){

        try{
        Field field = getField(fieldName);
        field.setValue(value);
        return  field;
        }
        catch (NoSuchFieldException e){
          Field field = new Field(fieldName,String.class);
          field.setValue(value);
          fieldList.add(field);
          return field;

        }

    }

    public  void addField(Field field){
        setValue(field.getFieldName(),field.getValue());
    }


    public Field getField(String fieldName) throws NoSuchFieldException{

        for(Field field : fieldList){
            if(field.getFieldName().equals(fieldName)){
                return  field;
            }
        }
        throw new NoSuchFieldException();

    }
    public void deleteField(int index){
    	fieldList.remove(index);
    }
    
    private List<Field> getAllFields(){
        return  fieldList;
    }







}
