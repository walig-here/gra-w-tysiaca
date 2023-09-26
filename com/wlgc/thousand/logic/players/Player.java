package com.wlgc.thousand.logic.players;

import com.wlgc.thousand.logic.Table;
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

    public void takeCards(Table game_table){
        for(int i = 0; i < game_table.getCards().length; i++){
            cards.add(game_table.getCards()[i]);
            game_table.getCards()[i] = null;
        }
    }

    public void layCard(Table game_table, PlayerID player_id, int card_index){
        game_table.putCard(player_id, cards.remove(card_index));
    }

    public void giveCardToPlayer(Player receiver, int card_index){
        receiver.cards.add(cards.remove(card_index));
    }

    //---------------------------------------------------------------------------------

}
