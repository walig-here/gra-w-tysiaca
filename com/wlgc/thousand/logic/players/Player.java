package com.wlgc.thousand.logic.players;

import com.wlgc.thousand.logic.cards.Card;

import java.util.ArrayList;
import java.util.List;

public class Player {
    
    private int turn_score = 0;        
    private int game_score = 0;             
    private int bet = 0;       
    private final List<Card> cards = new ArrayList<>();       

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

}
