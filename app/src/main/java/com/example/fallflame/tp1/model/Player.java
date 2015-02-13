package com.example.fallflame.tp1.model;

/**
 * Created by FallFlame on 15/1/27.
 */
public abstract class Player {

    protected int score = 0;
    protected Game game;
    protected String name = "Anonymous Player";

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

    public String getName(){
        return name;
    }

    public void setName(String _name){ this.name = _name; }

}
