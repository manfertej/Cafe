
package Tasks;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import Ports.Slot;



public class Splitter extends Task{
    

    private Slot input;
    private Slot output;
    private String expression;
    


    /*Metodos
    **************************************************************************/

    public Splitter(String s) { this.expression = s; }



    @Override
    public void setInput(Slot s) { this.input = s; }

    @Override
    public void setOutput(Slot s) { this.output = s; }



    public void run() {

        Document doc = this.input.read();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
        try {

            NodeList nodes = (NodeList) xpath.compile(this.expression).evaluate(doc, XPathConstants.NODESET); 
        
        } catch (Exception e) {
            // TODO: Cuando la expresion no se puede compilar debera lanzar una excepcion
        }
    
    
    }


}
