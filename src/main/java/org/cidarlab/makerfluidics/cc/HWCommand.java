/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.makerfluidics.cc;

/**
 *
 * @author krishna
 */
public class HWCommand implements Comparable<Object> {

    private float executiontime;
    
    public float getExecutiontime() {
        return executiontime;
    }
    
    public void setExecutiontime(float executiontime) {
        this.executiontime = executiontime;
    }
    
    public String getCommand() {
        return command;
    }
    
    public void setCommand(String command) {
        this.command = command;
    }
    private String command;

    /**
     *
     * @param time the value of time
     * @param cmdtext the value of cmdtext
     */
    public HWCommand(float time, String cmdtext) {
        executiontime = time;
        command = cmdtext;
    }
    
    public String getString(){
        return (executiontime +" " + command);
    }
    
    @Override
    public int compareTo(Object o) {        
        return Float.compare(this.executiontime, ((HWCommand) o).getExecutiontime());
    }
    
}
