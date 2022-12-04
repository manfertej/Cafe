
package Tasks;

import org.w3c.dom.Document;

/**
 * Tarea que cambia el formato de los mensajes para adaptarlo a otro contexto.
 */
public class Translator {
    
    //Fichero XSL que determina como se hará la traduccion.
    private final Document formatter;


    
    public Translator(Document formatter) {
        this.formatter = formatter;
    }

}
