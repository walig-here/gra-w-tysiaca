package com.wlgc.thousand.logic;

import java.util.List;
import java.util.Random;

import com.wlgc.thousand.logic.cards.Card;
import com.wlgc.thousand.logic.players.Player;
import com.wlgc.thousand.logic.players.PlayerActions;
import com.wlgc.thousand.logic.players.PlayerID;
import com.wlgc.thousand.logic.stages.GameStage;

public class Logic {

    static private Logic instance;
    private GameStage current_stage;
    private final Player[] players;
    private PlayerID dealer_id;
    private PlayerID leading_player_id; 
    private PlayerID current_player_id; 
    private final Table game_table;
    private PlayerActions pending_action;

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
            case prepare: preparationTurn(); return true;
            case play: gameTurn(); return true;
            case summarize: return summarizeDeal();
        }
        return true;
    }

    public PlayerID getLeadingPlayerID(){
        return leading_player_id;
    }

    public Table getTable(){
        return game_table;
    }

    public Player getPlayer(){
        return players[0];
    }

    public Card[] getCardsFromTable(){
        return game_table.getCards();
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
            if(players[i].getBet() > 0)
                return new PlayerID(i);
        
        return null;
    }

    public PlayerID getCurrentPlayerID(){
        return current_player_id;
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
        // "sprzątnięcie" stołu
        game_table.setActiveReport(null);

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
                case pass: 
                    players[current_player_id.toInt()].pass(); 
                    break;
                case overtrump: 
                    overtrump(current_player_id); 
                    break;
                default: 
                    break;
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
        if(current_player_id.toInt() == leading_player_id.toInt()){
            for (Player player : players)
                if(player.getBet() < 0)
                    player.setBet(0);
            current_stage = GameStage.prepare;
        }
    } 

    private void preparationTurn(){

        // akcje sztucznej inteligencji
        if(current_player_id.isAI()){
            if(players[current_player_id.toInt()].getCards().size() > 9){
                Random rng = new Random(System.currentTimeMillis());
                players[current_player_id.toInt()].giveCardToPlayer(
                    players[current_player_id.getNextPlayerID().toInt()], 
                    rng.nextInt(players[current_player_id.toInt()].getCards().size())
                );
            }
            else if(players[current_player_id.toInt()].getCards().size() > 8){
                Random rng = new Random(System.currentTimeMillis());
                players[current_player_id.toInt()].giveCardToPlayer(
                    players[current_player_id.getNextPlayerID().getNextPlayerID().toInt()], 
                    rng.nextInt(players[current_player_id.toInt()].getCards().size())
                );
            }
            else if(game_table.getCards()[0] != null){
                players[current_player_id.toInt()].takeCards(game_table);
            }
            else{
                Random rng = new Random(System.currentTimeMillis());
                if(rng.nextInt(100) < 50)
                    current_stage = GameStage.play;
                else
                    overtrump(current_player_id);
            }

            if(current_player_id.toInt() == 1)
                pending_action = PlayerActions.wait_for_bot_1;
            else
                pending_action = PlayerActions.wait_for_bot_2;
        }
        // akcje gracza
        else{
            switch(pending_action){
                case take_cards: 
                    getPlayer().takeCards(game_table);
                    break;
                case lay_card_0:
                    getPlayer().giveCardToPlayer(getPlayer().getCards().size() > 9 ? players[1] : players[2], 0);
                    break;
                case lay_card_1:
                    getPlayer().giveCardToPlayer(getPlayer().getCards().size() > 9 ? players[1] : players[2], 1);
                case lay_card_2:
                    getPlayer().giveCardToPlayer(getPlayer().getCards().size() > 9 ? players[1] : players[2], 2);
                    break;
                case lay_card_3:
                    getPlayer().giveCardToPlayer(getPlayer().getCards().size() > 9 ? players[1] : players[2], 3);
                    break;
                case lay_card_4:
                    getPlayer().giveCardToPlayer(getPlayer().getCards().size() > 9 ? players[1] : players[2], 4);
                    break;
                case lay_card_5:
                    getPlayer().giveCardToPlayer(getPlayer().getCards().size() > 9 ? players[1] : players[2], 5);
                    break;
                case lay_card_6:
                    getPlayer().giveCardToPlayer(getPlayer().getCards().size() > 9 ? players[1] : players[2], 6);
                    break;
                case lay_card_7:
                    getPlayer().giveCardToPlayer(getPlayer().getCards().size() > 9 ? players[1] : players[2], 7);
                    break;
                case lay_card_8:
                    getPlayer().giveCardToPlayer(getPlayer().getCards().size() > 9 ? players[1] : players[2], 8);
                    break;
                case lay_card_9:
                    getPlayer().giveCardToPlayer(getPlayer().getCards().size() > 8 ? players[1] : players[2], 9);
                    break;
                case overtrump:
                    overtrump(current_player_id);
                    break;
                case pass:
                    current_stage = GameStage.play;
                    break;
                default:
                    break;
            }
        }
    }

    private void gameTurn(){
        
        // o ile wszyscy się wyłożyli następuje rozstrzygnięcie
        if(game_table.allPlayersHaveLayedCards()){
            leading_player_id = game_table.clinch(leading_player_id);
            players[leading_player_id.toInt()].gatherPoints(game_table);
            current_player_id.set(leading_player_id.toInt());
            return;
        }

        // SI wybiera akcję
        if(current_player_id.isAI()){
            List<PlayerActions> permitted_actions = players[current_player_id.toInt()].getPermittedActions(game_table, leading_player_id, current_player_id);
            Random rng = new Random(System.currentTimeMillis());
            if(permitted_actions.size() > 1)
                pending_action = permitted_actions.get(rng.nextInt(permitted_actions.size()-1));
            else
                pending_action = permitted_actions.get(0);
        }

        // wykonanie akcji
        switch(pending_action){
            case lay_card_0:
                players[current_player_id.toInt()].layCard(game_table, current_player_id, 0);
                break;
            case lay_card_1:
                players[current_player_id.toInt()].layCard(game_table, current_player_id, 1);
                break;
            case lay_card_2:
                players[current_player_id.toInt()].layCard(game_table, current_player_id, 2);
                break;
            case lay_card_3:
                players[current_player_id.toInt()].layCard(game_table, current_player_id, 3);
                break;
            case lay_card_4:
                players[current_player_id.toInt()].layCard(game_table, current_player_id, 4);
                break;
            case lay_card_5:
                players[current_player_id.toInt()].layCard(game_table, current_player_id, 5);
                break;
            case lay_card_6:
                players[current_player_id.toInt()].layCard(game_table, current_player_id, 6);
                break;
            case lay_card_7:
                players[current_player_id.toInt()].layCard(game_table, current_player_id, 7);
                break;
            case report_40:
                players[current_player_id.toInt()].report(game_table, current_player_id, pending_action);
                break;
            case report_60:
                players[current_player_id.toInt()].report(game_table, current_player_id, pending_action);
                break;
            case report_80:
                players[current_player_id.toInt()].report(game_table, current_player_id, pending_action);
                break;
            case report_100:
                players[current_player_id.toInt()].report(game_table, current_player_id, pending_action);
                break;
            default:
                break;
        }

        // wykłada się kolejny gracz
        current_player_id.setToNextPlayerId();

        // jeżeli gracz nie majuż kart na ręce, to zakończ rozdanie
        if(players[current_player_id.toInt()].getCards().isEmpty())
            current_stage = GameStage.summarize;
    }

    private boolean summarizeDeal(){
        for (Player player : players){
            player.summarizeTurn();
            if(player.hasWonTheGame())
                return false;
        }
        current_stage = GameStage.deal;
        return true;
    }

    private void overtrump(PlayerID overtrumper_id){
        players[overtrumper_id.toInt()].setBet(players[leading_player_id.toInt()].getBet()+10);
        leading_player_id.set(overtrumper_id.toInt());
    }
}
