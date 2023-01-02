/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Guarana.Ports;


import javax.swing.JFileChooser;
import org.w3c.dom.Document;

/**
 * Te elije el fichero que entra al sistema.
 * @author alfonso
 */
public class Input extends Port{
    
    Slot input;
    
    
    @Override
    public void setInput(Slot s) { this.input = s; }
    @Override
    public void setOutput(Slot s){}
    
    
    public void selectFile() {
        
        JFileChooser jfc = new JFileChooser();
        jfc.showOpenDialog(jfc);
        String path = jfc.getSelectedFile().getPath();
        
        try {
            
            Document doc = Guarana.util.Toolbox.createDocument(path);
            this.input.write(doc);
        
        } catch (Exception ex) {}
    }

}
