package com.wlgc.thousand.logic.players;

import com.wlgc.thousand.logic.Table;
import com.wlgc.thousand.logic.cards.Card;
import com.wlgc.thousand.logic.cards.Color;
import com.wlgc.thousand.logic.cards.Figure;

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

    public boolean hasWonTheGame(){
        return game_score >= 1000;
    }

    public void summarizeTurn(){
        if(turn_score >= bet)
            game_score += turn_score;
        else
            game_score -= bet;
        turn_score = 0;
        bet = 0;
    }

    public void takeCards(Table game_table){
        for(int i = 0; i < game_table.getCards().length; i++){
            cards.add(game_table.getCards()[i]);
            game_table.getCards()[i] = null;
        }
    }

    public void gatherPoints(Table game_table){
        for(int i = 0; i < game_table.getCards().length; i++){
            switch(game_table.getCards()[i].figure){
                case jack:
                    turn_score += 2;
                    break;
                case queen:
                    turn_score += 3;
                    break;
                case king:
                    turn_score += 4;
                    break;
                case ten:
                    turn_score += 10;
                    break;
                case ace:
                    turn_score += 11;
                    break;
                default:
                    break;
            }
            game_table.getCards()[i] = null;
        }
    }

    public void layCard(Table game_table, PlayerID player_id, int card_index){
        game_table.putCard(player_id, cards.remove(card_index));
    }

    public void report(Table game_table, PlayerID player_id, PlayerActions report_type){
        switch(report_type){
            case report_100:
                game_table.setActiveReport(Color.hearts);
                layCard(game_table, player_id, cards.indexOf(new Card(Color.hearts, Figure.queen)));
                turn_score += 100;
                break;
            case report_80:
                game_table.setActiveReport(Color.diamonds);
                layCard(game_table, player_id, cards.indexOf(new Card(Color.diamonds, Figure.queen)));
                turn_score += 80;
                break;
            case report_60:
                game_table.setActiveReport(Color.clubs);
                layCard(game_table, player_id, cards.indexOf(new Card(Color.clubs, Figure.queen)));
                turn_score += 60;
                break;
            case report_40:
                game_table.setActiveReport(Color.spades);
                layCard(game_table, player_id, cards.indexOf(new Card(Color.spades, Figure.queen)));
                turn_score += 40;
                break;
            default:
                break;
        }
    }

    public void giveCardToPlayer(Player receiver, int card_index){
        receiver.cards.add(cards.remove(card_index));
    }

    // zwraca listę akcji, które gracz może aktualnie wykonać
    public List<PlayerActions> getPermittedActions(Table game_table, PlayerID leading_player_id, PlayerID this_id){
        List<PlayerActions> permitted_actions = new ArrayList<>();
        
        // Jeżeli to ten gracz ma inicjatywę, to może położyć dowolną karte jak i medlować
        if(leading_player_id.toInt() == this_id.toInt()){
            for(int i = 0; i < cards.size(); i++)
                permitted_actions.add(PlayerActions.values()[PlayerActions.lay_card_0.ordinal()+i]);
            
            if(cards.contains(new Card(Color.spades, Figure.queen)) && cards.contains(new Card(Color.spades, Figure.king)))
                permitted_actions.add(PlayerActions.report_40);
            if(cards.contains(new Card(Color.clubs, Figure.queen)) && cards.contains(new Card(Color.clubs, Figure.king)))
                permitted_actions.add(PlayerActions.report_60);
            if(cards.contains(new Card(Color.diamonds, Figure.queen)) && cards.contains(new Card(Color.diamonds, Figure.king)))
                permitted_actions.add(PlayerActions.report_80);
            if(cards.contains(new Card(Color.hearts, Figure.queen)) && cards.contains(new Card(Color.hearts, Figure.king)))
                permitted_actions.add(PlayerActions.report_100);
        }
        // Jeżeli nie, to należy kłaść do koloru, wykładać trumfy lub w ostateczności dowolną kartę
        else{
            for(int i = 0; i < cards.size(); i++)
                if(cards.get(i).color.ordinal() == game_table.getCards()[leading_player_id.toInt()].color.ordinal())
                    permitted_actions.add(PlayerActions.values()[PlayerActions.lay_card_0.ordinal() + i]);
            
            if(permitted_actions.isEmpty() && game_table.getActiveReport() != null)
                for(int i = 0; i < cards.size(); i++)
                    if(cards.get(i).color.ordinal() == game_table.getActiveReport().ordinal())
                        permitted_actions.add(PlayerActions.values()[PlayerActions.lay_card_0.ordinal() + i]);

            if(permitted_actions.isEmpty())
                for(int i = 0; i < cards.size(); i++)
                    permitted_actions.add(PlayerActions.values()[PlayerActions.lay_card_0.ordinal()+i]);
        }

        return permitted_actions;
    }

    //---------------------------------------------------------------------------------

}
