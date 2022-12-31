
package Guarana.util;


import Guarana.Ports.Input;
import Guarana.Ports.Output;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import Guarana.Ports.Slot;
import Guarana.Tasks.Task;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;




/**
 * Esta clase contendrá funciones auxiliares.
 * @author alfonso
 */
public class Toolbox {
    
    /**
     * Creará un slot que conectará dos Task entre si
     * @param t1
     * @param t2 
     */
    public static void connect(Task t1, Task t2) {
        
        Slot sAux = new Slot();
        t1.setInput(sAux);
        t2.setOutput(sAux);
    }

    
    
    
    public static void connect(Input inp, Task t) {
        
        Slot sAux = new Slot();
        inp.setInput(sAux);
        t.setInput(sAux);
    }
    
    
    public static void connect(Task t, Output o) {
        
        Slot sAux = new Slot();
        t.setOutput(sAux);
        o.setInput(sAux);
    }
    
    
    
    /**
     * Crea un documento en base a un fichero existente.
     * 
     * @param dir - La direccion del fichero.
     * @return
     * @throws Exception 
     */
    public static Document createDocument(String dir) throws Exception{
        File inputFile = new File(dir);

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        
        return doc;
    }
    
    
    
    
    public static String toString(Document doc) {
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }


    
    
    public static String toString(Node node) throws Exception {
        StringWriter writer = new StringWriter();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.transform(new DOMSource(node), new StreamResult(writer));
    
        return writer.toString();
    }


    
    
    /**
     * Crea un JSONObject en base al objeto cuya ruta se pasa por parametro.
     * Lanza una excepcion si el objeto no existe.
     * @param path
     * @return
     * @throws IOException 
     */
    public static JSONObject jsonFromFile(String path) throws IOException {
        
        String content = new String(Files.readAllBytes(Paths.get(path)));
        return new JSONObject(content);
    }
    
    
    public static String getText(Document doc, String name) throws Exception{
        
        XPath xPath = XPathFactory.newInstance().newXPath();
        
        NodeList nodeList = (NodeList) xPath.compile("//" + name)
                        .evaluate(doc, XPathConstants.NODESET);
        Node n = nodeList.item(0);
        Element e = (Element) n;
        
        return e.getTextContent();
    }
    

    public static Document newDocument() throws Exception {
        
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
    }
    
    
}

