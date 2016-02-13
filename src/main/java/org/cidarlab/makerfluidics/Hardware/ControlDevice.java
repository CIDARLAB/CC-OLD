/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.makerfluidics.Hardware;

/**
 *
 * @author krishna
 */
public interface ControlDevice {
    public String getTurnONCommand(String portID);
    
    public String getTurnOFFCommand(String portID);
}
