package sql_lite_engine_java.parser;

public class SelectStatement implements Statement {
    public String tableName;

    public SelectStatement(String tableName) {
        this.tableName = tableName;
    }

    @Override
    public Type getType() { return Type.SELECT; }
}
