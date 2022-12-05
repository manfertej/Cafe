
package Guarana.Tasks;


import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.*;
//import org.w3c.dom.Document;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;

import Guarana.Ports.Slot;
import Guarana.Toolbox;


public class Splitter extends Task{
    

    private Slot input;
    private Slot output;
    private String idExpr;
    private String xpathExpr;
    


    /*Metodos
    **************************************************************************/
    /**
     * Constructor de la clase.
     * @param xpathExpr Expresion XPATH sobre la que se va a hacer el split.
     *                  //order_id
     *                  //drinks/*
     */
    public Splitter(String idExpr, String xpathExpr) { 
        this.xpathExpr = xpathExpr; 
        this.idExpr = idExpr;
    }



    @Override
    public void setInput(Slot s) { this.input = s; }

    @Override
    public void setOutput(Slot s) { this.output = s; }


    
    //TODO: Revisar si deberia hacerle un Facade a Document
    public void run() {

        Document doc = this.input.read();
        XPath xpath = XPathFactory.newInstance().newXPath();
        
        try {
            //Conseguimos el nodo que contiene el ID y los nodos de las bebidas.
            Node id = doc.getElementsByTagName(idExpr).item(0);
            NodeList nodeList = (NodeList) xpath.compile(this.xpathExpr).evaluate(doc, XPathConstants.NODESET); 
            
            Document docAux;
            Node nAux;
            //Por cada nodo creamos un documento que contenga el nodo id y el nodo bebida.
            for(int i=0; i<nodeList.getLength(); i++) {

                docAux = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                nAux = nodeList.item(i);
                System.out.println(Toolbox.toString(nAux));


            }
            

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    
    
    }













    public static void main(String[] args) throws Exception {
        Slot s = new Slot();
        Splitter splitter = new Splitter("//order_id/*", "//drinks/*");
        splitter.setInput(s);
        
        Document doc = Toolbox.createDocument("order2.xml");
        //System.out.println(Toolbox.toString(doc));
        //System.out.println(doc.getDocumentElement().getNodeName());

        s.write(doc);
        splitter.run();


    }

}
