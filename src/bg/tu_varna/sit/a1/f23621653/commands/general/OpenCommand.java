package bg.tu_varna.sit.a1.f23621653.commands.general;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

import java.io.File;

/**
 * Command to open and load an XML file into the XMLDocument.
 * <p>
 * It takes a file path as an argument, checks if the file exists,
 * and if so, loads the content into the given XMLDocument instance.
 * </p>
 */
public class OpenCommand implements Command {
    /**
     * Executes the open command by loading the XML file at the specified path.
     *
     * @param args        Command arguments, expects the first argument to be the file path.
     * @param xmlDocument The XMLDocument instance where the file will be loaded.
     */
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length != 1) {
            System.out.println("Usage open <file>");
            return;
        }
        String filePath = args[0];

        if(!filePath.endsWith(".xml")){
            System.out.println("Only .xml files can be opened.");
            return;
        }

        File file = new File(filePath);
        System.out.println("Looking for file at: " + file.getAbsolutePath());

        if (!file.exists()) {
            System.out.println("File not found");
            return;
        }
        xmlDocument.loadFromFile(filePath);
    }
}
