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

    public Player[] getPlayers(){
        return players;
    }

    public GameStage getCurrentStage(){
        return current_stage;
    }

    public Integer getBiddingWinnerId(){
        if(current_stage == GameStage.bid)
            return leading_player_id;
        
        for(int i = 0; i < players.length; i++)
            if(players[i].getBet() > 0)
                return i;
        
        return null;
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
        for(int i = 0; i < players.length; i++)
            players[i] = new Player();
        current_stage = GameStage.bid;
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
