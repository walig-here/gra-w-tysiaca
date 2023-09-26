package com.wlgc.thousand.ui;

import com.wlgc.thousand.logic.Logic;
import com.wlgc.thousand.logic.players.PlayerActions;
import com.wlgc.thousand.logic.stages.GameStage;

public abstract class UserInterface {

    protected Scene scene;

    protected void setScene(GameStage stage){
        switch(stage){
            case deal: scene = Scene.dealing; break;
            case bid: scene = Scene.bidding; break;
            case prepare: scene = Scene.preparations; break;
            case play: scene = Scene.main_gameplay; break;
            case summarize: scene = Scene.summary; break;
            default: break;
        }
    }

    public void render(){
        switch(scene){
            case main_menu: renderMainMenu(); break;
            case dealing: renderDealing(); break;
            case bidding: renderBidding(); break;
            case preparations: renderPreparation();; break;
            case main_gameplay: renderMainGameplay(); break;
            case summary: renderSummary(); break;
        }
    }

    public PlayerActions userAction(){
        switch(scene){
            case main_menu: return actionsMainMenu();
            case dealing: return PlayerActions.wait_for_bot_1;
            case bidding: return actionsBidding();
            case preparations: return actionPreparation();
            case main_gameplay: return actionsGameplay();
            case summary: return actionsSummary();
        }
        return null;
    }

    protected abstract PlayerActions actionsMainMenu();

    protected abstract PlayerActions actionsBidding();

    protected abstract PlayerActions actionPreparation();

    protected abstract PlayerActions actionsGameplay();

    protected abstract PlayerActions actionsSummary();

    protected abstract void renderMainMenu();

    protected abstract void renderDealing();

    protected abstract void renderBidding();

    protected abstract void renderPreparation();

    protected abstract void renderMainGameplay();

    protected abstract void renderSummary();

    public abstract void loadData(Logic logic);

}
