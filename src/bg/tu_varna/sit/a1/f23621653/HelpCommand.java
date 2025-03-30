package bg.tu_varna.sit.a1.f23621653;

public class HelpCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        System.out.println("Available commands:");
        System.out.println("open <file>            - Opens an XML file.");
        System.out.println("close                  - Closes the currently opened file.");
        System.out.println("save                   - Saves the current file.");
        System.out.println("saveas <file>          - Saves as a different file.");
        System.out.println("print                  - Prints the XML structure");
        System.out.println("select <id> <key>      - Gets an attribute");
        System.out.println("set <id> <key> <value> - Sets an attribute");
        System.out.println("children <id>          - Lists child elements");
        System.out.println("child <id> <n>         - Gets nth child of element");
        System.out.println("text <id>              - Gets text of element");
        System.out.println("delete <id> <key>      - Deletes an attribute");
        System.out.println("newchild <id>          - Adds a new child element");
        System.out.println("xpath <id> <XPath>     - Executes XPath-like query");
        System.out.println("exit                   - Exits the program");
    }
}
