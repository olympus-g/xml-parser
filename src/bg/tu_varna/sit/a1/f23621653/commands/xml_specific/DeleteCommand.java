package bg.tu_varna.sit.a1.f23621653.commands.xml_specific;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.models.XMLElement;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

/**
 * Command to delete an attribute from a specified XML element.
 */
public class DeleteCommand implements Command {
    /**
     * Executes the delete command. Expects exactly two arguments:
     * <ul>
     *     <li>id - The ID of the element from which to delete the attribute.</li>
     *     <li>key - The attribute key to delete.</li>
     * </ul>
     * Prints an error if the element is not found.
     * Otherwise, removes the specified attribute and confirms the deletion.
     *
     * @param args        Command arguments: element id and attribute key.
     * @param xmlDocument The XMLDocument to operate on.
     */
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