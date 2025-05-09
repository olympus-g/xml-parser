package bg.tu_varna.sit.a1.f23621653;

import java.util.ArrayList;
import java.util.List;

public class CommandParser {
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

    public static ParsedCommand parse(String input) {
        List<String> args = new ArrayList<>();
        StringBuilder currentArg = new StringBuilder();
        boolean insideQuotes = false;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c == '\"') {
                insideQuotes = !insideQuotes;
            } else if (c == ' ' && !insideQuotes) {
                if (currentArg.length() > 0) {
                    args.add(currentArg.toString());
                    currentArg.setLength(0);
                }
            } else {
                currentArg.append(c);
            }
        }
        if (currentArg.length() > 0) {
            args.add(currentArg.toString());
        }
        String commandName = args.remove(0);
        String[] commandArgs = args.toArray(new String[0]);
        return new ParsedCommand(commandName, commandArgs);
    }
}
