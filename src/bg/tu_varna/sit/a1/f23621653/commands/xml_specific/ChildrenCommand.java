package bg.tu_varna.sit.a1.f23621653.commands.xml_specific;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.models.XMLElement;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

import java.util.List;
import java.util.Map;

/**
 * Command to list all children of a specified XML element,
 * printing each child's tag name along with its attributes.
 */
public class ChildrenCommand implements Command {
    /**
     * Executes the children command. Expects exactly one argument:
     * <ul>
     *     <li>id - The ID of the parent element.</li>
     * </ul>
     * Prints an error if the element is not found or if it has no children.
     * Otherwise, prints each child's tag and attributes in a formatted style.
     *
     * @param args        Command arguments: the parent element id.
     * @param xmlDocument The XMLDocument to operate on.
     */
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length != 1) {
            System.out.println("Usage: children <id>");
            return;
        }

        String id = args[0];
        XMLElement parent = xmlDocument.getElementById(id);

        if (parent == null) {
            System.out.println("Element with id \"" + id + "\" not found.");
            return;
        }
        List<XMLElement> children = parent.getChildren();
        if (children.isEmpty()) {
            System.out.println("Element with id \"" + id + "\" has no children.");
            return;
        }

        System.out.println("Attributes of children of element with id \"" + id + "\":");

        for (XMLElement child : children) {
            StringBuilder tagBuilder = new StringBuilder();
            tagBuilder.append("<").append(child.getTagName());
            for (Map.Entry<String, String> attr : child.getAttributes().entrySet()) {
                tagBuilder.append(" ").append(attr.getKey()).append("=\"").append(attr.getValue()).append("\"");
            }
            tagBuilder.append(">");
            System.out.println("- " + tagBuilder);
        }

    }
}
