package com.wlgc.thousand.ui;

import com.wlgc.thousand.logic.GameStage;
import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.logic.PlayerActions;

public abstract class UserInterface {

    protected Scene scene;

    protected void setScene(GameStage stage){
        switch(stage){
            case deal: scene = Scene.bidding; break;
            case bid: scene = Scene.bidding; break;
            case play: scene = Scene.main_gameplay; break;
            case summarize: scene = Scene.summary; break;
        }
    }

    public void render(){
        switch(scene){
            case main_menu: renderMainMenu(); break;
            case bidding: renderBidding(); break;
            case main_gameplay: renderMainGameplay(); break;
            case summary: renderSummary(); break;
        }
    }

    public PlayerActions userAction(){
        switch(scene){
            case main_menu: return actionsMainMenu();
            case bidding: return actionsBidding();
            case main_gameplay: return actionsGameplay();
            case summary: return actionsSummary();
        }
        return null;
    }

    protected abstract PlayerActions actionsMainMenu();

    protected abstract PlayerActions actionsBidding();

    protected abstract PlayerActions actionsGameplay();

    protected abstract PlayerActions actionsSummary();

    protected abstract void renderMainMenu();

    protected abstract void renderBidding();

    protected abstract void renderMainGameplay();

    protected abstract void renderSummary();

    public abstract void loadData(Logic logic);

}
