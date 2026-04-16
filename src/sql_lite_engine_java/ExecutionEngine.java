package sql_lite_engine_java;

import sql_lite_engine_java.parser.*;
import sql_lite_engine_java.storage.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class ExecutionEngine {
    private Map<String, TableManager> tables = new HashMap<>();

    public void execute(Statement stmt) throws Exception {
        if (stmt == null) return;
        
        if (stmt instanceof CreateTableStatement) {
            executeCreateTable((CreateTableStatement) stmt);
        } else if (stmt instanceof InsertStatement) {
            executeInsert((InsertStatement) stmt);
        } else if (stmt instanceof SelectStatement) {
            executeSelect((SelectStatement) stmt);
        } else {
            throw new Exception("Unsupported statement type.");
        }
    }

    private void executeCreateTable(CreateTableStatement stmt) throws Exception {
        if (tables.containsKey(stmt.tableName)) {
            throw new Exception("Table already exists: " + stmt.tableName);
        }
        
        // Ensure data directory exists
        java.io.File dataDir = new java.io.File("data");
        if (!dataDir.exists()) {
            dataDir.mkdir();
        }

        Table table = new Table(stmt.tableName, stmt.columns);
        TableManager tm = new TableManager(table, "data/" + stmt.tableName + ".ndb");
        tables.put(stmt.tableName, tm);
        System.out.println("Table " + stmt.tableName + " created.");
    }

    private void executeInsert(InsertStatement stmt) throws Exception {
        TableManager tm = tables.get(stmt.tableName);
        if (tm == null) {
            throw new Exception("Table not found: " + stmt.tableName);
        }
        Table table = tm.getTable();
        List<Column> columns = table.getColumns();

        if (stmt.values.size() != columns.size()) {
            throw new Exception("Column count doesn't match value count.");
        }

        Map<String, Object> data = new HashMap<>();
        for (int i = 0; i < columns.size(); i++) {
            data.put(columns.get(i).getName(), stmt.values.get(i));
        }

        Row row = new Row(data);
        tm.insert(row);
        System.out.println("1 row inserted.");
    }

    public void executeSelect(SelectStatement stmt) throws Exception {
        TableManager tm = tables.get(stmt.tableName);
        if (tm == null) {
            throw new Exception("Table not found: " + stmt.tableName);
        }
        Table table = tm.getTable();
        List<Column> columns = table.getColumns();

        // Print Header
        StringBuilder header = new StringBuilder();
        for (Column c : columns) {
            header.append(c.getName()).append("\t|\t");
        }
        System.out.println(header.toString());
        System.out.println("-".repeat(header.length()));

        // Print Rows
        for (Row row : table.getRows()) {
            StringBuilder rowStr = new StringBuilder();
            for (Column c : columns) {
                rowStr.append(row.getValue(c.getName())).append("\t|\t");
            }
            System.out.println(rowStr.toString());
        }
    }
}
