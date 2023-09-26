package com.wlgc.thousand.ui.cli;

import java.util.Scanner;

import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.logic.players.PlayerActions;
import com.wlgc.thousand.ui.UserInterface;

public class CLI extends UserInterface {

    private final PointsUI pointsUI = new PointsUI();
    private final BiddingUI biddingUI = new BiddingUI();
    private final HandUI handUI = new HandUI();
    private final TableUI tableUI = new TableUI();
    private final MainGameUI mainGameUI = new MainGameUI();
    private final PreparationUI preparationUI = new PreparationUI();
    private static Scanner input_stream = new Scanner(System.in);
    private static CLI instance;

    public static CLI getInstance(){
        if(instance == null)
            instance = new CLI();
        return instance;
    }

    @Override
    protected void renderMainMenu() {

    }

    @Override
    protected void renderBidding() {
        for(int i = 0; i < 100; i++)
            System.out.println();
        System.out.println("LICYTACJA");
        pointsUI.render();
        tableUI.render();
        handUI.render();
        biddingUI.render();
    }

    @Override
    protected void renderPreparation() {
        for(int i = 0; i < 100; i++)
            System.out.println();
        System.out.println("PRZYGOTOWANIA");
        pointsUI.render();
        tableUI.render();
        handUI.render();
        preparationUI.render();
    }

    @Override
    protected void renderMainGameplay() {
        for(int i = 0; i < 100; i++)
            System.out.println();
        System.out.println("ROZGRYWKA");
        pointsUI.render();
        tableUI.render();
        handUI.render();
        mainGameUI.render();
    }

    @Override
    protected void renderDealing() {
        for(int i = 0; i < 100; i++)
            System.out.println();
        System.out.println("ROZDAWANIE");
    }

    @Override
    protected void renderSummary() {
        for(int i = 0; i < 100; i++)
            System.out.println();
        System.out.println("PODSUMOWANIE");
        pointsUI.render();
    }

    @Override
    public void loadData(Logic logic) {
        setScene(logic.getCurrentStage());
        pointsUI.loadData(logic.getPlayers(), scene);

        switch(scene){
            case bidding:
                biddingUI.loadData(logic, input_stream);
                tableUI.loadData(logic, scene);
                handUI.loadData(logic.getUserCards());
                break;
            case preparations:
                tableUI.loadData(logic, scene);
                preparationUI.loadData(logic, input_stream);
                handUI.loadData(logic.getUserCards());
                break;
            case main_gameplay:
                tableUI.loadData(logic, scene);
                mainGameUI.loadData(logic, input_stream);
                handUI.loadData(logic.getUserCards());
            default: 
                break;
        }
    }

    @Override
    protected PlayerActions actionsMainMenu() {
        return PlayerActions.next;
    }

    @Override
    protected PlayerActions actionsBidding() {
        return biddingUI.userAction();
    }

    @Override
    protected PlayerActions actionsGameplay() {
        return mainGameUI.userAction();
    }

    @Override
    protected PlayerActions actionsSummary() {
        return PlayerActions.next;
    }

    @Override
    protected PlayerActions actionPreparation() {
        return preparationUI.userAction();
    }

    private CLI(){
        
    }

}
