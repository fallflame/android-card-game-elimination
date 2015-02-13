package com.example.fallflame.tp1.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Random;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.util.Log;

/**
 * Created by FallFlame on 15/1/27.
 */
public class Game extends Observable {
    private Card[] cards;
    private Player[] players;

    private int currentPlayerIndex;


    private boolean waitingForInput;
    private boolean isGameOver;

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
        for(int i=0; i<players.length; i++){
            players[i].setGame(this);
        }
    }


    public void nextChoose(){
        if(isGameOver) return;
        Log.d("app","Player " + currentPlayerIndex % players.length + " is choosing.");
        currentPlayer().choose();
    }

    public void currentPlayerChooseCard(int index){

        if (cards[index] == null || cards[index].isChosen()){
            nextChoose();
            return;
        }

        final int MATCHSCORE = 1;

        Log.d("app", "Card["+index+"]:" + cards[index].getName() + " is chosen");
        cards[index].setChosen(true);
        setChanged();
        notifyObservers(new int[]{index});

        for (int i=0; i<cards.length; i++){
            if (cards[i] != null) {
                if (cards[i] != cards[index] && cards[i].isChosen()) {
                    if (cards[i].getName() == cards[index].getName()) {
                        Log.d("app", "Card["+i+ "]:" + cards[i].getName() + " is removed");
                        cards[i] = null;
                        Log.d("app", "Card["+index+"]:" + cards[index].getName() + " is removed");
                        cards[index] = null;

                        currentPlayer().addScore(MATCHSCORE);
                        Log.d("app", "Player " +  currentPlayerIndex % players.length + " get " + MATCHSCORE + " score(s)." );
                        setChanged();
                        notifyObservers(new int[]{i, index});
                    } else {
                        cards[i].setChosen(false);
                        Log.d("app", "Card["+i+ "]:" + cards[i].getName() + " is set not chosen");
                        cards[index].setChosen(false);
                        Log.d("app", "Card["+index+"]:" + cards[index].getName() + " is set not chosen");
                        currentPlayerIndex++;
                        Log.d("app", "Next player");
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
            Log.d("app", "All cards are removed");
            gameOver();
        } else{
            this.waitingForInput = false;
            //nextChoose();
        }
    }

    public void gameOver(){
        isGameOver = true;
        setChanged();
        notifyObservers();
        Log.d("app", "Game Over");
        for(int i=0; i<players.length; i++){
            Log.d("app", players[i].getName() + " has " + players[i].score + " score(s)" );
        }
    }

    /******* setter and getter after ***********/

    public boolean isGameOver(){
        return this.isGameOver;
    }
    public Card[] getCards(){
        return this.cards;
    }

    public void setWaitingForInput(boolean flag){
        this.waitingForInput = flag;
    }

    public boolean isWaitingForInput(){
        return this.waitingForInput;
    }

    private Player currentPlayer(){
        return players[currentPlayerIndex % players.length];
    }

    public int getCurrentPlayerIndex(){
        return currentPlayerIndex % players.length;
    }

    public int getPlayerScoreByIndex(int i){
        return players[i].getScore();
    }

    public String getPlayerNameByIndex(int i){ return players[i].getName(); }

    public void setPlayerNameByIndex(int i, String _name) { this.players[i].setName(_name);}
}

