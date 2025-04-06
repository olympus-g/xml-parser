package bg.tu_varna.sit.a1.f23621653;

public class PrintCommand implements Command{
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if(xmlDocument.getRoot()==null){
            System.out.printf("No XML document loaded.");
            return;
        }
        System.out.println("Formatted XML output:\n");
        System.out.println(xmlDocument.getRoot().toFormattedXML(0));
    }
}
