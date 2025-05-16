package bg.tu_varna.sit.a1.f23621653.commands.general;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

/**
 * Command to save the currently opened XML document to its existing file path.
 */
public class SaveCommand implements Command {
    /**
     * Executes the save command by saving the XMLDocument to its current file path.
     * If no file is currently loaded, it notifies the user.
     *
     * @param args        Command arguments (not used here).
     * @param xmlDocument The XMLDocument instance to save.
     */
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        String filePath = xmlDocument.getCurrentFilePath();
        if (filePath == null || filePath.isBlank()) {
            System.out.println("No file loaded.");
            return;
        }
        xmlDocument.saveToFile(filePath);
        System.out.println("Successfully saved file");
    }
}
