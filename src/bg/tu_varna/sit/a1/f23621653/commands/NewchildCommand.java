package bg.tu_varna.sit.a1.f23621653.commands;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.XMLElement;

public class NewchildCommand implements Command {
    private static int counter = 1;

    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length != 1) {
            System.out.println("Usage: newchild <id>");
            return;

        }
        String parentId = args[0];
        XMLElement parent = xmlDocument.getElementById(parentId);

        if (parent == null) {
            System.out.println("Element with id \"" + parentId + "\" not found.");
            return;
        }

        String newId;
        do {
            newId = "auto_id_" + counter++;
        } while (xmlDocument.getElementById(newId) != null);
        XMLElement child = new XMLElement(newId);
        child.setTagName("child");
        child.setId(newId);
        child.setAttribute("id", newId);

        parent.addChild(child);
        xmlDocument.setRoot(xmlDocument.getRoot());
        System.out.println("New child with id \"" + newId + "\" added to element \"" + parentId + "\".");
    }
}
