
package Tasks;

import Ports.Slot;

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

    //TODO: Se deberia hacer un 

}
