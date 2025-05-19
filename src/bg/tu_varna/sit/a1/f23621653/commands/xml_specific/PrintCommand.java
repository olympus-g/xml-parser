package bg.tu_varna.sit.a1.f23621653.commands.xml_specific;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

/**
 * Command to print the current XML document's root element and its contents
 * in a formatted, human-readable way.
 */
public class PrintCommand implements Command {
    /**
     * Executes the print command.
     * <p>
     * If no XML document is loaded (root is null), it notifies the user.
     * Otherwise, prints the formatted XML representation of the root element.
     *
     * @param args        Command arguments (ignored in this command).
     * @param xmlDocument The XMLDocument to print.
     */
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (xmlDocument.getRoot() == null) {
            System.out.print("No XML document loaded.\n");
            return;
        }
        System.out.println("Formatted XML output:\n");
        System.out.println(xmlDocument.getRoot().toFormattedXML(0));
    }
}
