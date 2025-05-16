package bg.tu_varna.sit.a1.f23621653.commands.general;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

public class ExitCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}
