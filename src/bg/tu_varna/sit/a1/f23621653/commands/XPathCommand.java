package bg.tu_varna.sit.a1.f23621653.commands;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.XMLElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

        boolean isAttributeQuery=expression.contains("(@");
        if (results.isEmpty()&&!isAttributeQuery) {
            System.out.println("No matching elements found.");
        } else if(!isAttributeQuery){
            System.out.println("XPath results:");
            for (XMLElement element : results) {
                StringBuilder output=new StringBuilder();
                output.append("- <").append(element.getTagName());
                output.append(" id=\"").append(element.getId()).append("\"");

                for(Map.Entry<String ,String> attr:element.getAttributes().entrySet()){
                    if(!attr.getKey().equals("id")){
                        output.append(" ").append(attr.getKey())
                                .append("=\"").append(attr.getValue())
                                .append("\"");
                    }
                }
                output.append(">");
                if(element.getText()!=null&&!element.getText().isEmpty()){
                    output.append(element.getText());
                }
                output.append("</").append(element.getTagName()).append(">");
                System.out.println(output);
            }

        }
    }

    private List<XMLElement> evaluateXPath(XMLElement root, String expression) {
        String[] parts = expression.split("/");
        List<XMLElement> current = List.of(root);

        for (String part : parts) {
            if (part.isEmpty()) continue;

            String tagName = part;
            Integer index = null;
            String attributeAccess = null;
            String filterKey = null;
            String filterValue = null;

            if (part.contains("(@") && part.endsWith(")")) {
                int start = part.indexOf("(@");
                tagName = part.substring(0, start);
                attributeAccess = part.substring(start + 2, part.length() - 1);
            } else if (part.contains("(") && part.endsWith(")")) {
                int start = part.indexOf('(');
                tagName = part.substring(0, start);
                String filter = part.substring(start + 1, part.length() - 1);
                String[] filterParts = filter.split("=");
                if (filterParts.length == 2) {
                    filterKey = filterParts[0];
                    filterValue = filterParts[1].replace("\"", "");
                }
            } else if (part.contains("[") && part.endsWith("]")) {
                int start = part.indexOf('[');
                tagName = part.substring(0, start);
                try {
                    index = Integer.parseInt(part.substring(start + 1, part.length() - 1));
                } catch (NumberFormatException e) {
                    System.out.println("Invalid index in XPath: " + part);
                    return List.of();
                }
            }

            List<XMLElement> next = new ArrayList<>();
            for (XMLElement parent : current) {
                for (XMLElement child : parent.getChildren()) {
                    if (!child.getTagName().equals(tagName)) continue;

                    boolean matches = true;

                    if (filterKey != null && filterValue != null) {
                        matches = false;
                        for (XMLElement grandChild : child.getChildren()) {
                            if (grandChild.getTagName().equals(filterKey) &&
                                    filterValue.equals(grandChild.getText())) {
                                matches = true;
                                break;
                            }
                        }
                    }

                    if (matches) {
                        next.add(child);
                    }
                }
            }

            if (index != null) {
                if (index >= 0 && index < next.size()) {
                    current = List.of(next.get(index));
                } else {
                    current = new ArrayList<>();
                }
            } else {
                current = next;
            }

            if (attributeAccess != null) {
                boolean found = false;
                for (XMLElement el : current) {
                    String val = el.getAttribute(attributeAccess);
                    if (val != null) {
                        System.out.println("@" + attributeAccess + " = " + val);
                        found = true;
                    }
                }

                if (!found) {
                    System.out.println("No matching elements with @" + attributeAccess + " found.");
                }

                return current;
            }

        }

        return current;
    }


}