package bg.tu_varna.sit.a1.f23621653.commands;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;

public class CloseCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        xmlDocument.setRoot(null);
        xmlDocument.setCurrentFilePath(null);
        System.out.println("XML document closed.");
    }
}
