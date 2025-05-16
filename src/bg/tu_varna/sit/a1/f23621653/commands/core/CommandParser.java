package bg.tu_varna.sit.a1.f23621653.commands.core;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses user input into command names and arguments.
 * <p>
 * This class is responsible for extracting the command name and its associated arguments
 * from a single line of user input. It handles quoted arguments correctly, ensuring
 * multi-word arguments are preserved as a single element.
 */
public class CommandParser {
    /**
     * Represents a parsed command, containing the command name and its arguments.
     */
    public static class ParsedCommand {
        private final String commandName;
        private final String[] commandArgs;

        public ParsedCommand(String commandName, String[] commandArgs) {
            this.commandName = commandName;
            this.commandArgs = commandArgs;
        }

        public String getCommandName() {
            return commandName;
        }

        public String[] getCommandArgs() {
            return commandArgs;
        }
    }

    /**
     * Parses a line of user input into a command name and its arguments.
     * <p>
     * This method splits the input into a command name and an array of arguments,
     * correctly handling quoted text to preserve multi-word arguments.
     *
     * @param input The raw user input.
     * @return A ParsedCommand object containing the command name and arguments.
     */
    public static ParsedCommand parse(String input) {
        List<String> args = new ArrayList<>();
        StringBuilder currentArg = new StringBuilder();
        boolean insideQuotes = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\"') {
                insideQuotes = !insideQuotes;
            } else if (c == ' ' && !insideQuotes) {
                if (!currentArg.isEmpty()) {
                    args.add(currentArg.toString());
                    currentArg.setLength(0);
                }
            } else {
                currentArg.append(c);
            }
        }
        if (!currentArg.isEmpty()) {
            args.add(currentArg.toString());
        }
        String commandName = args.removeFirst();
        String[] commandArgs = args.toArray(new String[0]);
        return new ParsedCommand(commandName, commandArgs);
    }
}
