package bg.tu_varna.sit.a1.f23621653.commands.enums;

/**
 * Enumeration representing the different XPath axes supported by the XPathCommand.
 *
 * <p>Axes specify the relationship between nodes in the XML tree during XPath evaluation.</p>
 */
public enum Axis {
    SELF("self"),
    CHILD("child"),
    PARENT("parent"),
    ANCESTOR("ancestor"),
    DESCENDANT("descendant");

    private final String axisName;
    Axis(String axisName) {
        this.axisName = axisName;
    }

    public String getAxisName() {
        return axisName;
    }

    /**
     * Converts a string representation of an axis to the corresponding {@link Axis} enum constant.
     *
     * @param s The string representation of the axis (e.g., "self", "child").
     *          The comparison is case-insensitive.
     * @return The matching {@code Axis} enum constant, or {@code null} if the input
     *         string is {@code null} or does not match any axis name.
     */
    public static Axis fromString(String s) {
        if (s == null) return null;
        for (Axis axis : Axis.values()) {
            if (axis.getAxisName().equalsIgnoreCase(s)) {
                return axis;
            }
        }
        return null;
    }
}
