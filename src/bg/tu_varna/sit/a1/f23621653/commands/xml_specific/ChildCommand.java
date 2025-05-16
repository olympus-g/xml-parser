package bg.tu_varna.sit.a1.f23621653.commands.xml_specific;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.models.XMLElement;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

import java.util.List;

/**
 * Command to retrieve and print the nth child element of a specified XML element.
 */
public class ChildCommand implements Command {
    /**
     * Executes the child command. Expects exactly two arguments:
     * <ul>
     *     <li>id - The ID of the parent element.</li>
     *     <li>n - The index of the child element to retrieve.</li>
     * </ul>
     * Prints an error message if arguments are invalid or the element/child is not found.
     *
     * @param args        Command arguments: id and child index.
     * @param xmlDocument The XMLDocument to operate on.
     */
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length != 2) {
            System.out.println("Usage: child <id> <n>");
            return;
        }

        String id = args[0];
        int index;
        try {
            index = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("The second argument must be an integer (child index).");
            return;
        }
        XMLElement parent = xmlDocument.getElementById(id);
        if (parent == null) {
            System.out.println("Element with id \"" + id + "\" not found.");
            return;
        }
        List<XMLElement> children = parent.getChildren();
        if (index < 0 || index >= children.size()) {
            System.out.println("Invalid child index. Element has " + children.size() + " children.");
            return;
        }
        XMLElement child = children.get(index);
        System.out.println("Child at index " + index + " of element with id \"" + id + "\":");
        System.out.println(child.toFormattedXML(0));
    }
}
