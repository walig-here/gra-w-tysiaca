package com.wlgc.thousand.logic;

import java.util.List;
import java.util.Random;

import com.wlgc.thousand.logic.cards.Card;
import com.wlgc.thousand.logic.players.Player;
import com.wlgc.thousand.logic.players.PlayerActions;
import com.wlgc.thousand.logic.players.PlayerID;
import com.wlgc.thousand.logic.stages.GameStage;

public class Logic {
    public static Logic getInstance() {
        if(instance == null)
            instance = new Logic();
        return instance;
    }

    public boolean gameTick(){
        switch(current_stage){
            case init: current_stage = GameStage.deal; return true;
            case deal: deal(); return true;
            case bid: bidTurn(); return true;
            case play: gameTurn(); return true;
            case summarize: return summarizeDeal();
        }
        return true;
    }

    public Player[] getPlayers(){
        return players;
    }

    public GameStage getCurrentStage(){
        return current_stage;
    }

    public PlayerID getBiddingWinnerId(){
        if(current_stage == GameStage.bid)
            return leading_player_id;
        
        for(int i = 0; i < players.length; i++)
            if(players[i].getBet() >= 0)
                return new PlayerID(i);
        
        return null;
    }

    public PlayerActions getPendingAction(){
        return pending_action;
    }

    public void setPendingAction(PlayerActions action){
        pending_action = action;
    }

    public List<Card> getUserCards(){
        return players[0].getCards();
    }

    static private Logic instance;
    private GameStage current_stage;
    private final Player[] players;
    private PlayerID dealer_id;
    private PlayerID leading_player_id; 
    private PlayerID current_player_id; 
    private final Table game_table;
    private PlayerActions pending_action;
    
    private Logic(){
        players = new Player[3];
        for(int i = 0; i < players.length; i++)
            players[i] = new Player();

        // ustalenie rozającego tak, aby jako pierwszy na musiku był gracz
        dealer_id = new PlayerID(1);
        pending_action = PlayerActions.next;

        leading_player_id = new PlayerID(0);
        current_player_id = new PlayerID(0);

        current_stage = GameStage.init;
        game_table = Table.getInstance();
    }


    private void deal(){
        // wyłonienie rozdającego
        dealer_id.setToNextPlayerId();

        // rozdanie kart
        Card.shuffle();
        for(int i = 0; i < players.length; i++){
            for(int j = 0; j < 7; j++)
                players[i].getCards().add(Card.drawCard());
            game_table.putCard(new PlayerID(i), Card.drawCard());
        }

        // kolejny gracz od rozadjącego jest na musiku
        leading_player_id.set(dealer_id.getNextPlayerID().toInt());
        players[leading_player_id.toInt()].setBet(100);
        
        // pierwszwy ruch w licytacji ma kolejny gracz po musiku
        current_player_id.set(leading_player_id.getNextPlayerID().toInt());

        // przejście do licytacji
        current_stage = GameStage.bid;
    }


    private void bidTurn(){
        // akcje gracza
        if(!current_player_id.isAI()){
            switch(pending_action){
                case pass: players[current_player_id.toInt()].pass(); break;
                case overtrump: overtrump(current_player_id); break;
                default: break;
            }

            // kolejną turę nalezy przeczekać bezczynnie
            pending_action = PlayerActions.wait_for_bot_1;
        }
        // akcje ai
        else{
            Random rng = new Random(System.currentTimeMillis());
            if(players[current_player_id.toInt()].getBet() >= 0 && rng.nextInt(1000) <= 500)
                overtrump(current_player_id);
            else
                players[current_player_id.toInt()].pass();

            if(current_player_id.toInt() == 1)
                pending_action = PlayerActions.wait_for_bot_2;
            else 
                pending_action = PlayerActions.next;
        }

        // następny gracz
        current_player_id.setToNextPlayerId();;

        // gracz, który po przejściu całej kolejki pozostał liderem, zwycięża licytację
        if(current_player_id == leading_player_id){
            for (Player player : players)
                if(player.getBet() < 0)
                    player.setBet(0);
            current_stage = GameStage.play;
        }
    } 

    private void overtrump(PlayerID player_id){
        players[player_id.toInt()].setBet(players[leading_player_id.toInt()].getBet()+10);
        leading_player_id.set(player_id.toInt());
    }

    private void gameTurn(){
        
    }

    private boolean summarizeDeal(){
        return false;
    }
}
