package com.example.fallflame.tp1.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

import android.content.Context;
import android.os.CountDownTimer;

/**
 * Created by FallFlame on 15/1/27.
 */
public class Game extends Observable {
    private Card[] cards;
    private Player[] players;

    private int currentPlayerIndex;


    private boolean waitingForInput;

    public Game(){
        final int CARDNUMBER = 24;

        Card[] cards = new Card[CARDNUMBER];
        Deck deck = new Deck();

        for(int i=0; i<cards.length; i+=2){
            Card randomCard = deck.drawRandomCard();
            cards[i] = new Card(randomCard);
            cards[i+1] = new Card(randomCard);
        }

        Random random = new Random();
        for(int i=0; i<cards.length; i++){
            int p = random.nextInt(cards.length);
            Card temp = cards[i];
            cards[i] = cards[p];
            cards[p] = temp;
        }

        this.cards = cards;
    }

    public void setPlayers(Player[] _players){
        players = _players;
    }

    private Player currentPlayer(){
        return players[currentPlayerIndex % players.length];
    }

    public void nextChoose(){
        System.out.println("Player " +  currentPlayerIndex % players.length + " is choosing.");
        currentPlayer().choose();
    }

    public void currentPlayerChooseCard(int index){

        if (cards[index] == null || cards[index].isChosen()){
            nextChoose();
            return;
        }

        final int MATCHSCORE = 1;

        System.out.println("Card["+index+"]:" + cards[index].getName() + " is chosen");
        cards[index].setChosen(true);
        setChanged();
        notifyObservers(new int[]{index});

        for (int i=0; i<cards.length; i++){
            if (cards[i] != null) {
                if (cards[i] != cards[index] && cards[i].isChosen()) {
                    if (cards[i].getName() == cards[index].getName()) {
                        System.out.println("Card["+i+ "]:" + cards[i].getName() + " is removed");
                        cards[i] = null;
                        System.out.println("Card["+index+"]:" + cards[index].getName() + " is removed");
                        cards[index] = null;

                        currentPlayer().addScore(MATCHSCORE);
                        System.out.println("Player " +  currentPlayerIndex % players.length + " get " + MATCHSCORE + " score(s)." );
                        setChanged();
                        notifyObservers(new int[]{i, index});
                    } else {
                        cards[i].setChosen(false);
                        System.out.println("Card["+i+ "]:" + cards[i].getName() + " is set not chosen");
                        cards[index].setChosen(false);
                        System.out.println("Card["+index+"]:" + cards[index].getName() + " is set not chosen");
                        currentPlayerIndex++;
                        System.out.println("Next player");
                        setChanged();
                        notifyObservers(new int[]{i, index});
                    }
                }
            }
        }

        boolean allCardsAreRemoved = true;
        for (int i=0; i<cards.length; i++){
            if(cards[i] != null){
                allCardsAreRemoved = false;
                break;
            }
        }

        if (allCardsAreRemoved){
            System.out.println("All cards are removed");
            gameOver();
        } else{
            System.out.println("Next Choose");
            this.waitingForInput = false;
            nextChoose();
        }
    }

    public void gameOver(){
        System.out.println("Game Over");
        for(int i=0; i<players.length; i++){
            System.out.println("Player " + i + " has " + players[i].score + " score(s)" );
        }
    }

    /******* setter and getter after ***********/

    public Card[] getCards(){
        return this.cards;
    }

    public void setWaitingForInput(boolean flag){
        this.waitingForInput = flag;
    }
}
