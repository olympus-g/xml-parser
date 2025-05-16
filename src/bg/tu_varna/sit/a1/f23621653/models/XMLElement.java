package bg.tu_varna.sit.a1.f23621653.models;

import java.util.*;

/**
 * Represents a single XML element within an XML document.
 * <p>
 * This class is designed to support hierarchical XML structures, allowing each element to
 * have its own children and parent, effectively forming a tree structure.
 */
public class XMLElement {
    private String tagName;
    private String id;
    private final Map<String, String> attributes = new LinkedHashMap<>();
    private final List<XMLElement> children = new ArrayList<>();
    private String text = "";
    private String namespace;
    private XMLElement parent;

    public XMLElement(String id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttribute(String key) {
        return attributes.getOrDefault(key, null);
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void addChild(XMLElement child) {
        child.setParent(this);
        children.add(child);
    }

    public List<XMLElement> getChildren() {
        return children;
    }

    public XMLElement getParent() {
        return parent;
    }

    public void setParent(XMLElement parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Returns a formatted XML representation of this element and its children.
     * <p>
     * This method generates an indented, human-readable XML string, including
     * all attributes, text content, and child elements.
     *
     * @param indentLevel the current level of indentation (used for formatting)
     * @return the formatted XML string
     */
    public String toFormattedXML(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        String indent = "    ".repeat(indentLevel);
        String qualifiedName = (namespace != null && !namespace.isBlank()) ? namespace + ":" + tagName : tagName;

        sb.append(indent).append("<").append(qualifiedName);
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            sb.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
        }

        if (children.isEmpty() && (text == null || text.isBlank())) {
            sb.append(" />\n");
            return sb.toString();
        }
        sb.append(">");

        if (text != null && !text.isBlank()) {
            sb.append(text.trim());
        }
        if (!children.isEmpty()) {
            sb.append("\n");
            for (XMLElement child : children) {
                sb.append(child.toFormattedXML(indentLevel + 1));
            }
            sb.append(indent);
        }
        sb.append("</").append(qualifiedName).append(">\n");
        return sb.toString();
    }
}
