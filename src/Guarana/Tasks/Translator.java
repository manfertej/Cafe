
package Guarana.Tasks;

import Guarana.Ports.Slot;
import Guarana.util.Toolbox;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import org.json.JSONObject;
import org.w3c.dom.Document;

/**
 * Tarea que cambia el formato de los mensajes para adaptarlo a otro contexto.
 */
public class Translator extends Task{
    
    private Slot input;
    private Slot output;

    //Fichero XSL que determina como se hará la traduccion.
    private String xslPath;

    
    
    public Translator(JSONObject json) {
        
        try {
            this.xslPath = json.getString("path");
        } catch (Exception ex) {
            System.out.println("Fallo al leer el xsl de traduccion");
        }
        
    }
    

    
    @Override
    public void setInput(Slot s) {this.input = s;}

    @Override
    public void setOutput(Slot s) { this.output = s; }

    
    
    public void translate() throws Exception{
        Document doc = this.input.read();
        
        //Por algun motivo, si no lo hago asi da un error.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document xsl = db.parse(this.xslPath);

        
        //System.out.println(Toolbox.toString(xsl));
        
        Source xmlSource = new DOMSource(doc);
        Source xsltSource = new DOMSource(xsl);
        DOMResult result = new DOMResult();


        TransformerFactory transFact = TransformerFactory.newInstance();
        Transformer trans = transFact.newTransformer(xsltSource);

        trans.transform(xmlSource, result);

        Document resultDoc = (Document) result.getNode();
        
        //System.out.println(Toolbox.toString(resultDoc));
        this.output.write(resultDoc);
    }
    
    
    
    @Override
    public void run() throws Exception {

        while(!this.input.empty()) this.translate();
    }

    
    /*
    public static void main(String[] args) throws Exception {
        
        JSONObject json = Toolbox.jsonFromFile("config.json");
        Slot s = new Slot();
        Translator splitter = new Translator(json.getJSONObject("translatorFrias"));
        splitter.setInput(s);
        
        Document doc = Toolbox.createDocument("testCorrelator1.xml");
        
        s.write(doc);
        splitter.run();
    }
    */
    
}
