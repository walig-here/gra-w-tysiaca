package com.wlgc.thousand;

import com.wlgc.thousand.logic.Logic;

public class Game {
    
    public static void main(String[] args) {
        Logic logic = Logic.getInstance();
        boolean is_running = true;

        while(is_running){
            is_running = logic.gameTick();
        }
    }

}
