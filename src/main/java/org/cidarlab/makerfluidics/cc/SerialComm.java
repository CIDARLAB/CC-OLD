/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.makerfluidics.cc;

import com.fazecast.jSerialComm.SerialPort;
import java.io.BufferedReader;                    //BufferedReader makes reading operation efficient
import java.io.InputStreamReader;         //InputStreamReader decodes a stream of bytes into a character set
import java.io.OutputStream;          //writes stream of bytes into serial port

import java.util.TooManyListenersException;
import java.util.Scanner;

/**
 *
 * @author krishna
 */
public class SerialComm {
    /*
    TODO :  Modify this entire class to send out the commands.
    */
    SerialPort port;
    
   
    

    public SerialComm() {
    }
    
    public boolean connectToPort(String portname){
        port = SerialPort.getCommPort(portname);
        return port.openPort();
    }
    
    public SerialPort[] getCommPorts(){
        return SerialPort.getCommPorts();
    }

    void sendCommand(String command) {
        port.writeBytes(command.getBytes(), command.getBytes().length);
    }

}
