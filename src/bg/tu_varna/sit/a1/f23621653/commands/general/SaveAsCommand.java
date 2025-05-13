package bg.tu_varna.sit.a1.f23621653.commands.general;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

public class SaveAsCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length < 1) {
            System.out.println("No filePath specified.");
            return;
        }
        String filePath = args[0];
        xmlDocument.saveToFile(filePath);
        System.out.println("Successfully saved file");
    }
}
