package com.wlgc.thousand.logic;

import com.wlgc.thousand.logic.cards.Card;

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
    public void putCard(int player_id){
        
    }

}
