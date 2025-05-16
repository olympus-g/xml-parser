package bg.tu_varna.sit.a1.f23621653.commands.xml_specific;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.models.XMLElement;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

/**
 * Command to set or update an attribute of a specific XML element.
 */
public class SetCommand implements Command {
    /**
     * Executes the set command.
     * <p>
     * Expects exactly three arguments:
     * <ul>
     *     <li>id - The element ID.</li>
     *     <li>key - The attribute key.</li>
     *     <li>newValue - The new value for the attribute.</li>
     * </ul>
     * Sets the attribute to the new value, creating it if it doesn't exist,
     * and prints appropriate confirmation messages.
     *
     * @param args        Command arguments: element id, attribute key, and new value.
     * @param xmlDocument The XMLDocument containing the elements.
     */
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length != 3) {
            System.out.println("Usage: set <id> <key> <value>");
            return;
        }
        String id = args[0];
        String key = args[1];
        String newValue = args[2];

        XMLElement element = xmlDocument.getElementById(id);
        if (element == null) {
            System.out.println("Element with id \"" + id + "\" not found.");
            return;
        }

        String oldValue = element.getAttribute(key);
        element.setAttribute(key, newValue);
        if (oldValue == null) {
            System.out.println("Attribute \"" + key + "\" was created with value \"" + newValue + "\".");
        } else {
            System.out.println("Attribute \"" + key + "\" updated from \"" + oldValue + "\" to \"" + newValue + "\".");
        }
    }
}