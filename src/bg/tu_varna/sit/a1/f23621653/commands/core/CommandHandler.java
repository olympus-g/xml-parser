package bg.tu_varna.sit.a1.f23621653.commands.core;

import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;
import bg.tu_varna.sit.a1.f23621653.commands.enums.CommandName;
import bg.tu_varna.sit.a1.f23621653.commands.xml_specific.XPathCommand;
import bg.tu_varna.sit.a1.f23621653.commands.general.*;
import bg.tu_varna.sit.a1.f23621653.commands.xml_specific.*;
import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Handles user commands for the XML parser application.
 * <p>
 * This class is responsible for managing the available commands, parsing user input,
 * and executing the appropriate command based on the input. It also maintains the
 * association between command names and their corresponding implementations.
 */
public class CommandHandler {
    private final XMLDocument xmlDocument;
    private final Map<String, Command> commands;
    Scanner scanner = new Scanner(System.in);

    /**
     * Initializes the command handler with a reference to the XML document being managed.
     * It also registers all supported commands.
     *
     * @param xmlDocument The XML document that commands will operate on.
     */
    public CommandHandler(XMLDocument xmlDocument) {
        this.xmlDocument = xmlDocument;
        this.commands = new HashMap<>();

        commands.put(CommandName.HELP.getCommandName(), new HelpCommand());
        commands.put(CommandName.OPEN.getCommandName(), new OpenCommand());
        commands.put(CommandName.SAVE.getCommandName(), new SaveCommand());
        commands.put(CommandName.SAVE_AS.getCommandName(), new SaveAsCommand());
        commands.put(CommandName.CLOSE.getCommandName(), new CloseCommand());
        commands.put(CommandName.EXIT.getCommandName(), new ExitCommand());
        commands.put(CommandName.PRINT.getCommandName(), new PrintCommand());
        commands.put(CommandName.SELECT.getCommandName(), new SelectCommand());
        commands.put(CommandName.SET.getCommandName(), new SetCommand());
        commands.put(CommandName.TEXT.getCommandName(), new TextCommand());
        commands.put(CommandName.DELETE.getCommandName(), new DeleteCommand());
        commands.put(CommandName.CHILDREN.getCommandName(), new ChildrenCommand());
        commands.put(CommandName.CHILD.getCommandName(), new ChildCommand());
        commands.put(CommandName.NEWCHILD.getCommandName(), new NewchildCommand());
        commands.put(CommandName.XPATH.getCommandName(), new XPathCommand());
    }

    /**
     * Executes a command based on the provided command name and arguments.
     * <p>
     * If the command is not recognized, an error message is displayed. If the command
     * is an exit command, the input scanner is closed, and the program terminates.
     *
     * @param commandName The name of the command to execute.
     * @param args        The arguments for the command.
     */
    public void executeCommand(String commandName, String[] args) {
        Command command = commands.get(commandName);
        if (command != null) {
            command.execute(args, xmlDocument);
            if(command instanceof ExitCommand){
                scanner.close();
            }
        } else {
            System.out.println("Unknown command. Type 'help' for a list of commands");
        }
    }

    /**
     * Continuously reads user input, parses it into commands, and executes them.
     * <p>
     * This method handles the main command loop for the application.
     */
    public void run() {
        System.out.println("Enter a command: ");
        String input = scanner.nextLine().trim();
        CommandParser.ParsedCommand parsed = CommandParser.parse(input);
        executeCommand(parsed.getCommandName(), parsed.getCommandArgs());
    }
}
