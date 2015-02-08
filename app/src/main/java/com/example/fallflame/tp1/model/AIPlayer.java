package com.example.fallflame.tp1.model;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

/**
 * Created by FallFlame on 15/2/7.
 */
public class AIPlayer extends Player implements Observer {

    Card[] cards;

    @Override
    public void setGame(Game _game) {
        super.setGame(_game);
        _game.addObserver(this);
        this.cards = new Card[this.game.getCards().length];
    }

    @Override
    public void choose() {

        Random random = new Random();

        if(random.nextInt(100) < 50) {
            Card firstCard = null;
            int i, j;

            for (i = 0; i < cards.length; i++) {
                if (cards[i] != null) {
                    if (cards[i].isChosen()) {
                        firstCard = cards[i];
                        break;
                    }
                }
            }

            if (firstCard == null) { // no cards is chosen
                for (i = 0; i < cards.length; i++) {
                    for (j = 0; j < cards.length; j++) {
                        if (cards[i] != null && cards[j] != null && i != j) {
                            if (cards[i].getName() == cards[j].getName()) {
                                game.currentPlayerChooseCard(i);
                                return;
                            }
                        }
                    }
                }
            } else { // one cards is chosen
                for (i = 0; i < cards.length; i++) {
                    if (cards[i] != null) {
                        if (cards[i] != firstCard && cards[i].getName() == firstCard.getName()) {
                            game.currentPlayerChooseCard(i);
                            return;
                        }
                    }
                }
            }
        }


        int randomIndex =random.nextInt(this.cards.length);

        game.currentPlayerChooseCard(randomIndex);

    }

    @Override
    public void update(Observable observable, Object data) {
        if(data instanceof int[]){

            int[] indexes = (int[]) data;

            for(int i=0; i<indexes.length; i++){
                cards[indexes[i]] = game.getCards()[indexes[i]];
            }

        }
    }
}
