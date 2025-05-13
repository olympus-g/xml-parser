package bg.tu_varna.sit.a1.f23621653;

import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;
import bg.tu_varna.sit.a1.f23621653.commands.XPathCommand;
import bg.tu_varna.sit.a1.f23621653.commands.general.*;
import bg.tu_varna.sit.a1.f23621653.commands.xml_specific.*;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private XMLDocument xmlDocument;
    private Map<String, Command> commands;

    public CommandHandler(XMLDocument xmlDocument) {
        this.xmlDocument = xmlDocument;
        this.commands = new HashMap<>();

        commands.put(CommandName.HELP.getCommandName(), new HelpCommand());
        commands.put(CommandName.OPEN.getCommandName(), new OpenCommand());
        commands.put(CommandName.SAVE.getCommandName(), new SaveCommand());
        commands.put(CommandName.SAVE_AS.getCommandName(), new SaveAsCommand());
        commands.put(CommandName.CLOSE.getCommandName(), new CloseCommand());
        commands.put(CommandName.PRINT.getCommandName(), new PrintCommand());
        commands.put(CommandName.SELECT.getCommandName(), new SelectCommand());
        commands.put(CommandName.SET.getCommandName(), new SetCommand());
        commands.put(CommandName.TEXT.getCommandName(), new TextCommand());
        commands.put(CommandName.DELETE.getCommandName(), new DeleteCommand());
        commands.put(CommandName.CHILDREN.getCommandName(), new ChildrenCommand());
        commands.put(CommandName.CHILD.getCommandName(), new ChildCommand());
        commands.put(CommandName.NEWCHILD.getCommandName(), new NewchildCommand());
        commands.put(CommandName.XPATH.getCommandName(),new XPathCommand());
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
