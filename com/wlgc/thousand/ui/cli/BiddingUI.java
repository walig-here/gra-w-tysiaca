package com.wlgc.thousand.ui.cli;

import java.util.Scanner;

import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.logic.players.PlayerActions;

public class BiddingUI {
    
    private final String header = "Stan licytacji:";
    private final String[] player_data = new String[3];
    private ActionMenu menu;

    public void render(){
        System.out.println("-------------");
        System.out.println(header);
        for(int i = 0; i < player_data.length; i++)
            System.out.println(player_data[i]);
        menu.render();
    }
    
    public void loadData(Logic logic, Scanner scanner){
        int bet;
        for(int i = 0; i < player_data.length; i++){
            if(i == 0)
                player_data[i] = "Ty\t\t";
            else
                player_data[i] = "Gracz " + i + "\t\t";
            
            bet =  logic.getPlayers()[i].getBet();

            if(bet < 0)
                player_data[i] += "PASS";
            else
                player_data[i] += "Zakład:" + bet + "\t\t";

            if(i == logic.getBiddingWinnerId().toInt())
                player_data[i] += "LIDER";
        }
        
        if(logic.getPendingAction() == PlayerActions.wait_for_bot_1){
            menu = new ActionMenu("Licytuje Gracz 1", scanner);
            return;
        }
        else if(logic.getPendingAction() == PlayerActions.wait_for_bot_2){
            menu = new ActionMenu("Licytuje Gracz 2", scanner);
            return;
        }

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
