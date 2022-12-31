
package Guarana.Tasks;

import Guarana.Ports.Slot;
import Guarana.util.Toolbox;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;


public class Replicator extends Task{

    
    
    private Slot input;
    private Slot[] output; 
    private int nOutputs;
    
    
    
    public Replicator(JSONObject json) {
        
        this.nOutputs = json.getInt("nOutputs");
        this.output = new Slot[this.nOutputs];
    }
    
    
    
    @Override
    public void setInput(Slot s) { this.input = s; }

    
    
    @Override
    public void setOutput(Slot s) {
        for(int i=0; i<this.nOutputs; i++) {
            if(this.output[i] == null){
                this.output[i] = s;
                break;
            }
        }
    }

    
    
    @Override
    public void run() {
        while(this.input.nMessages() > 0){
            Document doc = this.input.read();

            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

                for(int i=0; i<this.nOutputs; i++) {

                    Node originalRoot = doc.getDocumentElement();

                    Document copiedDocument = dBuilder.newDocument();
                    Node copiedRoot = copiedDocument.importNode(originalRoot, true);
                    copiedDocument.appendChild(copiedRoot);

                    this.output[i].write(copiedDocument);
                }

            } catch (Exception ex) {
                System.out.println("El Replicator ha fallado.");
            }
        }
        
        
    }


/*
    public static void main(String[] args) throws Exception {
        JSONObject json = Toolbox.jsonFromFile("config.json");
        
        Replicator replicator = new Replicator(json.getJSONObject("replicator1"));
        
        Document doc = Toolbox.createDocument("orderuniq.xml");
        
        Slot s = new Slot();
        s.write(doc);
        replicator.setInput(s);

        Slot o1 = new Slot();
        Slot o2 = new Slot();
        replicator.setOutput(o1);
        replicator.setOutput(o2);
        
        replicator.run();
    }
*/




}
