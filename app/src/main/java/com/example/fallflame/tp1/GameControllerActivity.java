package com.example.fallflame.tp1;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.fallflame.tp1.model.AIPlayer;
import com.example.fallflame.tp1.model.Game;
import com.example.fallflame.tp1.model.HumainPlayer;
import com.example.fallflame.tp1.model.Player;

import java.util.Observable;
import java.util.Observer;


public class GameControllerActivity extends ActionBarActivity implements Observer {

    private Game game;
    private ImageView[] cardViews;
    //private AnimationSet animation = new AnimationSet(true);
    private long animationStartDelay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        AIPlayer player1 = new AIPlayer();
        HumainPlayer player2 = new HumainPlayer();
        this.game = new Game();
        game.addObserver(this);
        game.setPlayers(new Player[]{player1, player2});

        this.cardViews = new ImageView[game.getCards().length];

        for(int i=0; i<cardViews.length; i++){
            int resId = getResources().getIdentifier("cardImage"+i, "id", this.getPackageName());
            cardViews[i] = (ImageView)findViewById(resId);

            final int index = i;
            cardViews[i].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (game.isWaitingForInput() && game.getCards()[index] != null && !game.getCards()[index].isChosen()) {
                        game.setWaitingForInput(false);
                        game.currentPlayerChooseCard(index);
                    }
                    return false;
                }
            });
        }

        game.nextChoose();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void gameOver(){

        SQLiteDatabase db = openOrCreateDatabase("cardGame.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS history (_id INTEGER PRIMARY KEY AUTOINCREMENT, records VARCHAR)");
        db.execSQL("INSERT INTO history VALUES (NULL, ?)", new String[]{"Player1: " + game.getPlayer0Score() + ", Player2: " + game.getPlayer1Score() });

        Log.d("GameController", "Process Game Over");
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    @Override
    public void update(Observable observable, Object data) {

        //set the light and score

        if (game.isGameOver()){
            gameOver();
            return;
        }

        ImageView player0Light = (ImageView) findViewById(R.id.player0Light);
        ImageView player1Light = (ImageView) findViewById(R.id.player1Light);
        TextView player0Score = (TextView) findViewById(R.id.player0Score);
        TextView player1Score = (TextView) findViewById(R.id.player1Score);

        if (game.getCurrentPlayerIndex() == 0 ){
            player0Light.setBackgroundColor(0xff00ff00);
            player1Light.setBackgroundColor(0xffff0000);
        }else{
            player0Light.setBackgroundColor(0xffff0000);
            player1Light.setBackgroundColor(0xff00ff00);
        }

        player0Score.setText(Integer.toString(game.getPlayer0Score())); //Integer.toString(game.getPlayer0Score())
        player1Score.setText(Integer.toString(game.getPlayer1Score()));


        if(data instanceof int[]){

            int[] indexes = (int[]) data;

            for(int i=0; i<indexes.length; i++){

                final int index = indexes[i];

                if(game.getCards()[index] != null) {

                    if (game.getCards()[index].isChosen()) {

                        String cardName = game.getCards()[index].getName();
                        int resId = getResources().getIdentifier(cardName, "drawable", this.getPackageName());
                        cardViews[index].setImageDrawable(getResources().getDrawable(resId));
                        cardViews[index].animate().alpha(1).setDuration(0).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {;
                                game.nextChoose();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });



                    } else{
                        cardViews[index].animate().alpha(1).setDuration(1000).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if(game.getCards()[index] != null) {
                                    cardViews[index].setImageDrawable(getResources().getDrawable(R.drawable.cardback));
                                }
                                Log.d("app", "Next Choose");
                                game.nextChoose();
                            }

                            @Override
                            public void onAnimationCancel(Animator animation) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animation) {

                            }
                        });
                    }
                }else{
                    cardViews[index].animate().alpha(0).setDuration(1000).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if(!game.isGameOver()) {
                                Log.d("app", "Next Choose");
                                game.nextChoose();
                            }
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });
                }
            }

        }
    }
}
