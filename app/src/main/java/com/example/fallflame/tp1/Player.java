package com.example.fallflame.tp1;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by FallFlame on 15/1/27.
 */
public class Player implements Observer{

    private int score;
    private Game game;
    private int playerIndex;

    public void setGame(Game _game){
        this.game = _game;
    }

    public void setPlayerIndex(int i){
        this.playerIndex = i;
    }

    public void choose(Card card){
        if(this.game.getCurrentPlayerIndex() == this.playerIndex) {
            this.game.choose(card);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        // do nothing now
        //Game game = (Game)observable;
    }
}
