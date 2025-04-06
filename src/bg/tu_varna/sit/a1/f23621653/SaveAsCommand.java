package bg.tu_varna.sit.a1.f23621653;

public class SaveAsCommand implements Command {
    @Override
    public void execute(String[] args, XMLDocument xmlDocument) {
        if(args.length<1){
            System.out.println("No filePath specified.");
            return;
        }
        String filePath=args[0];
        xmlDocument.saveToFile(filePath);
    }
}
