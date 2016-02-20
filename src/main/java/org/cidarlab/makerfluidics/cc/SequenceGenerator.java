/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.makerfluidics.cc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import org.cidarlab.makerfluidics.Hardware.ControlBoard;

/**
 *
 * @author krishna
 */
public class SequenceGenerator {
    
    private int globaltimepoint;
    
    private ArrayList<Sequence> sequences;
    
    private ArrayList<HWCommand> commands;

    public ArrayList<HWCommand> getCommands() {
        return commands;
    }
    
    private ControlBoard device;
    
    /**
     *
     * @param cmdString the value of cmdString
     * @param deviceType the value of deviceType
     */
    public SequenceGenerator(String cmdString, ControlBoard deviceType){
        device = deviceType;
        globaltimepoint = 0;
        sequences = new ArrayList<>();
        commands = new ArrayList<>();
        parseCommands(cmdString);
    }

    private void parseCommands(String cmdString) {
        String[] cmdlines = cmdString.split("\n");
        String[] tokens;
        Sequence newSequence = new Sequence();
        boolean isfirsttimeflag = true;
        for(String line : cmdlines){
            tokens = line.split(" ");
            
            switch(tokens[0]){
                case "COMMAND":
                    //create a new time point for execution
                    if(!isfirsttimeflag){
                        sequences.add(newSequence);
                    }
                    newSequence = new Sequence();
                    isfirsttimeflag&=false;
                    break;
                case "SETUP_TIME":
                    //offset the globaltimepoint
                    newSequence.setup_time = Float.parseFloat(tokens[1]);
                    break;
                case "HOLD_TIME":
                    //generate reversing commands to all the ports
                    newSequence.hold_time = Float.parseFloat(tokens[1]);
                    break;
                default:
                    if(tokens[1].equals("STATE")){
                        newSequence.portstochange.put(tokens[0], (tokens[2].equals("true")));
                    }
            }
        }
        
        commands.addAll(getInitCommands(device));
        
        for(Sequence seq : sequences){
            commands.addAll(seq.generateHWCommands(globaltimepoint,device));
            globaltimepoint+= (seq.hold_time + seq.setup_time);
        }
        
        commands.addAll(getFinishCommands(device));
        
        sortcommandstemporally();
    }

    private void sortcommandstemporally() {
        /*
        TODO : Run through each of the commands and then arrange them in 
        a chronological order.
        */
        Collections.sort(commands);
    }

    private ArrayList<HWCommand> getInitCommands(ControlBoard device) {
        ArrayList<HWCommand> retcmds = new ArrayList<>();
        for(String cmdTxt : device.getInitCommands()){
            retcmds.add(new HWCommand(globaltimepoint, cmdTxt));
        }
        return retcmds;
    }

    private ArrayList<HWCommand> getFinishCommands(ControlBoard device) {
        ArrayList<HWCommand> retcmds = new ArrayList<>();
        for(String cmdTxt : device.getFinishCommands()){
            retcmds.add(new HWCommand(globaltimepoint, cmdTxt));
        }
        return retcmds;
    }
    
    //Member Classes
    private class Sequence {
        
        public int localtimepoint;
        
        float setup_time = 0;
        float hold_time = 0;
        HashMap<String,Boolean> portstochange ;
        
        /**
         * This object is a logical representation of every command that has
         * been generated with cassie's classical control system intended for
         * biostream. 
         * 
         * This entire implementation might change when a new control generation
         * mechanism is created.
         * 
         */
        public Sequence() {
            //TODO : Check if this one works properly
            portstochange = new HashMap<>();
        }
        
        /**
         *
         * @param startpoint the value of startpoint
         * @param device the value of device
         */
        private ArrayList<HWCommand> generateHWCommands(int startpoint, ControlBoard device) {
            localtimepoint = startpoint;
            
            ArrayList<HWCommand> cmdlist = new ArrayList<>();
            /*
            Here we take the time startpoint generate commands at localtimepoint + setup_time
            and then generate the reversing commands at timpoint + setup_time + hold_time
            */
            
            for(String key : portstochange.keySet()){
                String cmdText,cmdTextUndo;
                if(portstochange.get(key)){
                    cmdText = device.getTurnONCommand(key);
                    cmdTextUndo = device.getTurnONCommand(key);
                }else{
                    cmdText = device.getTurnOFFCommand(key);
                    cmdTextUndo = device.getTurnONCommand(key);
                }
                
                //Generate the HW command for set at localtimepoint + setup_time , add to cmdlist
                cmdlist.add(new HWCommand(localtimepoint+setup_time,cmdText));
                
                //TODO: Generate the HW command for reset at timpoint + setup_time + hold_time cmdlist
                cmdlist.add(new HWCommand(localtimepoint+setup_time+hold_time,cmdTextUndo));
            }
            
            return cmdlist;
        }
    }

}
