package bg.tu_varna.sit.a1.f23621653;

import bg.tu_varna.sit.a1.f23621653.commands.core.CommandHandler;
import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;

public class XMLParserApplication {
    public static void main(String[] args) {
        XMLDocument xmlDocument = new XMLDocument();
        CommandHandler commandHandler = new CommandHandler(xmlDocument);

        System.out.println("XML Parser started. Type help for a list of commands.");

        while (true) {
            commandHandler.run();
        }
    }
}
