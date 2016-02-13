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
public class SyringePumps implements ControlDevice{

    @Override
    public String getTurnONCommand(String portName) {
        return "ON" + portName;
    }

    @Override
    public String getTurnOFFCommand(String portName) {
        return "OFF" + portName;
    }
    
}
