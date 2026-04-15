package neondb.storage;
import java.util.List;
import java.util.ArrayList;

public class Table {
    private String name;
    private List<Column> columns;
    private List<Row> rows; // In-memory representation for now

    public Table(String name, List<Column> columns) {
        this.name = name;
        this.columns = columns;
        this.rows = new ArrayList<>();
    }

    public String getName() { return name; }
    public List<Column> getColumns() { return columns; }
    public void insertRow(Row row) { this.rows.add(row); }
    public List<Row> getRows() { return rows; }
}
