package bg.tu_varna.sit.a1.f23621653.commands.general;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

/**
 * Command that terminates the application.
 * This command immediately stops the program execution, exiting the JVM.
 */
public class ExitCommand implements Command {
    /**
     * Executes the exit command, terminating the entire application.
     * <p>
     * Note: This command does not save any unsaved changes before exiting.
     * </p>
     *
     * @param args        The command arguments (not used in this command).
     * @param xmlDocument The XMLDocument instance (not used in this command).
     */
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        System.out.println("Exiting the program...");
        System.exit(0);
    }
}
