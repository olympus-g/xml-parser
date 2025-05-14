package bg.tu_varna.sit.a1.f23621653.commands.xml_specific;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.XMLElement;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

import java.util.*;

public class XPathCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if (args.length != 2) {
            System.out.println("Usage: xpath <id> <XPath>");
            return;
        }
        String id = args[0];
        String expression = String.join(" ", Arrays.copyOfRange(args, 1, args.length));

        XMLElement root = xmlDocument.getElementById(id);
        if (root == null) {
            System.out.println("Element with id \"" + id + "\" not found.");
            return;
        }

        List<XMLElement> results = evaluateXPath(root, expression.trim());

        boolean isAttributeQuery = expression.contains("(@");
        if (results.isEmpty() && !isAttributeQuery) {
            System.out.println("No matching elements found.");
        } else if (!isAttributeQuery) {
            System.out.println("XPath results:");
            for (XMLElement element : results) {
                StringBuilder output = new StringBuilder();
                output.append("- <");
                if (element.getNamespace() != null && !element.getNamespace().isBlank()) {
                    output.append(element.getNamespace()).append(":");
                }

                output.append(element.getTagName());
                output.append(" id=\"").append(element.getId()).append("\"");

                for (Map.Entry<String, String> attr : element.getAttributes().entrySet()) {
                    if (!attr.getKey().equals("id")) {
                        output.append(" ").append(attr.getKey())
                                .append("=\"").append(attr.getValue())
                                .append("\"");
                    }
                }
                output.append(">");
                if (element.getText() != null && !element.getText().isEmpty()) {
                    output.append(element.getText());
                }
                output.append("</").append(element.getTagName()).append(">");
                System.out.println(output);
            }
        }
    }

    private List<XMLElement> evaluateXPath(XMLElement root, String expression) {
        String[] parts = expression.split("/");
        List<XMLElement> currentElements = List.of(root);

        for (String part : parts) {
            if (part.isEmpty()) continue;

            String axis = extractAxis(part);
            String tagName = extractTagName(part);
            String namespace = extractNamespace(part);
            Integer index = extractIndex(part);
            String attributeAccess = extractAttributeAccess(part);
            String[] filter = extractFilter(part);

            switch (axis) {
                case "self":
                    currentElements = selfAxis(currentElements, tagName, namespace);
                    break;
                case "child":
                    currentElements = findMatchingElements(currentElements, tagName, namespace, filter);
                    break;
                case "parent":
                    currentElements = parentAxis(currentElements, tagName, namespace);
                    break;
                case "ancestor":
                    currentElements = ancestorAxis(currentElements, tagName, namespace);
                    break;
                case "descendant":
                    currentElements = getAllDescendants(currentElements, tagName, namespace, filter);
                    break;
                default:
                    System.out.println("Unknown axis: " + axis);
                    return new ArrayList<>();
            }

            if (index != null) {
                currentElements = filterByIndex(currentElements, index);
            }
            if (attributeAccess != null) {
                printAttributes(currentElements, attributeAccess);
                return currentElements;
            }
            if (currentElements.isEmpty()) break;
        }
        return currentElements;
    }

    private String extractAxis(String part) {
        if (part.contains("::")) {
            return part.split("::")[0];
        }
        return "child";
    }

    private List<XMLElement> selfAxis(List<XMLElement> elements, String tagName, String namespace) {
        List<XMLElement> result = new ArrayList<>();
        for (XMLElement element : elements) {
            boolean namespaceMatches = (namespace == null) || namespace.equals(element.getNamespace());
            boolean tagNameMatches = tagName.equals("*") || tagName.equals(element.getTagName());

            if (namespaceMatches && tagNameMatches) {
                result.add(element);
            }
        }
        return result;
    }

    private String extractNamespace(String part) {
        if (part.contains("::")) {
            part = part.split("::")[1];
        }
        if (part.contains(":")) {
            return part.split(":")[0];
        }
        return null;
    }

    private String extractTagName(String part) {
        if (part.contains("::")) {
            part = part.split("::")[1];
        }
        if (part.contains(":")) {
            part = part.split(":")[1];
        }
        if (part.contains("[") || part.contains("(")) {
            return part.split("[\\[(]")[0];
        }
        return part;
    }

    private Integer extractIndex(String part) {
        if (part.contains("[") && part.endsWith("]")) {
            try {
                return Integer.parseInt(part.substring(part.indexOf('[') + 1, part.length() - 1));
            } catch (NumberFormatException e) {
                System.out.println("Invalid index in XPath: " + part);
            }
        }
        return null;
    }

    private String extractAttributeAccess(String part) {
        if (part.contains("(@") && part.endsWith(")")) {
            return part.substring(part.indexOf("(@") + 2, part.length() - 1);
        }
        return null;
    }

    private String[] extractFilter(String part) {
        if (part.contains("(") && part.endsWith(")")) {
            int start = part.indexOf('(');
            String filter = part.substring(start + 1, part.length() - 1);
            String[] filterParts = filter.split("=");
            if (filterParts.length == 2) {
                return new String[]{filterParts[0].trim(), filterParts[1].replace("\"", "").trim()};
            }
        }
        return null;
    }

    private List<XMLElement> findMatchingElements(List<XMLElement> parents, String tagName, String namespace, String[] filter) {
        List<XMLElement> results = new ArrayList<>();

        for (XMLElement parent : parents) {
            for (XMLElement child : parent.getChildren()) {
                boolean namespaceMatches = (namespace == null) || namespace.equals(child.getNamespace());
                boolean tagNameMatches = tagName.equals("*") || tagName.equals(child.getTagName());

                if (!namespaceMatches || !tagNameMatches) continue;

                if (filter != null) {
                    String filterKey = filter[0];
                    String filterValue = filter[1];

                    if (!matchesFilter(child, filterKey, filterValue)) continue;
                }
                results.add(child);
                results.addAll(getDescendants(child, tagName, namespace, filter));
            }
        }
        return results;
    }

    private List<XMLElement> getAllDescendants(List<XMLElement> elements, String tagName, String namespace, String[] filter) {
        List<XMLElement> results = new ArrayList<>();
        for (XMLElement element : elements) {
            results.addAll(getDescendants(element, tagName, namespace, filter));
        }
        return results;
    }

    private List<XMLElement> getDescendants(XMLElement element, String tagName, String namespace, String[] filter) {
        List<XMLElement> results = new ArrayList<>();

        for (XMLElement child : element.getChildren()) {
            boolean namespaceMatches = (namespace == null) || namespace.equals(child.getNamespace());
            boolean tagNameMatches = tagName.equals("*") || tagName.equals(child.getTagName());

            if (namespaceMatches && tagNameMatches) {
                if (filter == null || matchesFilter(child, filter[0], filter[1])) {
                    results.add(child);
                }
            }
            results.addAll(getDescendants(child, tagName, namespace, filter));
        }
        return results;
    }

    private List<XMLElement> parentAxis(List<XMLElement> elements, String tagName, String namespace) {
        Set<XMLElement> results = new LinkedHashSet<>();
        for (XMLElement element : elements) {
            XMLElement parent = element.getParent();
            if (parent != null) {
                boolean namespaceMatches = (namespace == null) || namespace.equals(parent.getNamespace());
                boolean tagNameMatches = tagName.equals("*") || tagName.equals(parent.getTagName());

                if (namespaceMatches && tagNameMatches) {
                    results.add(parent);
                }
            }
        }
        return new ArrayList<>(results);
    }

    private List<XMLElement> ancestorAxis(List<XMLElement> elements, String tagName, String namespace) {
        Set<XMLElement> results = new LinkedHashSet<>();
        for (XMLElement element : elements) {
            XMLElement ancestor = element.getParent();
            while (ancestor != null) {
                boolean namespaceMatches = (namespace == null) || namespace.equals(ancestor.getNamespace());
                boolean tagNameMatches = tagName.equals("*") || tagName.equals(ancestor.getTagName());

                if (namespaceMatches && tagNameMatches) {
                    results.add(ancestor);
                }
                ancestor = ancestor.getParent();
            }
        }
        return new ArrayList<>(results);
    }

    private boolean matchesFilter(XMLElement element, String filterKey, String filterValue) {
        String[] parts = filterKey.split(":", 2);
        String namespace = parts.length == 2 ? parts[0] : null;
        String tagName = parts.length == 2 ? parts[1] : filterKey;

        for (XMLElement child : element.getChildren()) {
            boolean namespaceMatches = (namespace == null) || namespace.equals(child.getNamespace());
            boolean tagNameMatches = tagName.equals("*") || tagName.equals(child.getTagName());
            boolean textMatches = child.getText() != null && child.getText().equals(filterValue);

            if (namespaceMatches && tagNameMatches && textMatches) {
                return true;
            }
        }
        String attributeValue = element.getAttribute(filterKey);
        if (attributeValue.equals(filterValue)) {
            return true;
        }
        return false;
    }

    private List<XMLElement> filterByIndex(List<XMLElement> elements, int index) {
        if (index >= 0 && index < elements.size()) {
            return List.of(elements.get(index));
        }
        return new ArrayList<>();
    }

    private void printAttributes(List<XMLElement> elements, String attributeName) {
        boolean found = false;
        for (XMLElement el : elements) {
            String val = el.getAttribute(attributeName);
            if (val != null) {
                System.out.println("@" + attributeName + " = " + val);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No matching elements with @" + attributeName + " found.");
        }
    }
}