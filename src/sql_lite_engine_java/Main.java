package sql_lite_engine_java;

import sql_lite_engine_java.parser.Lexer;
import sql_lite_engine_java.parser.Parser;
import sql_lite_engine_java.parser.Statement;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("sql_lite_engine_java Engine Starting...");
        System.out.println("Type '.exit' to quit.");

        ExecutionEngine engine = new ExecutionEngine();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("sql_lite_engine_java > ");
            if (!scanner.hasNextLine()) break;
            
            String input = scanner.nextLine().trim();

            if (input.equals(".exit")) {
                System.out.println("Bye!");
                break;
            }

            if (input.isEmpty()) {
                continue;
            }

            try {
                Lexer lexer = new Lexer(input);
                Parser parser = new Parser(lexer.tokenize());
                Statement stmt = parser.parse();
                
                if (stmt != null) {
                    engine.execute(stmt);
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
}
