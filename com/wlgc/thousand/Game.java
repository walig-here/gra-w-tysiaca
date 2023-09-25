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
        PlayerActions pending_action = null;

        while(is_running){
            is_running = logic.gameTick();
            ui.loadData(logic);
            do {
                ui.render();
                pending_action = ui.userAction();
            } while (pending_action == null);
        }
    }

}
