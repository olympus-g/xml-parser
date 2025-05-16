package bg.tu_varna.sit.a1.f23621653.commands.contracts;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;

/**
 * Represents a command that can be executed within the XML parser application.
 * <p>
 * Implementations of this interface define specific actions to be performed based on
 * user input, operating on an {@link XMLDocument} instance.
 */
public interface Command {
    /**
     * Executes the command with the given arguments, using the provided XML document.
     *
     * @param args        The command arguments.
     * @param xmlDocument The XML document to operate on.
     */
    void execute(String[] args, XMLDocument xmlDocument);
}
