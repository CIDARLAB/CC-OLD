/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cidarlab.makerfluidics.cc;

import java.util.TimerTask;

/**
 *
 * @author krishna
 */
class ExecutionTask extends TimerTask {

    private Board board;
    
    private float time = 0; //In seconds
    
    public ExecutionTask(Board boardRef) {
        this.board = boardRef;
    }

    @Override
    public void run() {
        
        switch(board.boardState){
            case RUNNING:
                time += 0.5; //Update every half a second
                board.executeCommand(time);
                break;
            case STOPPED:
                time = 0;
                break;
            case PAUSED:
                break;
        }
    }
    
}
