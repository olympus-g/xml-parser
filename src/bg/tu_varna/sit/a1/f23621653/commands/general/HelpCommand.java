package bg.tu_varna.sit.a1.f23621653.commands.general;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

public class HelpCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        System.out.println("Available commands:");
        System.out.println("open <file>            - Opens <file>.");
        System.out.println("close                  - Closes the currently opened file.");
        System.out.println("save                   - Saves the currently open file.");
        System.out.println("saveas <file>          - Saves  the currently open file in <file>.");
        System.out.println("help                   - Prints this information.");
        System.out.println("print                  - Prints the XML structure.");
        System.out.println("select <id> <key>      - Gets an attribute.");
        System.out.println("set <id> <key> <value> - Sets an attribute.");
        System.out.println("children <id>          - Lists child elements.");
        System.out.println("child <id> <n>         - Gets nth child of element.");
        System.out.println("text <id>              - Gets text of element.");
        System.out.println("delete <id> <key>      - Deletes an attribute.");
        System.out.println("newchild <id>          - Adds a new child element.");
        System.out.println("xpath <id> <XPath>     - Executes XPath-like query.");
        System.out.println("exit                   - Exits the program.");
    }
}
