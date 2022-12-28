package main;

import Guarana.Ports.*;
import Guarana.Tasks.*;
import Guarana.util.Toolbox;
import org.json.JSONObject;

/**
 *
 * @author alfonso
 */
public class Cafe {


    public static void main(String[] args) throws Exception {
        
        JSONObject json = Toolbox.jsonFromFile("config.json");
        
        //Crear las tasks
        //**********************************************************************
        Input input = new Input();
        
        
        Splitter splitter = new Splitter(json.getJSONObject("splitter"));
        Distributor distributor = new Distributor(json.getJSONObject("distributor"));
        
        //Comparten la misma configuracion
        Replicator replicator1 = new Replicator(json.getJSONObject("replicator1"));
        Replicator replicator2 = new Replicator(json.getJSONObject("replicator1"));
        
        
        
        
        //Linkar las tasks
        //**********************************************************************
        Slot s = new Slot();
        
        Toolbox.connect(splitter, distributor);
        Toolbox.connect(distributor, replicator1);
        Toolbox.connect(distributor, replicator2);
        
        
        
        
        //Funcionando
        //**********************************************************************
        
        
        
        
    }
    
}
