package com.example.fallflame.tp1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.Image;

import java.util.Random;

/**
 * Created by FallFlame on 15/1/27.
 */
public class Deck {

    private Card[] cards;

    Deck(Context context){
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
                new Card("J")
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
