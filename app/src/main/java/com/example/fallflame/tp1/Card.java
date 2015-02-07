package com.example.fallflame.tp1;

import java.util.Observable;

/**
 * Created by FallFlame on 15/1/27.
 */
public class Card{

    private String name;
    private boolean isChosen;

    Card (String _name){
        this.name = _name;
    }

    Card(Card card){
        this.name = card.name;
    }

    public String getName(){
        return this.name;
    }

    public void setChosen(boolean _isChosen){
        this.isChosen = _isChosen;
    }

    public boolean isChosen(){
        return this.isChosen;
    }


