
package Guarana.Tasks;

import Guarana.Ports.Slot;
import Guarana.util.Toolbox;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class Enricher extends Task{

    private Slot output;
    private Slot[] input;
    private String what;
    private String where;
    
    
    
    public Enricher(JSONObject json) {

        this.input = new Slot[2];
        this.what = json.getString("what");
        this.where = json.getString("where");
    }
    
    
    
    @Override
    public void setInput(Slot s) {
    
        for(int i=0; i<2; i++) {
            if(input[i] == null) {
                input[i] = s;
                break;
            }
        }
    }
    
    @Override
    public void setOutput(Slot s) { this.output = s; }

    
    
    public void enrich() {
        
//Leo los documentos del input
        Document docContext = this.input[0].read();
        Document docMain = this.input[1].read();

        //System.out.println(Toolbox.toString(docMain));
        //System.out.println(Toolbox.toString(docContext));

        Node nodeContext = docContext.getElementsByTagName(this.what).item(0);
        Node copyContext = nodeContext.cloneNode(true);
        docMain.adoptNode(copyContext);
        
     
        
        docMain.getElementsByTagName(this.where).item(0).appendChild(copyContext);
        

        this.output.write(docMain);
    }

    
    @Override
    public void run() {
    
        while(this.input[0].nMessages() == this.input[1].nMessages() &&
                this.input[0].nMessages() >0) {
            
            this.enrich();
        }
    }

}
