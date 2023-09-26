package com.wlgc.thousand.ui.cli;

import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.ui.Scene;

public class TableUI {
    
    private final String header = "Stół:";
    private final CardUI[] cards = new CardUI[3];

    public void loadData(Logic logic, Scene current_scene){
        boolean hide_cards;
        switch(current_scene){
            case bidding: 
                hide_cards = true; 
                break;
            case preparations: 
                hide_cards = logic.getBiddingWinnerId().isAI() && logic.getPlayers()[logic.getBiddingWinnerId().toInt()].getBet() == 100;
                break;
            default: 
                hide_cards = false; 
                break;
        }

        for(int i = 0; i < logic.getCardsFromTable().length; i++){
            if(hide_cards)
                cards[i] = new CardUI(null, null);
            else if(logic.getCardsFromTable()[i] == null)
                cards[i] = null;
            else
                cards[i] = new CardUI(logic.getCardsFromTable()[i].color, logic.getCardsFromTable()[i].figure);
        }
    }

    public void render(){
        System.out.println("-------------");
        System.out.println(header);
        for(CardUI card : cards){
            if(card == null)
                System.out.print("_____ ");
            else
                card.render();
        }
        System.out.println();
    }

}
