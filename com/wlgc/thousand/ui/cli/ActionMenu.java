package com.wlgc.thousand.ui.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.wlgc.thousand.logic.PlayerActions;

public class ActionMenu {
    
    private final String header;
    private final List<MenuRecord> options = new ArrayList<>();
    private final Scanner input_stream;

    public ActionMenu(String desc, Scanner scanner){
        header = desc;
        input_stream = scanner;
    }

    public PlayerActions getUserChoice(){
        int chosen_option = -1;
        
        try {
            chosen_option = input_stream.nextInt();
        } catch (Exception e) {
            return null;
        }

        for(MenuRecord option : options){
            if(option.getId().ordinal() == chosen_option){
                return option.getId();
            }
        }
        
        input_stream.nextLine();  
        return null;
    }

    public void appendOption(PlayerActions id, String desc){
        options.add(new MenuRecord(id, desc));
    }

    public void render(){
        System.out.println("--------------");
        System.out.println(header);
        for(MenuRecord option : options)
            option.render();
        System.out.print(">");
    }

}

class MenuRecord{

    private final String description;
    private final PlayerActions id;

    public MenuRecord(PlayerActions id, String desc){
        description = desc;
        this.id = id;
    }

    public void render(){
        System.out.println("[" + id.ordinal() + "] " + description);
    }

    public PlayerActions getId(){
        return id;
    }

}
