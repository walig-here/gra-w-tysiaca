package com.wlgc.thousand.logic;

import com.wlgc.thousand.logic.players.Player;

public class Logic {
    public static Logic getInstance() {
        if(instance == null)
            instance = new Logic();
        return instance;
    }

    public boolean gameTick(){
        switch(current_stage){
            case deal: deal(); return true;
            case bid: bidTurn(); return true;
            case play: gameTurn(); return true;
            case summarize: return summarizeDeal();
        }
        return true;
    }

    static private Logic instance;
    private GameStage current_stage;
    private final Player[] players;
    private int dealer_id;
    private int leading_player_id; 
    private int current_player_id; 
    private final Table game_table;
    
    private Logic(){
        players = new Player[3];
        current_stage = GameStage.deal;
        game_table = Table.getInstance();
    }

    private void deal(){

    }

    private boolean bidTurn(){
        return false;
    } 

    private boolean gameTurn(){
        return false;
    }

    private boolean summarizeDeal(){
        return false;
    }
}
