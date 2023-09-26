package com.wlgc.thousand.ui.cli;

import java.util.Scanner;

import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.logic.players.PlayerActions;
import com.wlgc.thousand.ui.Scene;
import com.wlgc.thousand.ui.UserInterface;

public class CLI extends UserInterface {

    private final PointsUI pointsUI = new PointsUI();
    private final BiddingUI biddingUI = new BiddingUI();
    private final HandUI handUI = new HandUI();
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
        System.out.println("\n\n\n\n\n");
        System.out.println("LICYTACJA");
        pointsUI.render();
        handUI.render();
        biddingUI.render();
    }

    @Override
    protected void renderMainGameplay() {
        System.out.println("\n\n\n\n\n");
        System.out.println("ROZGRYWKA");
        pointsUI.render();
        handUI.render();
    }

    @Override
    protected void renderDealing() {
        System.out.println("\n\n\n\n\n");
        System.out.println("ROZDAWANIE");
    }

    @Override
    protected void renderSummary() {
    }

    @Override
    public void loadData(Logic logic) {
        setScene(logic.getCurrentStage());
        pointsUI.loadData(logic.getPlayers(), scene);
        handUI.loadData(logic.getUserCards());
        if(scene == Scene.bidding)
            biddingUI.loadData(logic, input_stream);
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
        return PlayerActions.next;
    }

    @Override
    protected PlayerActions actionsSummary() {
        return PlayerActions.next;
    }

    private CLI(){
        
    }

}
