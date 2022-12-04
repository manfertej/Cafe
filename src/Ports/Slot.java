
package Ports;

import Tasks.Task;
import java.util.LinkedList;
import java.util.Queue;

import org.w3c.dom.Document;


/**
 * Buffer de mensajes entre tareas.
 * Funcionará como una cola para asegurar prioridad a los 
 * mensajes mas antiguos
 */
public class Slot {

    
    private Queue<Document> buffer;


    
    /* Metodos
    ***************************************************************************/
    
    
    public Slot() {
        this.buffer = new LinkedList<Document>();
    }

    
    
    /**
     * Lee un documento del slot.
     * @return El documento a la cabeza de la cola.
     */
    public Document read () { return this.buffer.poll(); }


    
    /**
     * Añade un documento al slot.
     * @param doc : El documento que se va a añadir.
     */
    public void write(Document doc) { this.buffer.add(doc); }


    
    /**
     * 
     * @return : Numero de mensajes que hay en el Slot.
     */
    public int nMessages() { return this.buffer.size(); }


    

    
}
