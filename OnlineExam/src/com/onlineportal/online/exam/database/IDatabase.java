package com.onlineportal.online.exam.database;

import com.onlineportal.online.exam.database.table.ITable;

import java.util.List;

/**
  Interface Database behavior.
 */
public interface IDatabase {
    ITable createTable (String tableName);
    void deleteTable(ITable table);
    List<ITable> getAllTables();
    ITable getTableByName(String name) throws TableNotFoundException;
    void commit();
}
