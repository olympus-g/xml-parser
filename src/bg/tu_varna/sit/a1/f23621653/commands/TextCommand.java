package bg.tu_varna.sit.a1.f23621653.commands;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.XMLElement;

public class TextCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length != 1) {
            System.out.println("Usage: text <id>");
            return;
        }

        String id = args[0];
        XMLElement element = xmlDocument.getElementById(id);
        if (element == null) {
            System.out.println("Element with id \"" + id + "\" not found.");
            return;
        }
        String text = element.getText();
        if (text == null || text.isBlank()) {
            System.out.println("Element with id \"" + id + "\" does not contain text.");
        } else {
            System.out.println("Element with id \"" + id + "\" contains text: \"" + text + "\"");
        }
    }
}
