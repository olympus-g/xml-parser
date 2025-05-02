package bg.tu_varna.sit.a1.f23621653.commands;

import bg.tu_varna.sit.a1.f23621653.XMLDocument;
import bg.tu_varna.sit.a1.f23621653.XMLElement;
import org.w3c.dom.xpath.XPathResult;

import java.util.List;

public class ChildCommand implements Command{
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if(args.length!=2){
            System.out.println("Usage: child <id> <n>");
            return;
        }

        String id=args[0];
        int index;
        try {
            index=Integer.parseInt(args[1]);
        }catch (NumberFormatException e){
            System.out.println("The second argument must be an integer (child index).");
        return;
        }
        XMLElement parent=xmlDocument.getElementById(id);
        if (parent == null) {
            System.out.println("Element with id \"" + id + "\" not found.");
            return;
        }
        List<XMLElement> children=parent.getChildren();
        if (index<0||index>=children.size()){
            System.out.println("Invalid child index. Element has "+children.size()+" children.");
            return;
        }
        XMLElement child=children.get(index);
        System.out.println("Child at index "+index+" of element with id \""+id+"\":");
        System.out.println(child.toFormattedXML(0));
    }
}
