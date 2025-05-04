package bg.tu_varna.sit.a1.f23621653;

import bg.tu_varna.sit.a1.f23621653.commands.*;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private XMLDocument xmlDocument;
    private Map<String, Command> commands;

    public CommandHandler(XMLDocument xmlDocument) {
        this.xmlDocument = xmlDocument;
        this.commands = new HashMap<>();

        commands.put("help", new HelpCommand());
        commands.put("open", new OpenCommand());
        commands.put("save", new SaveCommand());
        commands.put("saveas", new SaveAsCommand());
        commands.put("close", new CloseCommand());
        commands.put("print", new PrintCommand());
        commands.put("select", new SelectCommand());
        commands.put("set", new SetCommand());
        commands.put("text", new TextCommand());
        commands.put("delete", new DeleteCommand());
        commands.put("children", new ChildrenCommand());
        commands.put("child", new ChildCommand());
        commands.put("newchild", new NewchildCommand());
        commands.put("xpath",new XPathCommand());
    }

    public void executeCommand(String commandName, String[] args) {
        Command command = commands.get(commandName);
        if (command != null) {
            command.execute(args, xmlDocument);
        } else {
            System.out.println("Unknown command. Type 'help' for a list of commands");
        }
    }
}
