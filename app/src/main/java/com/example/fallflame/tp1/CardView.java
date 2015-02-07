package com.example.fallflame.tp1;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by FallFlame on 15/1/27.
 */
public class CardView extends ImageView implements Observer{

    CardView(Context context, final Card card, final GameController controller){
        super(context);
        card.addObserver(this);
        this.update(card, null);

        this.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("Card is clicked");
                controller.chooseACard(card);
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void update(Observable observable, Object data) {

        System.out.println("Update() is called");

        if(observable instanceof Card) {
            Card card = (Card) observable;
            if (card.isRemoved()) {
                this.setImageAlpha(50);
            }

            if (card.isChosen()) {
                this.setImageBitmap(card.getImage());
            } else {

                this.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cardback));
            }
        }
    }
}
