/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.makerfluidics.Hardware;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author krishna
 */
public class SyringePumpBoard implements ControlBoard {

    HashMap<String, String> portMapping;
    
    
    private int uStepsMove;
    private int pwmSpeed;
    
    private enum PumpType {
        SYRINGE_PUMP,
        FLOW_PUMP
    }
    
    

    /**
     *
     * @param mappingFilePath the value of mappingFilePath
     * @param boardSettingsFilePath the value of boardSettingsFilePath
     */
    public SyringePumpBoard(String mappingFilePath, String boardSettingsFilePath){
        portMapping = new HashMap<>();
        loadMapping(mappingFilePath);
        //loadSettings(boardSettingsFilePath); //TODO: Make the default settings for this
    }
    
    private void loadMapping(String filePath) {
        try {
            String thisLine;
            String[] tokens;
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while ((thisLine = br.readLine()) != null) { // while loop begins here
                if(thisLine.charAt(0)=='#') //Skips comment characters.
                    continue;
                tokens = thisLine.split(" ");
                portMapping.put(tokens[1], tokens[0]);
            } // end while 
        } // end try
        catch (IOException e) {
            System.err.println("Error: " + e);
        }
    }

    @Override
    public String getTurnONCommand(String portName) {
        // "F P" + str(pumpID) + " D" + str(uStepsMove) +";"
        return "F P" + portMapping.get(portName) + " D" + uStepsMove +";";
    }

    @Override
    public String getTurnOFFCommand(String portName) {
        // "B P" + str(pumpID) + " D" + str(uStepsMove) +";"
        return "B P" + portMapping.get(portName) + " D" + uStepsMove +";";
    }

    private void loadSettings(String boardSettingsFilePath) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getInitCommands() {
        ArrayList<String> retString = new ArrayList<>();
        retString.add("E M1" + " D" + pwmSpeed + "\n");
        retString.add("E M2" + " D" + pwmSpeed + "\n");
        retString.add("E M3" + " D" + pwmSpeed + "\n");
        return retString;
    }

    @Override
    public ArrayList<String> getFinishCommands() {
        ArrayList<String> retString = new ArrayList<>();
        retString.add("E M1" + " D" + 0 + "\n");
        retString.add("E M2" + " D" + 0 + "\n");
        retString.add("E M3" + " D" + 0 + "\n");
        return retString;
    }

    
    
}
