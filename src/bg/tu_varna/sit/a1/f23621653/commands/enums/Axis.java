package bg.tu_varna.sit.a1.f23621653.commands.enums;

public enum Axis {
    SELF,
    CHILD,
    PARENT,
    ANCESTOR,
    DESCENDANT;

    public static Axis fromString(String s) {
        switch (s.toLowerCase()) {
            case "self": return SELF;
            case "child": return CHILD;
            case "parent": return PARENT;
            case "ancestor": return ANCESTOR;
            case "descendant": return DESCENDANT;
            default: throw new IllegalArgumentException("Unknown axis: " + s);
        }
    }
}
