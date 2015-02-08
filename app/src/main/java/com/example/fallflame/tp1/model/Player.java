package com.example.fallflame.tp1.model;

/**
 * Created by FallFlame on 15/1/27.
 */
public abstract class Player {

    protected int score = 0;
    protected Game game;

    public void setGame(Game _game){
        this.game = _game;
    }

    public abstract void choose();

    public void addScore(int s){
        this.score += s;
    }

    public int getScore(){
        return score;
    }

}
