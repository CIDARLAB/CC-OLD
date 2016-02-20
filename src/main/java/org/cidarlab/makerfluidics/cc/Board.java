/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.makerfluidics.cc;

import com.sun.org.apache.xalan.internal.xsltc.trax.SAX2StAXBaseWriter;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author krishna
 */
public class Board {

    public Board() {
    }
    
    private final float EXECUTION_WINDOW = 3; //SECONDS ??
    void executeCommand(float time) {
        /*
        TODO: Check when the execution time is for the next command. If it is 
        within the execution window. send the command to the device. Then push 
        */
        if(abs(nextcommandexecutiontime - time) < EXECUTION_WINDOW ){
            channel.sendCommand(hardwareCommands.get(executionIndex).getCommand());
            executionIndex ++;
            setNextCommand();
            //recursively call the execute to execute all the commands that 
            //have to be executed at the same time.
            executeCommand(time);
        }
        
    }

    private void setNextCommand() {
        nextcommandexecutiontime = hardwareCommands.get(executionIndex).getExecutiontime();
    }
    
    public enum boardStates{
        RUNNING,
        PAUSED,
        STOPPED
    }
    
    private ArrayList<HWCommand> hardwareCommands;
    
    public boardStates boardState;
    
    private Timer timer;
    
    private float nextcommandexecutiontime;
    
    private int executionIndex = 0;
    
    SerialComm channel;
    
    /**
     *
     * @param cmds the value of cmds
     */
    public Board(ArrayList<HWCommand> cmds, SerialComm serialComm){
        boardState = boardStates.STOPPED;
        hardwareCommands = cmds;
        channel = serialComm;
        timer = new Timer();
        timer.schedule(new ExecutionTask(this), 2000, 500);
    }
    
    public void startSequence(){
        boardState = boardStates.RUNNING;
        setNextCommand();
    }
    
    public void pauseSequence(){
        boardState = boardStates.PAUSED;
    }
    
    public void stopSequence(){
        boardState = boardStates.STOPPED;
        executionIndex = 0;
    }
    
}
