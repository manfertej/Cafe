package Guarana.Ports;

import Guarana.util.Toolbox;
import org.w3c.dom.Document;



/**
 * Simplemente muestra los XML del slot.
 * @author alfonso
 */
public class Output {
    
    private Slot input;
    
    
    
    public void setInput(Slot s) { this.input = s; }
    
    
    public void run() {
        
        Document doc;
        while(this.input.nMessages()>0) {
            
            doc = this.input.read();
            System.out.println(Toolbox.toString(doc));
        }
    }
}
