package Guarana.Tasks;


import Guarana.Ports.Slot;
import Guarana.util.Toolbox;
import java.util.ArrayList;
import org.json.JSONObject;
import org.w3c.dom.Document;






/**
 * Saca los mensajes ordenados por la salida.
 * 
 * Parametros del json:
 *  - int nPaths : Numero de entradas y salidas.
 *  - String id : Nombre del campo que identifica a los mensajes relacionados.
 * 
 * @author alfonso
 */

public class Correlator extends Task{
    
    private Slot[] input;
    private Slot[] output;
    
    private ArrayList<Document>alInput;
    private ArrayList<Document>alOutput;
    
    private String id, common;
    
    
    
    public Correlator(JSONObject json) {
        
        this.id = json.getString("id");
        this.common = json.getString("common");
        
        this.input = new Slot[2];
        this.output = new Slot[2];
        
        this.alInput = new ArrayList<Document>();
        this.alOutput = new ArrayList<Document>();
    }
    
    
    
    
    @Override
    public void setInput(Slot s) {
   
        for(int i=0; i<this.input.length; i++) {
            if (this.input[i] == null) {
                this.input[i] = s;
                return;
            }
        }
    }

    @Override
    public void setOutput(Slot s) {
        
        for(int i=0; i<this.output.length; i++) {
            if (this.output[i] == null) {
                this.output[i] = s;
                return;
            }
        }
    }

    
    
    // TODO : Buff, vaya pestiño ilegible. Hay que refactorizar. 
    @Override
    public void run() {
        
        Document doc1 = null;
        Document doc2 = null;
        boolean match;
        String id1, id2, common1, common2;
        
        
        int n = this.input[0].nMessages();
        for(int i=0; i<n; i++) {
            
            match = false;
            doc1 = this.input[0].read();
            
            try{
                
                id1 = Toolbox.getText(doc1, this.id);
                common1 = Toolbox.getText(doc1, this.common);
                
            }
            catch(Exception ex) {
                System.out.println("El Correlator ha fallado");
                System.out.println("Mensaje de error: " + ex.getMessage());
                return;
            }
            
            int m = this.input[1].nMessages();
            for (int j = 0; j < m; j++) {
                
                doc2 = this.input[1].read();
                
                try{
                
                    id2 = Toolbox.getText(doc1, this.id);
                    common2 = Toolbox.getText(doc1, this.common);
                }
                catch(Exception ex) {
                    System.out.println("El Correlator ha fallado");
                    System.out.println("Mensaje de error: " + ex.getMessage());
                    return;
                }
                
                /*
                Si los mensajes estan correlacionados, ponemos el flag "match"
                a true y salimos del buble interno
                */
                if(id1.equals(id2) && common1.equals(common2)) {
                    match = true;
                    break;
                }
                // Sino, lo devolvemos a la cola y continuamos.
                else {
                    this.input[1].write(doc2);
                }
            }
            
            //Si los mensajes han sido match, los pasamos al output y continuamos.
            if(match) {
                
                this.output[0].write(doc1);
                this.output[1].write(doc2);
            }
            //Si no ha habido match, lo devolvemos a la cola.
            else {
                this.input[0].write(doc1);
            }
        }
    }
    
    
    
    
    
    
    
    
    
    
    
}
