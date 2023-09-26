package com.wlgc.thousand;

import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.logic.players.PlayerActions;
import com.wlgc.thousand.ui.UserInterface;
import com.wlgc.thousand.ui.cli.CLI;

public class Game {
    
    public static void main(String[] args) {
        Game game = Game.getInstance();
        game.run();
    }

    private Logic logic;
    private UserInterface ui;
    private static Game instance;

    public void run(){
        gameplayLoop();
    }

    private Game(){
        logic = Logic.getInstance();
        ui = CLI.getInstance();
    }

    private void gameplayLoop(){
        boolean is_running = true;
        while(is_running){
            // logika gry
            is_running = logic.gameTick();

            // wyrenderowanie zaktualizowanego UI oraz oczekiwanie na akcje użytkownika
            ui.loadData(logic);
            do {
                ui.render();
                logic.setPendingAction(ui.userAction());
            } while (logic.getPendingAction() == null);
        }
    }

    public static Game getInstance(){
        if(instance == null)
            instance = new Game();
        return instance;
    }

}
