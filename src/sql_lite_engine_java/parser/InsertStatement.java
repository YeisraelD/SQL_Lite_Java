package sql_lite_engine_java.parser;
import java.util.List;

public class InsertStatement implements Statement {
    public String tableName;
    public List<Object> values;

    public InsertStatement(String tableName, List<Object> values) {
        this.tableName = tableName;
        this.values = values;
    }

    @Override
    public Type getType() { return Type.INSERT; }
}
