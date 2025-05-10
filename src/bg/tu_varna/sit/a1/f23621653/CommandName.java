package bg.tu_varna.sit.a1.f23621653;

public enum CommandName {
    OPEN("open"),
    CLOSE("close"),
    SAVE("save"),
    SAVE_AS("saveas"),
    HELP("help"),
    EXIT("exit"),
    PRINT("print"),
    SELECT("select"),
    SET("set"),
    CHILDREN("children"),
    CHILD("child"),
    TEXT("text"),
    DELETE("delete"),
    NEWCHILD("newchild"),
    XPATH("xpath");

    private String commandName;
    CommandName(String commandName) {
        this.commandName = commandName;
    }
    public String getCommandName() {
        return commandName;
    }
}
