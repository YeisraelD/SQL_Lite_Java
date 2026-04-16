package sql_lite_engine_java.parser;

public interface Statement {
    enum Type { SELECT, INSERT, CREATE_TABLE }
    Type getType();
}
