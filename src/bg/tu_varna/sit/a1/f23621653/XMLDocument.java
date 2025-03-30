package bg.tu_varna.sit.a1.f23621653;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class XMLDocument {
    //will be used to represent the entire xml document
    private XMLElement root;
    private Map<String, XMLElement> elementMap = new HashMap<>();

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

    private void indexElements(XMLElement element) {
        if (element == null) return;
        if (element.getId() != null) {
            elementMap.put(element.getId(), element);
        }
        for (XMLElement child : element.getChildren()
        ) {
            indexElements(child);
        }
    }

    public XMLElement getElementById(String id) {
        return elementMap.get(id);
    }

    public void print() {
        if (root != null) {
            System.out.println();
        } else {
            System.out.println("No XML document loaded");
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
        } catch (IOException e) {
            System.out.println("Error saving XML document: " + e.getMessage());
        }
    }

    public void loadFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            XMLElement currentElement = null;
            XMLElement rootElement = null;
            Map<Integer, XMLElement> elementMap = new HashMap<>();
            int level = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.startsWith("<") && !line.startsWith("</")) {
                    String elementName = extractTagName(line);
                    XMLElement newElement = new XMLElement(elementName);
                    extractAttributes(newElement, line);

                    if (currentElement != null) {
                        currentElement.addChild(newElement);
                    } else rootElement = newElement;

                    elementMap.put(level, newElement);
                    currentElement = newElement;
                    level++;
                } else if (line.startsWith("</")) {
                    level--;
                    currentElement = elementMap.get(level - 1);
                } else {
                    if (currentElement != null) {
                        currentElement.setText(line);
                    }
                }
            }
            setRoot(rootElement);
            System.out.println("XML document loaded successfully");
        } catch (IOException e) {
            System.out.println("Error loading XML document: " + e.getMessage());
        }
    }

    private String extractTagName(String line) {
        int spaceIndex = line.indexOf(" ");
        int endIndex = line.indexOf(">");
        if (spaceIndex == -1 || spaceIndex > endIndex) {
            spaceIndex = endIndex;
        }
        return line.substring(1, spaceIndex);
    }

    private void extractAttributes(XMLElement element, String line) {
        int startIndex = line.indexOf(" ");
        int endIndex = line.indexOf(">");

        if (startIndex == -1 || startIndex > endIndex) return;

        String attrString = line.substring(startIndex + 1, endIndex);
        String[] pairs = attrString.split("\\s+");
        for (String pair : pairs
        ) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1].replace("\"", "");
                element.setAttribute(key, value);
            }
        }
    }
}
