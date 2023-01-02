package Guarana.Ports;

import Guarana.util.Toolbox;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

/**
 * Conector para una base de datos
 * db4free.net
 * nombre: cafedb
 * user: alfonsofertej
 * pass: UHUetsiiia
 * nombre host: db4free.net
 * puerto: 3306
 * 
 * @author alfonso
 */
public class Solicitude extends Port{
  
    private java.sql.Connection connection;
    
    private Slot input;
    private Slot output;
    
    
    
    @Override
    public void setInput(Slot s) { this.input = s; }
    @Override
    public void setOutput(Slot s) { this.output = s; }
    
    
    
    
    public void query() throws Exception {
        try {
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection("jdbc:mysql://db4free.net:3306/cafedb", "alfonsofertej", "UHUetsiiia");
            
            Document doc;
            while(!this.input.empty()) {
                
                doc = this.input.read();
                String query = doc.getElementsByTagName("sql").item(0).getTextContent();
                //System.out.println(query);
                PreparedStatement statement = this.connection.prepareStatement(query); 
                ResultSet resultset = statement.executeQuery();
                
                //Comprobamos si la bebida existe en la BD
                int count = 0;
                if(resultset.next()) count = resultset.getInt("count(1)");
                
                //Eliminamos la consulta del mensaje
                Element element = (Element) doc.getElementsByTagName("sql").item(0);
                element.getParentNode().removeChild(element);
                
                Element avalible = doc.createElement("avalible");
                Text text = null;
                if(count == 1) {
                    text = doc.createTextNode("true");
                }
                else {
                    text = doc.createTextNode("false");
                }
                
                avalible.appendChild(text);
                doc.getElementsByTagName("drink").item(0).appendChild(avalible);
                
                
                //System.out.println(Toolbox.toString(doc));
                this.output.write(doc);
            }
        
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        finally{
            this.connection.close();
        }
    }
    
    
    
    /*
    public static void main(String[] args) throws Exception {
        
        Slot s = new Slot();
        s.write(Toolbox.createDocument("testDB.xml"));
        
        Solicitude db = new Solicitude();
        db.setInput(s);
        db.query();
    }
    */
}
