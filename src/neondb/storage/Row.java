package neondb.storage;
import java.util.Map;

public class Row {
    private Map<String, Object> data;

    public Row(Map<String, Object> data) {
        this.data = data;
    }

    public Object getValue(String columnName) {
        return data.get(columnName);
    }
}
