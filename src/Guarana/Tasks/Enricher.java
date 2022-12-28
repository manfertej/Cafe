
package Guarana.Tasks;

import Guarana.Ports.Slot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;


public class Enricher extends Task{

    private Slot output;
    private Slot[] input;
    
    
    public Enricher() {
        this.input = new Slot[2];
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

    
    
    @Override
    public void run() {
    
        //Leo los documentos del input
        Document docContext = this.input[0].read();
        Document docMain = this.input[1].read();
        
        
        Element elementContext = docContext.getDocumentElement();
        Element elementMain = docMain.getDocumentElement();
        
        Node node;
        while((node = elementContext.getFirstChild()) != null) {
            docMain.adoptNode(node);
            elementMain.appendChild(node);
        }
        
        this.output.write(docMain);
    }


    public static void main(String[] args) {
        
    }

    
}
