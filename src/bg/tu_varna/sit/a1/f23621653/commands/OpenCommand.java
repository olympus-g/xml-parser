package bg.tu_varna.sit.a1.f23621653.commands;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;

import java.io.File;

public class OpenCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        String filePath = args[0];
        File file = new File(filePath);
        System.out.println("Looking for file at: " + file.getAbsolutePath());//maybe remove later

        if (!file.exists()) {
            System.out.println("File not found");
            return;
        }
        xmlDocument.loadFromFile(filePath);
    }
}
