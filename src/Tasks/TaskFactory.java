
package Tasks;


/**
 * Usaremos el Patron Factory para la creacion de Tasks.
 * @author alfonso
 */
public class TaskFactory {
    
    private static TaskFactory factory = new TaskFactory();
    

    private TaskFactory() {}


    public TaskFactory getInstance() { return factory; }

    public Task createTask(int type) {

        switch (type) {
            
            case 0:
            break;
                
        


            default: return null;
        }
        return null;
    }
    
}
