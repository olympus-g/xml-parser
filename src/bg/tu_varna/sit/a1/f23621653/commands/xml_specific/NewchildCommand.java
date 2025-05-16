package bg.tu_varna.sit.a1.f23621653.commands.xml_specific;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.models.XMLElement;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

/**
 * Command to add a new child element with a unique auto-generated ID
 * to the specified parent element in the XML document.
 */
public class NewchildCommand implements Command {
    private static int counter = 1;

    /**
     * Executes the command to add a new child element.
     * <p>
     * Expects exactly one argument:
     * <ul>
     *     <li>id - the ID of the parent element where the new child will be added.</li>
     * </ul>
     * Generates a unique ID for the new child element automatically,
     * sets its tag name to "child", and adds it to the parent.
     * Prints appropriate messages if the parent element is not found or
     * confirms the addition otherwise.
     *
     * @param args        Command arguments: parent element id.
     * @param xmlDocument The XMLDocument to modify.
     */
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
