
package Guarana.Tasks;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;


import Guarana.Ports.Slot;
import Guarana.util.Toolbox;
import org.json.JSONObject;


/**
 * Divide el mensaje en tantos mensajes como la lista que contiene.
 * 
 * La clase debe recibir un JSON con dos parametros de configuracion.
 * - id: Nombre de la etiqueta de identificacion que se mantendra en todos los
 *  documentos. Si no existe no la colocara.
 * - list: Expresion XPath que determina como encontrar la lista dentro del XML.
 * 
 * @author alfonso
 */

public class Splitter extends Task{
    

    private Slot input;
    private Slot output;
   
    //Expresion XPath que determina el id que se va a mantener. 
    private String idExpr;
    
    //Expresion XPath para encontrar la lista de elementos.
    private String xpathExpr;


    /*Metodos
    **************************************************************************/
    
    /**
     * Constructor de la clase.
     * @param json
     */
    public Splitter(JSONObject json) {
        
        try {
            this.idExpr = json.getString("id");
        }
        catch (Exception ex) {
            this.idExpr = null;
        }
        this.xpathExpr = json.getString("list");
    }



    @Override
    public void setInput(Slot s) { this.input = s; }

    @Override
    public void setOutput(Slot s) { this.output = s; }


    
    public void run() {
        
        Document doc = this.input.read();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
        try {
            
            //Nodo con el id de la comanda
            Node id = null;
            if (this.idExpr != null) id = doc.getElementsByTagName(idExpr).item(0);
            
            //Nodo con la lista de bebidas de la comanda.
            NodeList nodeList = (NodeList) xpath.compile(this.xpathExpr).evaluate(doc, XPathConstants.NODESET); 
            


            Document docAux;
            Node nAux;
            for(int i=0; i<nodeList.getLength(); i++) {

                //Creamos el documento, le damos un root con el mismo nombre que
                //el original
                docAux = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                Element root = docAux.createElement(doc.getDocumentElement().getNodeName());
                docAux.appendChild(root);
                nAux = nodeList.item(i);
                
                Node idAux;
                if(id != null){
                    idAux = docAux.importNode(id, true);
                    docAux.getDocumentElement().appendChild(idAux);
                }
                
                Node itemAux = docAux.importNode(nAux, true);
                docAux.getDocumentElement().appendChild(itemAux);
                
                System.out.println(Toolbox.toString(docAux));
                this.output.write(docAux);
            }
        } 
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }



// Main para pruebas ***********************************************************
/*
    public static void main(String[] args) throws Exception {
        
        JSONObject json = Toolbox.jsonFromFile("config.json");
        Slot s = new Slot();
        Splitter splitter = new Splitter(json.getJSONObject("splitter"));
        splitter.setInput(s);
        
        Document doc = Toolbox.createDocument("order1.xml");
        
        s.write(doc);
        splitter.run();
    }
*/
}