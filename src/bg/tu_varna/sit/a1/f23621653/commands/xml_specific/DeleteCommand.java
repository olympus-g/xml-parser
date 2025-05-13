package bg.tu_varna.sit.a1.f23621653.commands.xml_specific;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.XMLElement;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

public class DeleteCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length != 2) {
            System.out.println("Usage: delete <id> <key>");
            return;
        }
        String id = args[0];
        String key = args[1];
        XMLElement element = xmlDocument.getElementById(id);
        if (element == null) {
            System.out.println("Element with id \"" + id + "\" not found.");
            return;
        }
        element.removeAttribute(key);
        System.out.println("Attribute with key \"" + key + "\" has been deleted from element with id \"" + id + "\"");
    }
}