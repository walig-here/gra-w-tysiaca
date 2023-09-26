package com.wlgc.thousand;

import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.logic.PlayerActions;
import com.wlgc.thousand.ui.UserInterface;
import com.wlgc.thousand.ui.cli.ConsoleUI;

public class Game {
    
    public static void main(String[] args) {
        Logic logic = Logic.getInstance();
        UserInterface ui = new ConsoleUI();
        boolean is_running = true;

        while(is_running){
            // logika gry
            is_running = logic.gameTick();

            // załadowanie danych z logiki do UI
            ui.loadData(logic);

            // wyrenderowanie UI oraz akcje użytkownika
            do {
                ui.render();
                if(logic.getPendingAction().ordinal() < PlayerActions.wait_for_bot_1.ordinal())
                    logic.setPendingAction(ui.userAction());
            } while (logic.getPendingAction() == null);
        }
    }

}
