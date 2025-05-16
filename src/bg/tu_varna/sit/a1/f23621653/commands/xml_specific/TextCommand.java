package bg.tu_varna.sit.a1.f23621653.commands.xml_specific;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.models.XMLElement;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

/**
 * Command to print the text content of a specified XML element.
 */
public class TextCommand implements Command {
    /**
     * Executes the text command.
     * <p>
     * Expects exactly one argument:
     * <ul>
     *     <li>id - The ID of the element whose text content will be printed.</li>
     * </ul>
     * Prints the text content of the element if present,
     * otherwise informs that the element has no text.
     *
     * @param args        Command arguments: element id.
     * @param xmlDocument The XMLDocument containing the elements.
     */
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
