package bg.tu_varna.sit.a1.f23621653.commands;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.XMLElement;

import java.util.List;
import java.util.Map;

public class ChildrenCommand implements Command{
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if(args.length!=1){
            System.out.println("Usage: children <id>");
            return;
        }

        String id=args[0];
        XMLElement parent=xmlDocument.getElementById(id);

        if(parent==null){
            System.out.println("Element with id \""+id+"\" not found.");
            return;
        }
        List<XMLElement> children=parent.getChildren();
        if(children.isEmpty()){
            System.out.println("Element with id \""+id+"\" has no children.");
            return;
        }

        System.out.println("Attributes of children of element with id \""+id+"\":");

        for(XMLElement child:children) {
            StringBuffer tagBuilder=new StringBuffer();
            tagBuilder.append("<").append(child.getTagName());
            for (Map.Entry<String, String> attr : child.getAttributes().entrySet()) {
                tagBuilder.append(" ").append(attr.getKey()).append("=\"").append(attr.getValue()).append("\"");
            }
            tagBuilder.append(">");
            System.out.println("- "+tagBuilder);
        }

    }
}
