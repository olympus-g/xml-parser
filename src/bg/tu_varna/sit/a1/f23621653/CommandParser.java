package bg.tu_varna.sit.a1.f23621653;

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
        String[] parts = input.split("\\s+");
        String commandName = parts[0];
        String[] commandArgs = new String[parts.length - 1];
        System.arraycopy(parts, 1, commandArgs, 0, commandArgs.length);
        return new ParsedCommand(commandName, commandArgs);
    }
}
