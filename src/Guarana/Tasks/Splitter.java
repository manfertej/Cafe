
package Guarana.Tasks;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;


import Guarana.Ports.Slot;
import Guarana.util.Toolbox;
import org.json.JSONObject;


/**
 * La clase debe recibir un JSON con dos parametros de configuracion.
 * - id: 
 * 
 * 
 * @author alfonso
 */

public class Splitter extends Task{
    

    private Slot input;
    private Slot output;
    
    //Objeto JSON de configuracion.
    private JSONObject json;
    
    //Expresion XPath que determina el id que se va a mantener. 
    private String idExpr;
    
    private XPath xpath;
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
            

            //Por cada nodo de la lista creamos un documento que 
            //contenga el nodo id y el nodo bebida.
            Document docAux;
            Node nAux;
            for(int i=0; i<nodeList.getLength(); i++) {

                docAux = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                nAux = nodeList.item(i);
                
                Element root = docAux.createElement("root");
                docAux.appendChild(root);
                
                Node idAux;
                if(id != null){
                    idAux = docAux.importNode(id, true);
                    docAux.getDocumentElement().appendChild(idAux);
                }
                
                Node itemAux = docAux.importNode(nAux, true);
                docAux.getDocumentElement().appendChild(itemAux);
                
                System.out.println(Toolbox.toString(docAux));
            }
            

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    
    
    }













    public static void main(String[] args) throws Exception {
        
        JSONObject json = Toolbox.jsonFromFile("config.json");
        Slot s = new Slot();
        Splitter splitter = new Splitter(json.getJSONObject("splitter"));
        splitter.setInput(s);
        
        Document doc = Toolbox.createDocument("order1.xml");
        
        s.write(doc);
        splitter.run();
        


    }

}