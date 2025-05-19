package bg.tu_varna.sit.a1.f23621653.models;

import java.io.*;
import java.util.*;

/**
 * The {@code XMLDocument} class represents an entire XML document, providing methods
 * for managing the document structure, including loading from and saving to files,
 * indexing elements by their IDs, and accessing individual elements by ID.
 * <p>
 * This class acts as a wrapper around a hierarchical XML structure rooted at a single
 * {@link XMLElement} node.
 */
public class XMLDocument {
    private XMLElement root;
    private final Map<String, XMLElement> elementMap = new HashMap<>();
    private String currentFilePath;

    public XMLDocument() {
        this.root = null;
    }

    public void setRoot(XMLElement root) {
        this.root = root;
        elementMap.clear();
        indexElements(root);
    }

    public XMLElement getRoot() {
        return root;
    }

    public String getCurrentFilePath() {
        return currentFilePath;
    }

    public void setCurrentFilePath(String currentFilePath) {
        this.currentFilePath = currentFilePath;
    }

    /**
     * Recursively indexes all elements in the document, allowing fast lookup by ID.
     *
     * @param element The element to index, along with all its descendants.
     */
    private void indexElements(XMLElement element) {
        if (element == null) return;
        if (element.getId() != null) {
            elementMap.put(element.getId(), element);
        }
        for (XMLElement child : element.getChildren()) {
            indexElements(child);
        }
    }

    public XMLElement getElementById(String id) {
        return elementMap.get(id);
    }

    public void loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            List<String> lines = readLines(reader);
            XMLElement rootElement = parseXML(lines);
            setRoot(rootElement);
            setCurrentFilePath(filePath);
            System.out.println("XML document opened successfully");
        } catch (IOException e) {
            System.out.println("Error loading XML document: " + e.getMessage());
        }
    }

    public void saveToFile(String filePath) {
        if (root == null) {
            System.out.println("No XML document to save");
            return;
        }
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(root.toFormattedXML(0));
            System.out.println("XML document saved successfully at " + filePath);
            setCurrentFilePath(filePath);
        } catch (IOException e) {
            System.out.println("Error saving XML document: " + e.getMessage());
        }
    }

    /**
     * Reads all non-empty lines from a BufferedReader, trimming whitespace.
     *
     * @param reader The BufferedReader to read lines from.
     * @return A list of trimmed, non-empty lines.
     * @throws IOException If an error occurs while reading the lines.
     */
    private List<String> readLines(BufferedReader reader) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Parses a list of XML lines into a tree of XMLElements, creating a root element.
     *
     * @param lines The lines representing the XML document.
     * @return The root element of the parsed XML tree.
     */
    private XMLElement parseXML(List<String> lines) {
        Map<Integer, XMLElement> levelMap = new HashMap<>();
        Set<String> existingIds = new HashSet<>();
        Map<String, Integer> duplicateCountMap = new HashMap<>();
        int autoIdCounter = 1;

        int level = 0;
        XMLElement currentElement = null;
        XMLElement rootElement = null;

        for (String line : lines) {
            line = line.trim();

            if (line.startsWith("<") && line.endsWith(">") && !line.startsWith("</")) {
                int endIndex = line.indexOf(">");
                String rawTagName = extractTagName(line);
                String[] tagParts = rawTagName.split(":", 2);
                String namespace = null;
                String tagName;

                if (tagParts.length == 2) {
                    namespace = tagParts[0];
                    tagName = tagParts[1];
                } else {
                    tagName = rawTagName;
                }

                Map<String, String> attributes = extractAttributes(line);
                String rawId = attributes.get("id");
                String finalId;

                if (rawId != null) {
                    if (existingIds.contains(rawId)) {
                        int count = duplicateCountMap.getOrDefault(rawId, 1);
                        finalId = rawId + "_" + count;
                        duplicateCountMap.put(rawId, count + 1);
                    } else {
                        finalId = rawId;
                        duplicateCountMap.put(rawId, 1);
                    }
                    existingIds.add(rawId);
                } else {
                    finalId = "auto_id_" + autoIdCounter++;
                }
                attributes.put("id", finalId);

                XMLElement newElement = new XMLElement(finalId);
                newElement.setNamespace(namespace);
                newElement.setTagName(tagName);
                newElement.setId(finalId);
                newElement.setAttribute("id", finalId);
                newElement.getAttributes().putAll(attributes);

                int closeTagIndex = line.indexOf("</" + rawTagName + ">");
                if (closeTagIndex != -1) {
                    String textContent = line.substring(endIndex + 1, closeTagIndex).trim();
                    newElement.setText(textContent);
                    if (currentElement != null) {
                        currentElement.addChild(newElement);
                    } else {
                        rootElement = newElement;
                    }
                    continue;
                }

                if (currentElement != null) {
                    currentElement.addChild(newElement);
                } else {
                    rootElement = newElement;
                }

                levelMap.put(level, newElement);
                currentElement = newElement;
                level++;
            } else if (line.startsWith("</")) {
                level--;
                currentElement = level > 0 ? levelMap.get(level - 1) : null;
            } else {
                if (currentElement != null) {
                    currentElement.setText(line);
                }
            }
        }

        return rootElement;
    }

    /**
     * Extracts the tag name from an XML element line.
     *
     * @param line The line containing the XML element.
     * @return The tag name without attributes or angle brackets.
     */
    private String extractTagName(String line) {
        int spaceIndex = line.indexOf(" ");
        int endIndex = line.indexOf(">");
        if (spaceIndex == -1 || spaceIndex > endIndex) {
            spaceIndex = endIndex;
        }
        return line.substring(1, spaceIndex).trim();
    }

    /**
     * Extracts the attributes from an XML element line as a map of key-value pairs.
     *
     * @param line The line containing the XML element.
     * @return A map of attribute names and their corresponding values.
     */
    private Map<String, String> extractAttributes(String line) {
        Map<String, String> attributes = new HashMap<>();
        int startIndex = line.indexOf(" ");
        int endIndex = line.indexOf(">");

        if (startIndex == -1 || startIndex > endIndex) return attributes;

        String attrString = line.substring(startIndex + 1, endIndex);
        String[] pairs = attrString.split("\\s+");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1].replace("\"", "");
                attributes.put(key, value);
            }
        }
        return attributes;
    }
}
