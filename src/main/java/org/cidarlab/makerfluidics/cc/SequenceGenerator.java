/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.makerfluidics.cc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 *
 * @author krishna
 */
public class SequenceGenerator {
    
    int globaltimepoint;
    
    private ArrayList<Sequence> sequences;
    
    private ArrayList<HWCommand> commands;
    
    public SequenceGenerator(String cmdString){
        globaltimepoint = 0;
        sequences = new ArrayList<>();
        commands = new ArrayList<>();
        parseCommands(cmdString);
    }

    private void parseCommands(String cmdString) {
        String[] cmdlines = cmdString.split("\n");
        String[] tokens;
        Sequence newSequence = new Sequence();
        for(String line : cmdlines){
            tokens = line.split(" ");
            boolean isfirsttimeflag = true;
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
        
        for(Sequence seq : sequences){
            commands.addAll(seq.generateHWCommands(globaltimepoint));
            globaltimepoint+= (seq.hold_time + seq.setup_time);
        }
        
        sortcommandstemporally();
    }

    private void sortcommandstemporally() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void getCommands() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static class Sequence {
        
        public int timepoint;
        
        float setup_time = 0;
        float hold_time = 0;
        HashMap<String,Boolean> portstochange ;
        
        public Sequence() {
            //TODO : Check if this one works properly
            portstochange = new HashMap<>();
        }
        
        private ArrayList<HWCommand> generateHWCommands(int startpoint) {
            timepoint = startpoint;
            
            ArrayList<HWCommand> cmdlist = new ArrayList<>();
            /*
            Here we take the time startpoint generate commands at timepoint + setup_time
            and then generate the reversing commands at timpoint + setup_time + hold_time
            */
            
            for(String key : portstochange.keySet()){
                //TODO: Generate the HW command for set at timepoint + setup_time , add to cmdlist
                
                //TODO: Generate the HW command for reset at timpoint + setup_time + hold_time cmdlist
            }
            
            return cmdlist;
        }
    }

    private static class HWCommand {

        public HWCommand() {
        }
    }
}
