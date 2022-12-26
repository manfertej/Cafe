
package Guarana.Tasks;

import org.json.JSONArray;
import org.json.JSONObject;



/**
 * Usaremos el Patron Factory para la creacion de Tasks.
 * @author alfonso
 */
public class TaskFactory {
    
    private static TaskFactory factory = new TaskFactory();
    

    private TaskFactory() {}


    public TaskFactory getInstance() { return factory; }

    
    public Task createTask(Tasks type, JSONObject json) {

        
        
        switch (type) {
            
            case SPLITTER:
                
                return new Splitter(json);

                
        


            default: return null;
        }
    }
    
}
