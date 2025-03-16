package bg.tu_varna.sit.a1.f23621653;

import java.util.Scanner;

public class XMLParserApplication {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        XMLDocument xmlDocument = new XMLDocument();
        CommandHandler commandHandler = new CommandHandler(xmlDocument);

        System.out.println("XML Parser started. Type help for a list of commands.");

        while(true){
            System.out.println("Enter a command: ");
            String input =scanner.nextLine().trim();

            if(input.equalsIgnoreCase("exit")){
                System.out.println("Exiting...");
                break;
            }

            String[] parts=input.split("\\s+");
            String commandName=parts[0];
            String[] commandArgs=new String[parts.length-1];
            System.arraycopy(parts,1,commandArgs,0,commandArgs.length);
            commandHandler.executeCommand(commandName,commandArgs);
        }
        scanner.close();
    }
}
