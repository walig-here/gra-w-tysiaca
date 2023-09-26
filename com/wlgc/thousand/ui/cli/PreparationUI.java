package com.wlgc.thousand.ui.cli;

import java.util.Scanner;

import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.logic.players.PlayerActions;

public class PreparationUI {

    private ActionMenu menu;

    public void loadData(Logic logic, Scanner input_stream){
        if(logic.getBiddingWinnerId().isAI()){
            menu = new ActionMenu("Tura gracza " + logic.getBiddingWinnerId().toInt(), input_stream);
            menu.appendOption(PlayerActions.next, "Dalej");
            return;
        }
        
        menu = new ActionMenu("Akcje:", input_stream);

        // gracz wygrał musi wyrzucić karty
        if(logic.getPlayer().getCards().size() > 9){
            menu.appendOption(PlayerActions.lay_card_0, "Wydaj kartę (0) graczowi 1");
            menu.appendOption(PlayerActions.lay_card_1, "Wydaj kartę (1) graczowi 1");
            menu.appendOption(PlayerActions.lay_card_2, "Wydaj kartę (2) graczowi 1");
            menu.appendOption(PlayerActions.lay_card_3, "Wydaj kartę (3) graczowi 1");
            menu.appendOption(PlayerActions.lay_card_4, "Wydaj kartę (4) graczowi 1");
            menu.appendOption(PlayerActions.lay_card_5, "Wydaj kartę (5) graczowi 1");
            menu.appendOption(PlayerActions.lay_card_6, "Wydaj kartę (6) graczowi 1");
            menu.appendOption(PlayerActions.lay_card_7, "Wydaj kartę (7) graczowi 1");
            menu.appendOption(PlayerActions.lay_card_8, "Wydaj kartę (8) graczowi 1");
            menu.appendOption(PlayerActions.lay_card_9, "Wydaj kartę (9) graczowi 1");
        }
        else if(logic.getPlayer().getCards().size() > 8){
            menu.appendOption(PlayerActions.lay_card_0, "Wydaj kartę (0) graczowi 2");
            menu.appendOption(PlayerActions.lay_card_1, "Wydaj kartę (1) graczowi 2");
            menu.appendOption(PlayerActions.lay_card_2, "Wydaj kartę (2) graczowi 2");
            menu.appendOption(PlayerActions.lay_card_3, "Wydaj kartę (3) graczowi 2");
            menu.appendOption(PlayerActions.lay_card_4, "Wydaj kartę (4) graczowi 2");
            menu.appendOption(PlayerActions.lay_card_5, "Wydaj kartę (5) graczowi 2");
            menu.appendOption(PlayerActions.lay_card_6, "Wydaj kartę (6) graczowi 2");
            menu.appendOption(PlayerActions.lay_card_7, "Wydaj kartę (7) graczowi 2");
            menu.appendOption(PlayerActions.lay_card_8, "Wydaj kartę (8) graczowi 2");
        }
        // gracz wygrał, ale jeszcze nie zebrał kart ze stołu
        else if(logic.getCardsFromTable()[0] != null){
            menu.appendOption(PlayerActions.take_cards, "Zbierz musik");
        }
        // gracz już rozdał karty i musi ustalić ostateczny zakład
        else{
            menu.appendOption(PlayerActions.overtrump, "Podbij zakład");
            menu.appendOption(PlayerActions.pass, "Rozpocznij grę");
        }
    }

    public void render(){
        menu.render();
    }

    public PlayerActions userAction(){
        return menu.getUserChoice();
    }

}
