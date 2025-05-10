package bg.tu_varna.sit.a1.f23621653;

import java.util.Scanner;

public class XMLParserApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        XMLDocument xmlDocument = new XMLDocument();
        CommandHandler commandHandler = new CommandHandler(xmlDocument);

        System.out.println("XML Parser started. Type help for a list of commands.");

        while (true) {
            System.out.println("Enter a command: ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase(CommandName.EXIT.getCommandName())) {
                System.out.println("Exiting...");
                break;
            }

            CommandParser.ParsedCommand parsed = CommandParser.parse(input);
            commandHandler.executeCommand(parsed.getCommandName(), parsed.getCommandArgs());
        }
        scanner.close();
    }
}
