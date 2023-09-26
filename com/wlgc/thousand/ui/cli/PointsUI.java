package com.wlgc.thousand.ui.cli;

import com.wlgc.thousand.logic.players.Player;
import com.wlgc.thousand.ui.Scene;

public class PointsUI {
    
    final String header = "Punktacja:";
    final String[] player_data = new String[3];

    public void loadData(Player[] players, Scene current_scene){
        for(int i = 0; i < players.length; i++){
            if(i == 0)
                player_data[i] = "Ty\t\t";
            else
                player_data[i] = "Gracz " + i + "\t\t";
            switch(current_scene){
                case bidding: 
                    loadBiddingData(players[i], i); 
                    break;
                case preparations: 
                    loadMainGameData(players[i], i);
                    break;
                case main_gameplay: 
                    loadMainGameData(players[i], i); 
                    break;
                case summary: 
                    loadSummaryData(players[i], i);
                    break;
                default: 
                    break;
            }
        }
    }

    public void render(){
        System.out.println("-------------");
        System.out.println(header);
        for(int i = 0; i < player_data.length; i++)
            System.out.println(player_data[i]);
    }

    private void loadBiddingData(Player player, int player_id){
        player_data[player_id] += "Wynik: " + player.getGameScore();
    }

    private void loadMainGameData(Player player, int player_id){
        if(player.getBet() > 0)
            player_data[player_id] += "Tura: " + player.getTurnScore() + "/" + player.getBet() + "\t\t";
        else
            player_data[player_id] += "Tura: " + player.getTurnScore() + "  \t\t";
        player_data[player_id] += "Wynik: " + player.getGameScore();
    }

    private void loadSummaryData(Player player, int player_id){
        player_data[player_id] += "Wynik: " + player.getGameScore() + "\t\t";
        player_data[player_id] += "Zmiana: " + player.getTurnScore();
    }

}
