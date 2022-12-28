
package Guarana.Tasks;

import Guarana.Ports.Slot;
import java.io.File;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONObject;
import org.w3c.dom.Document;

/**
 * Tarea que cambia el formato de los mensajes para adaptarlo a otro contexto.
 */
public class Translator extends Task{
    
    private Slot input;
    private Slot output;

    //Fichero XSL que determina como se hará la traduccion.
    private Document formatter;

    
    
    public Translator(JSONObject json) {
        
    }
    

    
    @Override
    public void setInput(Slot s) {this.input = s;}

    @Override
    public void setOutput(Slot s) { this.output = s; }

    
    
    
    @Override
    public void run() {

        TransformerFactory factory = TransformerFactory.newInstance();
        Source xslt = new StreamSource(new File("transform.xsl"));
    }

}
