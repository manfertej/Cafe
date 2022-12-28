
package Guarana.Tasks;

import Guarana.Ports.Slot;
import java.util.ArrayList;
import org.json.JSONObject;
import org.w3c.dom.Document;


public class Distributor extends Task{

    
    private Slot input;
    private Slot[] output; 
    private int nOutputs;
    
    private ArrayList<String> routers;
    private String discriminant;
    
    
    
    
    public Distributor(JSONObject json) {
        
        this.nOutputs = json.getInt("nInputs");
        
        this.output = new Slot[nOutputs];
        
        this.routers = new ArrayList<String>();
        for(Object o : json.getJSONArray("routers")) {
            this.routers.add((String) o);
        }
        
        this.discriminant = json.getString("discriminant");
    }
    
    
    
    
    
    
    @Override
    public void setInput(Slot s) { this.input = s; }

    
    /**
     * Colocara el buffer "s" en el primer hueco disponible, por lo que hay que
     * colocarlos en orden.
     * 
     * @param s 
     */
    @Override
    public void setOutput(Slot s) { 
    
        for(int i=0; i < this.output.length; i++) {
            if(this.output[i] == null) {
                this.output[i] = s;
                break;
            }
        }
    }
    
    
    
    
    
    
    @Override
    public void run() {
        while(this.input.nMessages()>0){
            Document doc = this.input.read();


            String disc = doc.getElementsByTagName(this.discriminant).item(0).getTextContent();

            this.output[this.routers.indexOf(disc)].write(doc);
        }
    }
    
    
    
    /*
    public static void main(String[] args) throws Exception {
        
        JSONObject json = Toolbox.jsonFromFile("config.json");
        
        Distributor distributor = new Distributor(json.getJSONObject("distributor"));
        
        Document doc = Toolbox.createDocument("orderuniq.xml");
        
        Slot s = new Slot();
        s.write(doc);
        distributor.setInput(s);
        Slot hot = new Slot();
        Slot cold = new Slot();
        distributor.setOutput(hot);
        distributor.setOutput(cold);
        
        distributor.run();
        
    }
    */
}
