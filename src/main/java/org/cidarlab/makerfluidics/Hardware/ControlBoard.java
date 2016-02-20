/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.makerfluidics.Hardware;

import java.util.ArrayList;

/**
 *
 * @author krishna
 */
public interface ControlBoard {
    public String getTurnONCommand(String portID);
    
    public String getTurnOFFCommand(String portID);
    
    public ArrayList<String> getInitCommands();
    
    public ArrayList<String> getFinishCommands();
}
