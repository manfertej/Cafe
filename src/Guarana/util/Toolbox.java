
package Guarana.util;


import org.w3c.dom.Document;
import org.w3c.dom.Node;

import Guarana.Ports.Slot;
import Guarana.Tasks.Task;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.json.JSONObject;




/**
 * Esta clase contendrá metodos auxiliares
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



}

