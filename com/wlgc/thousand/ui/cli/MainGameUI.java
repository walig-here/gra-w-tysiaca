package com.wlgc.thousand.ui.cli;

import java.util.List;
import java.util.Scanner;

import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.logic.players.PlayerActions;
import com.wlgc.thousand.logic.players.PlayerID;

public class MainGameUI {
    
    private ActionMenu menu;

    public void render(){
        menu.render();
    }

    public PlayerActions userAction(){
        return menu.getUserChoice();
    }

    public void loadData(Logic logic, Scanner input_stream){
        if(logic.getTable().allPlayersHaveLayedCards()){
            menu = new ActionMenu("Rozstrzygnięcie rozadania gracza " + logic.getLeadingPlayerID().toInt(), input_stream);
            menu.appendOption(PlayerActions.next, "Dalej");
            return;
        }

        if(logic.getCurrentPlayerID().isAI()){
            menu = new ActionMenu("Tura gracza " + logic.getCurrentPlayerID().toInt(), input_stream);
            menu.appendOption(PlayerActions.next, "Dalej");
            return;
        }

        menu = new ActionMenu("Akcje:", input_stream);
        List<PlayerActions> permitted_actions = logic.getPlayer().getPermittedActions(logic.getTable(), logic.getLeadingPlayerID(), new PlayerID(0));
        for(PlayerActions action : permitted_actions){
            switch(action){
                case lay_card_0:
                    menu.appendOption(action, "Wyłóż kartę (0) na stół");
                    break;
                case lay_card_1:
                    menu.appendOption(action, "Wyłóż kartę (1) na stół");
                    break;
                case lay_card_2:
                    menu.appendOption(action, "Wyłóż kartę (2) na stół");
                    break;
                case lay_card_3:
                    menu.appendOption(action, "Wyłóż kartę (3) na stół");
                    break;
                case lay_card_4:
                    menu.appendOption(action, "Wyłóż kartę (4) na stół");
                    break;
                case lay_card_5:
                    menu.appendOption(action, "Wyłóż kartę (5) na stół");
                    break;
                case lay_card_6:
                    menu.appendOption(action, "Wyłóż kartę (6) na stół");
                    break;
                case lay_card_7:
                    menu.appendOption(action, "Wyłóż kartę (7) na stół");
                    break;
                case report_40:
                    menu.appendOption(action, "Zamelduj 40 punktów");
                    break;
                case report_60:
                    menu.appendOption(action, "Zamelduj 60 punktów");
                    break;
                case report_80:
                    menu.appendOption(action, "Zamelduj 80 punktów");
                    break;
                case report_100:
                    menu.appendOption(action, "Zamelduj 100 punktów");
                    break;
                default:
                    break;
            }
        }
    }

}
