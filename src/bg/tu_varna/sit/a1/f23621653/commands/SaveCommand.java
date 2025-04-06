package bg.tu_varna.sit.a1.f23621653.commands;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;

public class SaveCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        String filePath= xmlDocument.getCurrentFilePath();
        if(filePath==null||filePath.isBlank()){
            System.out.println("No file loaded.");
            return;
        }
        xmlDocument.saveToFile(filePath);
    }
}
