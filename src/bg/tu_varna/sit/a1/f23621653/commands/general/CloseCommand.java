package bg.tu_varna.sit.a1.f23621653.commands.general;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

public class CloseCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        xmlDocument.setRoot(null);
        xmlDocument.setCurrentFilePath(null);
        System.out.println("XML document closed.");
    }
}
