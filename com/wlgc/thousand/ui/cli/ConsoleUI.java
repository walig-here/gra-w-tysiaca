package com.wlgc.thousand.ui.cli;

import java.util.Scanner;

import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.logic.PlayerActions;
import com.wlgc.thousand.ui.Scene;
import com.wlgc.thousand.ui.UserInterface;

public class ConsoleUI extends UserInterface {

    private final PointsUI pointsUI = new PointsUI();
    private final BiddingUI biddingUI = new BiddingUI();
    private static Scanner input = new Scanner(System.in);

    @Override
    protected void renderMainMenu() {

    }

    @Override
    protected void renderBidding() {
        System.out.println("\n\n\n\n\n");
        System.out.println("LICYTACJA");
        pointsUI.render();
        biddingUI.render();
    }

    @Override
    protected void renderMainGameplay() {
        System.out.println("\n\n\n\n\n");
        System.out.println("ROZGRYWKA");
        pointsUI.render();
    }

    @Override
    protected void renderSummary() {
    }

    @Override
    public void loadData(Logic logic) {
        setScene(logic.getCurrentStage());
        pointsUI.loadData(logic.getPlayers(), scene);
        if(scene == Scene.bidding)
            biddingUI.loadData(logic, input);
    }

    @Override
    protected PlayerActions actionsMainMenu() {
        return null;
    }

    @Override
    protected PlayerActions actionsBidding() {
        return biddingUI.userAction();
    }

    @Override
    protected PlayerActions actionsGameplay() {
        return null;
    }

    @Override
    protected PlayerActions actionsSummary() {
        return null;
    }

}