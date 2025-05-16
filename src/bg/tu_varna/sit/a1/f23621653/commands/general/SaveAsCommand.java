package bg.tu_varna.sit.a1.f23621653.commands.general;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

/**
 * Command to save the current XML document to a specified file path.
 * <p>
 * This command saves the XMLDocument contents to a new file path
 * provided as an argument.
 * </p>
 */
public class SaveAsCommand implements Command {
    /**
     * Executes the saveas command by saving the XMLDocument to the specified file path.
     * If no file path is provided, it notifies the user.
     *
     * @param args        Command arguments, expects the first argument to be the file path.
     * @param xmlDocument The XMLDocument instance to save.
     */
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length != 1) {
            System.out.println("Usage saveas <file>");
            return;
        }
        String filePath = args[0];
        xmlDocument.saveToFile(filePath);
        System.out.println("Successfully saved file");
    }
}
