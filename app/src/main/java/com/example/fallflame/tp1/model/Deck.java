package com.example.fallflame.tp1.model;

import android.content.Context;

import java.util.Random;

/**
 * Created by FallFlame on 15/1/27.
 */
public class Deck {

    private Card[] cards;

    Deck(){
       //twelve cards the zodiac
        Card[] _cards = {
                new Card("rat"),
                new Card("ox"),
                new Card("tiger"),
                new Card("rabbit"),
                new Card("dragon"),
                new Card("snake"),
                new Card("horse"),
                new Card("goat"),
                new Card("monkey"),
                new Card("rooster"),
                new Card("dog"),
                new Card("pig")
        };

        this.cards = _cards;
    }

    public Card drawRandomCard(){

        Random random = new Random();
        int num =random.nextInt(this.cards.length);
        Card randomCard = cards[num];

        return randomCard;
    }

}
