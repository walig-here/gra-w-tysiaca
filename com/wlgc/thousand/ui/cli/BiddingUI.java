package com.wlgc.thousand.ui.cli;

import java.util.Scanner;

import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.logic.PlayerActions;

public class BiddingUI {
    
    private final String header = "Aktualny zwycięzca:";
    private String winner_data;
    private ActionMenu menu;

    public void render(){
        System.out.println("-------------");
        System.out.println(header);
        System.out.println(winner_data);
        menu.render();
    }
    
    public void loadData(Logic logic, Scanner scanner){
        int winner_id = logic.getBiddingWinnerId();
        winner_data = "Gracz " + winner_id + ": " + logic.getPlayers()[winner_id].getBet() + " punktów";
        
        menu = new ActionMenu("Akcje:", scanner);
        if(logic.getPlayers()[0].getBet() >= 0){
            menu.appendOption(PlayerActions.overtrump, "Przebij");
            menu.appendOption(PlayerActions.pass, "Pasuj");
        }
        else{
            menu.appendOption(PlayerActions.next, "Dalej");
        }
    }

    public PlayerActions userAction(){
        return menu.getUserChoice();
    }
}
