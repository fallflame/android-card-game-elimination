package com.example.fallflame.tp1.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by FallFlame on 15/1/27.
 */
public class Deck {

    //private Card[] cards;
    private ArrayList<Card> cards = new ArrayList<>();

    Deck(){
        populateCards();
    }

    private void populateCards(){
        //twelve cards the zodiac
        this.cards.add(new Card("rat"));
        this.cards.add(new Card("ox"));
        this.cards.add(new Card("tiger"));
        this.cards.add(new Card("rabbit"));
        this.cards.add(new Card("dragon"));
        this.cards.add(new Card("snake"));
        this.cards.add(new Card("horse"));
        this.cards.add(new Card("goat"));
        this.cards.add(new Card("monkey"));
        this.cards.add(new Card("rooster"));
        this.cards.add(new Card("dog"));
        this.cards.add(new Card("pig"));
        Collections.shuffle(cards);
    }

    public Card drawRandomCard(){

        if(cards.size() == 0){
            populateCards();
        }

        Card card = cards.get(0);
        cards.remove(0);
        return card;
    }

}
