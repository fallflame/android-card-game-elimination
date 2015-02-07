package com.example.fallflame.tp1.model;

/**
 * Created by FallFlame on 15/2/7.
 */
public class HumainPlayer extends Player {

    @Override
    public void choose() {
        game.setWaitingForInput(true);
    }
}
