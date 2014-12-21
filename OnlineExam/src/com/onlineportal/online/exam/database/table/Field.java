package com.onlineportal.online.exam.database.table;

import java.io.Serializable;


public class Field<K> implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private K value;
    private Class kind;
    private String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public Field(String fieldName,Class kind){
        this.fieldName = fieldName;
        this.kind=kind;
    }

    public void setValue(K value){
        if(value==null){
            this.value=value;
        }
        else if(kind.isAssignableFrom(value.getClass())){
          this.value=value;
        }

        else throw  new ClassCastException("Value for the field "+fieldName+ " should be of the type"+ kind.getName()+" but sent as"+ value.getClass().getName());


    }

    public K getValue(){
        return value;
    }





}
