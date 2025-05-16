package bg.tu_varna.sit.a1.f23621653.commands.xml_specific;

import bg.tu_varna.sit.a1.f23621653.models.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.models.XMLElement;
import bg.tu_varna.sit.a1.f23621653.commands.contracts.Command;

import java.util.*;

/**
 * Command that evaluates a simplified XPath expression starting from a specified element.
 * <p>
 * Supports axes like self, child, parent, ancestor, descendant, as well as
 * filtering by tag name, namespace prefix, index, and attribute-based predicates.
 */
public class XPathCommand implements Command {
    /**
     * Executes the XPath command.
     *
     * @param args       Command arguments; expects an element id and an XPath expression
     *                   (e.g., {"rootId", "descendant::book[@category=\"fiction\"]"}).
     * @param xmlDocument The XML document to query.
     */
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

        if (!isAttributeQuery) {
            printElements(results);
        }
    }

    /**
     * Evaluates a simplified XPath expression starting from a root element.
     *
     * @param root       The starting element for the XPath evaluation.
     * @param expression The XPath expression to evaluate.
     * @return A list of matching elements based on the XPath expression.
     */
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
                    currentElements = childAxis(currentElements, tagName, namespace, filter);
                    break;
                case "parent":
                    currentElements = parentAxis(currentElements, tagName, namespace);
                    break;
                case "ancestor":
                    currentElements = ancestorAxis(currentElements, tagName, namespace);
                    break;
                case "descendant":
                    currentElements = descendantAxis(currentElements, tagName, namespace, filter);
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

    /**
     * Checks if an element matches the specified namespace and tag name.
     *
     * @param element   The element to check.
     * @param namespace The expected namespace, or null to ignore namespace.
     * @param tagName   The expected tag name, or "*" to match any tag.
     * @return True if the element matches the namespace and tag name, otherwise false.
     */
    private boolean matchesNamespaceAndTag(XMLElement element, String namespace, String tagName) {
        boolean namespaceMatches = (namespace == null) || namespace.equals(element.getNamespace());
        boolean tagNameMatches = tagName.equals("*") || tagName.equals(element.getTagName());
        return namespaceMatches && tagNameMatches;
    }

    /**
     * Extracts the axis from an XPath step.
     *
     * @param part The XPath step (e.g., "child::book" or "ancestor::chapter").
     * @return The axis part of the step, defaulting to "child" if not specified.
     */
    private String extractAxis(String part) {
        if (part.contains("::")) {
            return part.split("::")[0];
        }
        return "child";
    }

    /**
     * Extracts the namespace prefix from an XPath step, if present.
     *
     * @param part The XPath step (e.g., "ns:book" or "book").
     * @return The namespace prefix, or null if none is present.
     */
    private String extractNamespace(String part) {
        if (part.contains("::")) {
            part = part.split("::")[1];
        }
        if (part.contains(":")) {
            return part.split(":")[0];
        }
        return null;
    }

    /**
     * Extracts the tag name from an XPath step.
     *
     * @param part The XPath step (e.g., "book" or "ns:book").
     * @return The tag name without the namespace or filters.
     */
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

    /**
     * Extracts the index from an XPath step if present.
     *
     * @param part The XPath step (e.g., "book[2]").
     * @return The index if present, or null if not.
     */
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

    /**
     * Extracts attribute access from an XPath step, if present.
     *
     * @param part The XPath step (e.g., "book(@id)").
     * @return The attribute name, or null if not present.
     */
    private String extractAttributeAccess(String part) {
        if (part.contains("(@") && part.endsWith(")")) {
            return part.substring(part.indexOf("(@") + 2, part.length() - 1);
        }
        return null;
    }

    /**
     * Extracts a filter condition from an XPath step if present.
     *
     * @param part The XPath step (e.g., "book(author=\"Smith\")").
     * @return A two-element array containing the filter key and value, or null if no filter is present.
     */
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

    /**
     * Returns the same elements if they match the specified tag and namespace.
     *
     * @param elements  The current elements.
     * @param tagName   The expected tag name, or "*" to match any tag.
     * @param namespace The expected namespace, or null to ignore namespace.
     * @return A filtered list of matching elements.
     */
    private List<XMLElement> selfAxis(List<XMLElement> elements, String tagName, String namespace) {
        return filterElements(elements, tagName, namespace, null);
    }

    /**
     * Finds all descendant elements matching the specified tag and namespace.
     *
     * @param elements  The starting elements.
     * @param tagName   The expected tag name, or "*" to match any tag.
     * @param namespace The expected namespace, or null to ignore namespace.
     * @param filter    An optional filter to apply.
     * @return A list of matching descendant elements.
     */
    private List<XMLElement> descendantAxis(List<XMLElement> elements, String tagName, String namespace, String[] filter) {
        List<XMLElement> results = new ArrayList<>();
        for (XMLElement element : elements) {
            getDescendants(element, tagName, namespace, filter, results);
        }
        return results;
    }

    /**
     * Recursively adds all matching descendants of an element to a result list.
     *
     * @param element   The root element to search.
     * @param tagName   The expected tag name, or "*" to match any tag.
     * @param namespace The expected namespace, or null to ignore namespace.
     * @param filter    An optional filter to apply.
     * @param results   The list to which matching descendants will be added.
     */
    private void getDescendants(XMLElement element, String tagName, String namespace, String[] filter, List<XMLElement>results) {
        for (XMLElement child : element.getChildren()) {
            if (matchesNamespaceAndTag(child, namespace, tagName) && (filter == null || matchesFilter(child, filter[0], filter[1]))) {
                results.add(child);
            }
            getDescendants(child, tagName, namespace, filter, results);
        }
    }

    /**
     * Finds the parents of the given elements that match the specified tag and namespace.
     *
     * @param elements  The elements for which to find parents.
     * @param tagName   The expected tag name, or "*" to match any tag.
     * @param namespace The expected namespace, or null to ignore namespace.
     * @return A list of matching parent elements.
     */
    private List<XMLElement> parentAxis(List<XMLElement> elements, String tagName, String namespace) {
        Set<XMLElement> results = new LinkedHashSet<>();
        for (XMLElement element : elements) {
            XMLElement parent = element.getParent();
            if (parent != null && matchesNamespaceAndTag(parent, namespace, tagName)) {
                results.add(parent);
            }
        }
        return new ArrayList<>(results);
    }

    /**
     * Finds all ancestors of the given elements that match the specified tag and namespace.
     *
     * @param elements  The elements for which to find ancestors.
     * @param tagName   The expected tag name, or "*" to match any tag.
     * @param namespace The expected namespace, or null to ignore namespace.
     * @return A list of matching ancestor elements.
     */
    private List<XMLElement> ancestorAxis(List<XMLElement> elements, String tagName, String namespace) {
        Set<XMLElement> results = new LinkedHashSet<>();
        for (XMLElement element : elements) {
            XMLElement ancestor = element.getParent();
            while (ancestor != null) {
                if (matchesNamespaceAndTag(ancestor, namespace, tagName)) {
                    results.add(ancestor);
                }
                ancestor = ancestor.getParent();
            }
        }
        return new ArrayList<>(results);
    }

    /**
     * Finds direct children of the current elements that match the specified tag, namespace, and optional filter.
     *
     * @param parents   The parent elements.
     * @param tagName   The expected tag name, or "*" to match any tag.
     * @param namespace The expected namespace, or null to ignore namespace.
     * @param filter    An optional filter to apply.
     * @return A list of matching child elements.
     */
    private List<XMLElement> childAxis(List<XMLElement> parents, String tagName, String namespace, String[] filter) {
        List<XMLElement> results = new ArrayList<>();
        for (XMLElement parent : parents) {
            results.addAll(filterElements(parent.getChildren(), tagName, namespace, filter));
        }
        return results;
    }

    /**
     * Filters a list of elements based on tag name, namespace, and optional filter.
     *
     * @param elements  The elements to filter.
     * @param tagName   The expected tag name, or "*" to match any tag.
     * @param namespace The expected namespace, or null to ignore namespace.
     * @param filter    An optional filter to apply.
     * @return A list of elements that match the given criteria.
     */
    private List<XMLElement> filterElements(List<XMLElement> elements, String tagName, String namespace, String[] filter) {
        List<XMLElement> results = new ArrayList<>();
        for (XMLElement element : elements) {
            if (matchesNamespaceAndTag(element, namespace, tagName) &&
                    (filter == null || matchesFilter(element, filter[0], filter[1]))) {
                results.add(element);
            }
        }
        return results;
    }

    /**
     * Checks if an element matches a filter condition based on an attribute or text content.
     *
     * @param element    The element to check.
     * @param filterKey  The filter key, which can be an attribute name or a tag name.
     * @param filterValue The expected value for the attribute or text.
     * @return True if the element matches the filter, otherwise false.
     */
    private boolean matchesFilter(XMLElement element, String filterKey, String filterValue) {
        String[] parts = filterKey.split(":", 2);
        String namespace = parts.length == 2 ? parts[0] : null;
        String tagName = parts.length == 2 ? parts[1] : filterKey;

        String attributeValue = element.getAttribute(filterKey);
        if (attributeValue != null && attributeValue.equals(filterValue)) {
            return true;
        }
        for (XMLElement child : element.getChildren()) {
            if (matchesNamespaceAndTag(child, namespace, tagName) && filterValue.equals(child.getText())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a single element from a list at a specified index, if within bounds.
     *
     * @param elements The list of elements to filter.
     * @param index    The zero-based index to select.
     * @return A list containing only the selected element, or an empty list if the index is out of bounds.
     */
    private List<XMLElement> filterByIndex(List<XMLElement> elements, int index) {
        if (index >= 0 && index < elements.size()) {
            return List.of(elements.get(index));
        }
        return new ArrayList<>();
    }

    private void printElements(List<XMLElement> elements) {
        if (elements.isEmpty()) {
            System.out.println("No matching elements found.");
            return;
        }
        System.out.println("XPath results:");
        for (XMLElement element : elements) {
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