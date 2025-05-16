package bg.tu_varna.sit.a1.f23621653.commands.contracts;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;

public interface Command {
    void execute(String[] args, XMLDocument xmlDocument);
}
