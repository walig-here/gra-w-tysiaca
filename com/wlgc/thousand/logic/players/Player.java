package com.wlgc.thousand.logic.players;

import com.wlgc.thousand.logic.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {

    //---------------------------------------------------------------------------------
    /* ### POLA ### */

    private int turn_score;        
    private int game_score;             
    private int bet = 0;       
    private final List<Card> cards;      

    //---------------------------------------------------------------------------------
    /* ## METODY ### */

    public Player(){
        turn_score = 0;
        game_score = 0;
        bet = 0;
        cards = new ArrayList<>();  
    }

    public List<Card> getCards(){
        return cards;
    }

    public int getTurnScore(){
        return turn_score;
    }

    public int getGameScore(){
        return game_score;
    }

    public int getBet(){
        return bet;
    }

    public void setBet(int bet){
        this.bet = bet;
    }

    public void pass(){
        bet = -1;
    }

    //---------------------------------------------------------------------------------

}
