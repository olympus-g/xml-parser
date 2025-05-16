package bg.tu_varna.sit.a1.f23621653.commands.general;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;
/**
 * Command that closes the currently loaded XML document.
 * This operation clears the loaded XML data and resets the current file path.
 */
public class CloseCommand implements Command {
    /**
     * Executes the close command, resetting the XML document to an empty state.
     * This effectively "closes" the document without saving any changes.
     *
     * @param args        The command arguments (not used in this command).
     * @param xmlDocument The XMLDocument instance to be reset.
     */
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        xmlDocument.setRoot(null);
        xmlDocument.setCurrentFilePath(null);
        System.out.println("XML document closed.");
    }
}
