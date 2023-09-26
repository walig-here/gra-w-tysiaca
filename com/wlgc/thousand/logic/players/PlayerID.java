package com.wlgc.thousand.logic.players;

public class PlayerID {
    
    //---------------------------------------------------------------------------------
    /* ### POLA ### */

    private int id;
    private final static int NUMBER_OF_PLAYERS = 3;

    //---------------------------------------------------------------------------------
    /* ### METODY ### */

    public PlayerID(int id){
        set(id);
    } 

    public void set(int id){
        this.id = id%NUMBER_OF_PLAYERS;
    }

    public void setToNextPlayerId(){
        id = (id+1)%NUMBER_OF_PLAYERS;
    }

    public PlayerID getNextPlayerID(){
        return new PlayerID((id+1)%NUMBER_OF_PLAYERS);
    }

    public int toInt(){
        return id;
    }

    public boolean isAI(){
        return id != 0;
    }

    //---------------------------------------------------------------------------------

}
