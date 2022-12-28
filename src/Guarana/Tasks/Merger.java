
package Guarana.Tasks;

import Guarana.Ports.Slot;
import org.json.JSONObject;



public class Merger extends Task{

    private Slot[] input;
    private Slot output;
    private int nInputs;
    
    
    
    
    public Merger(JSONObject json) {
        
        this.nInputs = json.getInt("nInputs");
        this.input = new Slot[nInputs];
    }
    
    
    
    @Override
    public void setInput(Slot s) {
        for(int i=0; i<this.nInputs; i++) {
            if(this.input[i] == null){
                this.input[i] = s;
                break;
            }
        }
    }

    
    
    @Override
    public void setOutput(Slot s) { this.output = s; }

    
    
    
    
    
    @Override
    public void run() {
        
        
        for(Slot s: this.input) {
            while(s.nMessages()>0)
                this.output.write(s.read());
        }    
    }
    
}
