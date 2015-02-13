package com.example.fallflame.tp1.model;

/**
 * Created by FallFlame on 15/2/7.
 */
public class HumanPlayer extends Player {

    public HumanPlayer(){
        this.name = "Player";
    }
    public HumanPlayer(String _name){
        this.name = _name;
    }

    @Override
    public void choose() {
        game.setWaitingForInput(true);
    }

    public void setName(String name) {
        this.name = name;
    }
}
