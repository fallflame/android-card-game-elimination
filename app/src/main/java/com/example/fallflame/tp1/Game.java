package com.example.fallflame.tp1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.os.CountDownTimer;

/**
 * Created by FallFlame on 15/1/27.
 */
public class Game extends Observable {
    private ArrayList<Card> cards = new ArrayList<Card>();
    private ArrayList<Player> players = new ArrayList<Player>();
    private int currentPlayerIndex;
    final private int CARDNUMBER = 24;
    final private int MATCHSCORE = 1;

    Game(Context context, Player player1, Player player2){
        player1.setGame(this);
        player1.setPlayerIndex(0);
        this.addObserver(player1);
        this.players.add(player1);
        player2.setGame(this);
        player2.setPlayerIndex(1);
        this.addObserver(player2);
        this.players.add(player2);

        // create the cards to play
        Deck deck = new Deck(context);
        for (int i = 0; i < CARDNUMBER/2; i++){
            Card aCard = deck.drawRandomCard();

            // add two copies of this card to the game
            Card c1 = new Card(aCard);
            Card c2 = new Card(aCard);

            this.cards.add(c1);
            this.cards.add(c2);
        }
        Collections.shuffle(this.cards);

        // set the first player
        this.currentPlayerIndex = 0;
        //setChanged();
        //notify();
    }

    public ArrayList<Card> getCards(){
        return this.cards;
    }

    public int getCurrentPlayerIndex(){
        return this.currentPlayerIndex;
    }

    // this method will return the score obtained
    public int choose(final Card card){

        card.setChosen(true);

        new CountDownTimer(1000,1000) {

            @Override
            public void onTick(long arg0) {}

            @Override
            public void onFinish() {
                check(card);
            }
        }.start();

        return 0;
    }

    public int check(Card card){
        for (Card otherCard : cards){
            if (otherCard != card && otherCard.isChosen()) {

                if(otherCard.getName() == card.getName()){

                    card.remove();
                    otherCard.remove();

                    return MATCHSCORE;

                }else{
                    otherCard.setChosen(false);
                    card.setChosen(false);
                    currentPlayerIndex = (currentPlayerIndex == 0) ? 1 : 0;

                }

                break;
            }
        }

        return 0;
    }

}
