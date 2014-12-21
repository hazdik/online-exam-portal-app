package com.onlineportal.online.exam.database.table;

import java.util.List;


public interface ITable {
    Row createNewRow();
    void deleteRow(Row row);
    List<Row> getAllRows();
    String getTableName();
    void truncateTable();
    Row updateRow(Row row, String fieldName, String fieldValue);
}
