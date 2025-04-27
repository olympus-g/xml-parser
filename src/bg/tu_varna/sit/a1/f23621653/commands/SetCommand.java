package bg.tu_varna.sit.a1.f23621653.commands;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.XMLElement;

public class SetCommand implements Command{
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length != 3) {
            System.out.println("Usage: set <id> <key> <value>");
            return;
        }
        String id=args[0];
        String key=args[1];
        String newValue=args[2];

        XMLElement element=xmlDocument.getElementById(id);
        if (element == null) {
            System.out.println("Element with id \"" + id + "\" not found.");
            return;
        }

        String oldValue=element.getAttribute(key);
        element.setAttribute(key,newValue);
        if (oldValue == null) {
            System.out.println("Attribute \"" + key +  "\" was created with value \"" + newValue + "\".");
        } else{
            System.out.println("Attribute \"" + key + "\" updated from \"" + oldValue + "\" to \"" + newValue + "\".");
        }
    }
}