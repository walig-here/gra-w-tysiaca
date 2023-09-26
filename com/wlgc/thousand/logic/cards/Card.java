package com.wlgc.thousand.logic.cards;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Card {

    //---------------------------------------------------------------------------------
    /* ### POLA ### */

    public final Figure figure;
    public final Color color;
    private final static List<Card> pack = new ArrayList<>();

    //---------------------------------------------------------------------------------
    /* ### METODY ### */

    public static Card drawCard(){
        int drawn_card_index = 0;
        if(pack.size() > 1){
            Random card_picker = new Random(System.currentTimeMillis());
            drawn_card_index = card_picker.nextInt(pack.size()-1);
        }
        Card drawn_card = new Card(pack.get(drawn_card_index).color, pack.get(drawn_card_index).figure);
        pack.remove(drawn_card_index);
        return drawn_card;
    }

    public static void shuffle(){
        if(!pack.isEmpty())
            pack.clear();
        for(int color = 0; color <= Color.hearts.ordinal(); color++)
            for(int figure = 0; figure <= Figure.ace.ordinal(); figure++)
                pack.add(new Card(Color.values()[color], Figure.values()[figure]));
    }

    @Override
    public boolean equals(Object o){
        if(!(o instanceof Card))
            return false;
        return ((Card)o).color.ordinal() == this.color.ordinal() && ((Card)o).figure.ordinal() == this.figure.ordinal();
    }

    public Card(Color color, Figure figure){
        this.color = color;
        this.figure = figure;
    }

    //---------------------------------------------------------------------------------
}
