package com.wlgc.thousand.ui.cli;

import java.util.ArrayList;
import java.util.List;

import com.wlgc.thousand.logic.cards.Card;

public class HandUI {
    
    private final String header = "Ręka:";
    private final List<CardUI> cards = new ArrayList<>();

    public HandUI(List<Card> cards_data){
        for(Card card : cards_data)
            cards.add(new CardUI(card.color, card.figure));
    }

    public void render(){
        System.out.println("-------------");
        System.out.println(header);
        for(CardUI card : cards)
            card.render();
        System.out.println();
    }

}
