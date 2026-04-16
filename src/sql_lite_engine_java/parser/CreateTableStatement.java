package sql_lite_engine_java.parser;
import sql_lite_engine_java.storage.Column;
import java.util.List;

public class CreateTableStatement implements Statement {
    public String tableName;
    public List<Column> columns;

    public CreateTableStatement(String tableName, List<Column> columns) {
        this.tableName = tableName;
        this.columns = columns;
    }

    @Override
    public Type getType() { return Type.CREATE_TABLE; }
}
