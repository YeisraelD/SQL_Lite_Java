package neondb.parser;

public class Token {
    public enum Type {
        IDENTIFIER,
        KEYWORD,
        STRING_LITERAL,
        NUMBER,
        PUNCTUATION,
        EOF
    }

    public Type type;
    public String value;

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return type + "('" + value + "')";
    }
}
