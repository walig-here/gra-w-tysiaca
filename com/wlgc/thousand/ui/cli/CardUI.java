package com.wlgc.thousand.ui.cli;

import com.wlgc.thousand.logic.cards.Color;
import com.wlgc.thousand.logic.cards.Figure;

public class CardUI {
    
    private String card_string;

    public CardUI(Color color, Figure figure){
        card_string = "[";
        switch(color){
            case spades: card_string += "w"; break;
            case clubs: card_string += "ż"; break;
            case diamonds: card_string += "d"; break;
            case hearts: card_string += "c"; break;
        }
        card_string += " ";
        switch(figure){
            case nine: card_string += "9"; break;
            case jack: card_string += "J"; break;
            case queen: card_string += "Q"; break;
            case king: card_string += "K"; break;
            case ten: card_string += "10"; break;
            case ace: card_string += "A"; break;
        }
        card_string += "]";
    }

    public void render(){
        System.out.print(card_string + " ");
    }

}
