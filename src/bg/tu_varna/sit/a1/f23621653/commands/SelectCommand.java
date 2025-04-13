package bg.tu_varna.sit.a1.f23621653.commands;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.XMLElement;

public class SelectCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length != 2) {
            System.out.println("Usage: select <id> <key>");
            return;
        }
        String id = args[0];
        String key = args[1];

        XMLElement element = xmlDocument.getElementById(id);
        if (element == null) {
            System.out.println("Element with id \"" + id + "\" not found.");
            return;
        }
        String value = element.getAttribute(key);
        if (value == null) {
            System.out.println("Attribute \"" + key + "\" not found in element with id \"" + id + "\".");
        } else{
            System.out.println("Value of attribute \""+key+"\": "+value);
        }
    }
}
