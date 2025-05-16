package bg.tu_varna.sit.a1.f23621653;

import bg.tu_varna.sit.a1.f23621653.commands.core.CommandHandler;
import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;

/**
 * The main entry point for the XML parser application.
 * <p>
 * This application provides a simple command-line interface for managing XML documents.
 * It continuously accepts user commands until the "exit" command is entered.
 */
public class XMLParserApplication {
    /**
     * The main method that starts the XML parser application.
     * It initializes the XMLDocument and CommandHandler, then enters an infinite loop
     * to process user commands.
     *
     * @param args Command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        XMLDocument xmlDocument = new XMLDocument();
        CommandHandler commandHandler = new CommandHandler(xmlDocument);

        System.out.println("XML Parser started. Type help for a list of commands.");

        while (true) {
            commandHandler.run();
        }
    }
}
