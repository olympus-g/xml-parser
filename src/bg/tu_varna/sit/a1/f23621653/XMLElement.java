package bg.tu_varna.sit.a1.f23621653;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLElement {
    //will be used to represent a single xml element

    private String tagName;
    private String id;
    private Map<String, String> attributes = new HashMap<>();
    private List<XMLElement> children = new ArrayList<>();
    private String text = "";

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

    public String getAttribute(String key) {
        return attributes.getOrDefault(key, null);
    }

    public void setAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public void addChild(XMLElement child) {
        children.add(child);
    }

    public List<XMLElement> getChildren() {
        return children;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String toFormattedXML(int indentLevel) {
        StringBuilder sb = new StringBuilder();
        String indent = "    ".repeat(indentLevel);

        sb.append(indent).append("<").append(tagName);
        for (Map.Entry<String, String> entry : attributes.entrySet()) {
            sb.append(" ").append(entry.getKey()).append("=\"").append(entry.getValue()).append("\"");
        }
        if (children.isEmpty() && (text == null || text.isBlank())) {
            sb.append((" />\n"));
        } else {
            sb.append(">\n");
            if (text != null && !text.isBlank()) {
                sb.append(indent).append("    ").append(text.trim()).append("\n");
            }

            for (XMLElement child : children
            ) {
                sb.append(child.toFormattedXML(indentLevel + 1));
            }
            sb.append(indent).append("</").append(tagName).append(">\n");
        }
        return sb.toString();
    }
}
