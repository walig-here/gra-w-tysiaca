package com.wlgc.thousand.logic;

import com.wlgc.thousand.logic.cards.Card;
import com.wlgc.thousand.logic.players.PlayerID;

public class Table {
    
    private static Table instance;
    private final Card[] cards = new Card[3];

    public static Table getInstance(){
        if(instance == null)
            instance = new Table();
        return instance;
    }

    // wyłonienie zwycięzcy
    public int clinch(){
        return 0;
    } 

    // wyłożenie karty na stół
    public void putCard(PlayerID player_id, Card card){
        cards[player_id.toInt()] = card;
    }

}
