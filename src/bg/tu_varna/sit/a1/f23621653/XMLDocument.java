package bg.tu_varna.sit.a1.f23621653;

import java.io.*;
import java.util.*;

public class XMLDocument {
    //will be used to represent the entire xml document
    private XMLElement root;
    private Map<String, XMLElement> elementMap = new HashMap<>();
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
                String tagName = extractTagName(line);
                Map<String, String> attributes = extractAttributes(line);
                String rawId = attributes.get("id");
                String finalId;

                if (rawId != null) {
                    if (!existingIds.contains(rawId)) {
                        finalId = rawId;
                    } else {
                        int count = duplicateCountMap.getOrDefault(rawId, 1);
                        finalId = rawId + "_" + count;
                        duplicateCountMap.put(rawId, count + 1);
                    }
                } else {
                    finalId = "auto_id_" + autoIdCounter++;
                }

                existingIds.add(finalId);

                XMLElement newElement = new XMLElement(finalId);
                newElement.setTagName(tagName);
                newElement.getAttributes().putAll(attributes);
                newElement.setId(finalId);
                newElement.setAttribute("id", finalId);

                int closeTagIndex = line.indexOf("</" + tagName + ">");
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

    private String extractTagName(String line) {
        int spaceIndex = line.indexOf(" ");
        int endIndex = line.indexOf(">");
        if (spaceIndex == -1 || spaceIndex > endIndex) {
            spaceIndex = endIndex;
        }
        return line.substring(1, spaceIndex);
    }

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
