package com.wlgc.thousand.logic;


import com.wlgc.thousand.logic.cards.Card;
import com.wlgc.thousand.logic.cards.Color;
import com.wlgc.thousand.logic.players.PlayerID;

public class Table {
    
    private static Table instance;
    private final Card[] cards = new Card[3];
    private Color active_report = null;

    public static Table getInstance(){
        if(instance == null)
            instance = new Table();
        return instance;
    }

    public Color getActiveReport(){
        return active_report;
    }

    public void setActiveReport(Color new_report){
        active_report = new_report;
    }

    public Card[] getCards(){
        return cards;
    }

    // sprawdzenie, czy wszyscy gracze się wyłożyli
    public boolean allPlayersHaveLayedCards(){
        for(Card card_on_table : cards)
            if(card_on_table == null)
                return false;
        return true;
    }

    // wyłonienie zwycięzcy
    public PlayerID clinch(PlayerID leading_player_id){
        Color leading_card_color = cards[leading_player_id.toInt()].color; 
        int[] battle_score = new int[3];

        for(int i = 0; i < battle_score.length; i++){
            battle_score[i] = 0;
            if(cards[i].color.ordinal() != leading_card_color.ordinal() && cards[i].color.ordinal() == leading_card_color.ordinal())
                battle_score[i] += 100;
            else if (cards[i].color.ordinal() != leading_card_color.ordinal())
                continue;
            battle_score[i] += cards[i].figure.ordinal();
        }   

        PlayerID winner_id = new PlayerID(0);
        if(battle_score[winner_id.toInt()] < battle_score[1]) 
            winner_id.set(1);
        if(battle_score[winner_id.toInt()] < battle_score[2])
            winner_id.set(2);
        return winner_id;
    }

    // wyłożenie karty na stół
    public void putCard(PlayerID player_id, Card card){
        cards[player_id.toInt()] = card;
    }

}
