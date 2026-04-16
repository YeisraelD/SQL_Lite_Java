package sql_lite_engine_java.parser;

import sql_lite_engine_java.storage.DataType;
import sql_lite_engine_java.storage.Column;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private List<Token> tokens;
    private int position = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Statement parse() throws Exception {
        if (tokens.isEmpty() || peek().type == Token.Type.EOF) return null;
        
        Token token = peek();
        if (token.type == Token.Type.KEYWORD) {
            String val = token.value.toUpperCase();
            if (val.equals("CREATE")) {
                return parseCreateTable();
            } else if (val.equals("INSERT")) {
                return parseInsert();
            } else if (val.equals("SELECT")) {
                return parseSelect();
            }
        }
        throw new Exception("Syntax error: Unknown command");
    }

    private Statement parseCreateTable() throws Exception {
        consume("CREATE");
        consume("TABLE");
        String tableName = consume(Token.Type.IDENTIFIER).value;
        consume("(");
        
        List<Column> columns = new ArrayList<>();
        while (peek().type != Token.Type.PUNCTUATION || !peek().value.equals(")")) {
            String colName = consume(Token.Type.IDENTIFIER).value;
            String typeName = consume(Token.Type.KEYWORD).value;
            DataType type = typeName.equalsIgnoreCase("INT") ? DataType.INT : DataType.STRING;
            columns.add(new Column(colName, type));
            
            if (peek().type == Token.Type.PUNCTUATION && peek().value.equals(",")) {
                consume(",");
            }
        }
        consume(")");
        return new CreateTableStatement(tableName, columns);
    }

    private Statement parseInsert() throws Exception {
        consume("INSERT");
        consume("INTO");
        String tableName = consume(Token.Type.IDENTIFIER).value;
        consume("VALUES");
        consume("(");
        
        List<Object> values = new ArrayList<>();
        while (peek().type != Token.Type.PUNCTUATION || !peek().value.equals(")")) {
            Token t = consume();
            if (t.type == Token.Type.NUMBER) {
                values.add(Integer.parseInt(t.value));
            } else if (t.type == Token.Type.STRING_LITERAL) {
                values.add(t.value);
            }
            if (peek().type == Token.Type.PUNCTUATION && peek().value.equals(",")) {
                consume(",");
            }
        }
        consume(")");
        return new InsertStatement(tableName, values);
    }

    private Statement parseSelect() throws Exception {
        consume("SELECT");
        if(peek().value.equals("*")) {
            consume("*");
        } else {
            // Simplified: we just consume the columns to ignore them and treat it as SELECT * for now
            while(!peek().value.equalsIgnoreCase("FROM")) {
                consume();
            }
        }
        consume("FROM");
        String tableName = consume(Token.Type.IDENTIFIER).value;
        return new SelectStatement(tableName);
    }

    private Token peek() {
        if (position >= tokens.size()) return tokens.get(tokens.size() - 1);
        return tokens.get(position);
    }

    private Token consume() {
        Token t = peek();
        position++;
        return t;
    }

    private Token consume(Token.Type expectedType) throws Exception {
        Token t = consume();
        if (t.type != expectedType) {
            throw new Exception("Expected token type " + expectedType + " but got " + t.type);
        }
        return t;
    }

    private Token consume(String expectedValue) throws Exception {
        Token t = consume();
        if (!t.value.equalsIgnoreCase(expectedValue)) {
            throw new Exception("Expected " + expectedValue + " but got " + t.value);
        }
        return t;
    }
}
