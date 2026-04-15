package neondb;

import neondb.parser.Lexer;
import neondb.parser.Parser;
import neondb.parser.Statement;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("NeonDB Engine Starting...");
        System.out.println("Type '.exit' to quit.");

        ExecutionEngine engine = new ExecutionEngine();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("neondb > ");
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
