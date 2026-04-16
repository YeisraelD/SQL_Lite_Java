package sql_lite_engine_java.parser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {
    private String input;
    private int position = 0;

    public Lexer(String input) {
        this.input = input;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();
        while (position < input.length()) {
            char c = input.charAt(position);
            
            if (Character.isWhitespace(c)) {
                position++;
            } else if (Character.isLetter(c)) {
                String word = readWord();
                if (isKeyword(word)) {
                    tokens.add(new Token(Token.Type.KEYWORD, word.toUpperCase()));
                } else {
                    tokens.add(new Token(Token.Type.IDENTIFIER, word));
                }
            } else if (Character.isDigit(c)) {
                tokens.add(new Token(Token.Type.NUMBER, readNumber()));
            } else if (c == '\'' || c == '"') {
                tokens.add(new Token(Token.Type.STRING_LITERAL, readString(c)));
            } else {
                tokens.add(new Token(Token.Type.PUNCTUATION, String.valueOf(c)));
                position++;
            }
        }
        tokens.add(new Token(Token.Type.EOF, ""));
        return tokens;
    }

    private String readWord() {
        StringBuilder sb = new StringBuilder();
        while (position < input.length() && (Character.isLetterOrDigit(input.charAt(position)) || input.charAt(position) == '_')) {
            sb.append(input.charAt(position));
            position++;
        }
        return sb.toString();
    }

    private String readNumber() {
        StringBuilder sb = new StringBuilder();
        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            sb.append(input.charAt(position));
            position++;
        }
        return sb.toString();
    }

    private String readString(char quote) {
        StringBuilder sb = new StringBuilder();
        position++; // Skip open quote
        while (position < input.length() && input.charAt(position) != quote) {
            sb.append(input.charAt(position));
            position++;
        }
        if (position < input.length()) {
            position++; // Skip close quote
        }
        return sb.toString();
    }

    private boolean isKeyword(String word) {
        String upper = word.toUpperCase();
        return upper.equals("SELECT") || upper.equals("INSERT") || upper.equals("INTO") ||
               upper.equals("VALUES") || upper.equals("CREATE") || upper.equals("TABLE") ||
               upper.equals("INT") || upper.equals("STRING") || upper.equals("FROM");
    }
}
