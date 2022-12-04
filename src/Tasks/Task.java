
package Tasks;

import Ports.Slot;

/**
 * De momento esto es solo para Agrupar las Tasks y para gestionar el ensamblaje de Task-Slot
 * @author alfonso
 */
public abstract class Task {
    
    public abstract void setInput(Slot s);
    public abstract void setOutput(Slot s);
    
    /* No se si voy a necesitar esto. 
    public abstract Slot getInput(Slot s);
    public abstract Slot getOutput(Slot s);
    */
}

