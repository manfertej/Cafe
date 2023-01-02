
package Guarana.Tasks;

import Guarana.Ports.Slot;
import Guarana.util.Toolbox;
import java.util.ArrayList;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;



/**
 * 
 * 
 * @author alfonso
 */
public class Aggregator extends Task {
    
    
    private String id;
    private String list;
    private String item;
    
    private Slot input;
    private Slot output;
    
    
    
    /**
     * Toma todos los mensajes de su entrada y combina los que estan relacionados
     * en una lista.
     * 
     * - id: Nombre del campo que correlaciona los mensajes.
     * - list: Nombre del campo que va a agrupar los elementos divididos.
     * 
     * @param json 
     */
    public Aggregator(JSONObject json) {
        
        this.id = json.getString("id");
        this.list = json.getString("list");
        this.item = json.getString("items");
    }
    
    
    
    
    @Override
    public void setInput(Slot s) { this.input = s; }

    @Override
    public void setOutput(Slot s) { this.output = s;}

    
    
    @Override
    public void run() throws Exception {
    
        while(!this.input.empty()) {
            
            Document doc = this.input.read();
            ArrayList<Document> array = new ArrayList<>();
            
            //Tomamos el id del primer mensaje de la cola.
            String id1 = Toolbox.getText(doc, id);
            array.add(doc);
            
            
            //Buscamos en la cola todos los mensajes con el mismo id.
            int n = this.input.nMessages();
            for (int i = 0; i < n; i++) {
                
                Document aux = this.input.read();
                
                String id2 = Toolbox.getText(aux, id);
                
                
                //Si tienen el mismo id, lo añadimos a la lista
                if(id1.equals(id2)){
                    array.add(aux);
                }
                //Sino, lo devolvemos a la cola.
                else {
                    this.input.write(aux);
                }
            }
            
            //Creamos un nuevo documento para combinar en el todo los mensajes.
            Document combined = Toolbox.newDocument();
            Element root = combined.createElement(doc.getDocumentElement().getNodeName());
            combined.appendChild(root);
            
            //Creamos el tag del id y le damos su valor.
            Node id = combined.createElement(this.id);
            root.appendChild(id);
            Text idValue = combined.createTextNode(doc.getElementsByTagName(this.id).item(0).getTextContent());
            id.appendChild(idValue);
            
            //Creamos el tag que servira como lista.
            Node list = combined.createElement(this.list);
            root.appendChild(list);
            
            
            //Recorremos todos los documentos y insertamos sus items en la lista.
            for(Document d : array) {
                
                Node nodeAux = d.getElementsByTagName(this.item).item(0);
                nodeAux = combined.importNode(nodeAux, true);
                list.appendChild(nodeAux);
            }
            
            
            //System.out.println(Toolbox.toString(id));
            //System.out.println(Toolbox.toString(combined));
            this.output.write(combined);
            array.clear();
        }
    }


    
/*
    public static void main(String[] args) throws Exception {
    
        Document xml1 = Toolbox.createDocument("testCorrelator1.xml");
        Document xml2 = Toolbox.createDocument("testCorrelator2.xml");
        JSONObject json = Toolbox.jsonFromFile("config.json");
        
        Slot input = new Slot();
        Aggregator a = new Aggregator(json.getJSONObject("aggregator"));
        a.setInput(input);
        
        input.write(xml1);
        input.write(xml2);
        
        a.run();
    }
*/




}
