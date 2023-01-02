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
        //Input input = new Input();
        Splitter splitter = new Splitter(json.getJSONObject("splitter"));
        Distributor distributor = new Distributor(json.getJSONObject("distributor"));
        Replicator replicatorCalientes = new Replicator(json.getJSONObject("replicator1"));
        Replicator replicatorFrias = new Replicator(json.getJSONObject("replicator1"));
        Translator translatorFrias = new Translator(json.getJSONObject("translatorFrias"));
        Translator translatorCalientes = new Translator(json.getJSONObject("translatorCalientes"));
        Solicitude solicitudeCalientes = new Solicitude();
        Solicitude solicitudeFrias = new Solicitude();
        Correlator correlatorCalientes = new Correlator(json.getJSONObject("correlator"));
        Correlator correlatorFrias = new Correlator(json.getJSONObject("correlator"));
        Enricher enricherCalientes = new Enricher(json.getJSONObject("enricher"));
        Enricher enricherFrias = new Enricher(json.getJSONObject("enricher"));
        Merger merger = new Merger(json.getJSONObject("merger"));
        Aggregator aggregator = new Aggregator(json.getJSONObject("aggregator"));
        Output o = new Output();
        
        
        //Linkar las tasks
        //**********************************************************************
        
        //Toolbox.connect(input, splitter);
        Toolbox.connect(splitter, distributor);
        Toolbox.connect(distributor, replicatorCalientes);
        Toolbox.connect(distributor, replicatorFrias);
        Toolbox.connect(replicatorCalientes, translatorCalientes);
        Toolbox.connect(replicatorFrias, translatorFrias);
        Toolbox.connect(translatorCalientes, solicitudeCalientes);
        Toolbox.connect(translatorFrias, solicitudeFrias);
        
        Toolbox.connect(solicitudeCalientes, correlatorCalientes);
        Toolbox.connect(replicatorCalientes, correlatorCalientes);
        Toolbox.connect(solicitudeFrias, correlatorFrias);
        Toolbox.connect(replicatorFrias, correlatorFrias);
        
        Toolbox.connect(correlatorCalientes, enricherCalientes);
        Toolbox.connect(correlatorCalientes, enricherCalientes);
        Toolbox.connect(correlatorFrias, enricherFrias);
        Toolbox.connect(correlatorFrias, enricherFrias);
        
        Toolbox.connect(enricherCalientes, merger);
        Toolbox.connect(enricherFrias, merger);
        
        Toolbox.connect(merger, aggregator);
        Toolbox.connect(aggregator, o);
        
        
        
        
        
        
        //Funcionando
        //**********************************************************************
        //input.selectFile();
        Slot s = new Slot();
        s.write(Toolbox.createDocument("orderFail.xml"));
        splitter.setInput(s);
        
        splitter.run();
        distributor.run();
        replicatorCalientes.run();
        replicatorFrias.run();
        translatorFrias.run();
        translatorCalientes.run();
        solicitudeCalientes.query();
        solicitudeFrias.query();
        correlatorCalientes.run();
        correlatorFrias.run();
        enricherCalientes.run();
        enricherFrias.run();
        merger.run();
        aggregator.run();
        o.run();
        
        
    }
    
}
