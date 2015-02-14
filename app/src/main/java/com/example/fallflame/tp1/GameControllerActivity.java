package com.example.fallflame.tp1;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fallflame.tp1.model.AIPlayer;
import com.example.fallflame.tp1.model.Game;
import com.example.fallflame.tp1.model.HumanPlayer;
import com.example.fallflame.tp1.model.Player;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class GameControllerActivity extends ActionBarActivity implements Observer {

    private Game game;
    private ImageView[] cardViews;

    //used for show the tips
    private ArrayList<String> tips = new ArrayList<>();
    private boolean showTips = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("GameControllerActivity", "creating");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        // get the game mode from the welcome activity
        Intent intent = getIntent();
        String gameMode = intent.getStringExtra(WelcomeActivity.GAME_MODE);
        Log.d("GameControllerActivity", "Game Mode: " + gameMode);

        this.game = new Game();
        game.addObserver(this);

        // set the game mode
        if (gameMode.equals("HumanVsHuman")) {
            // two human player
            game.setPlayers(new Player[]{new HumanPlayer(), new HumanPlayer()});

            // two alert dialogs ask the player's name
            final EditText name1EditText = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Please input player2's name")
                    .setView(name1EditText)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            game.setPlayerNameByIndex(1, name1EditText.getText().toString());
                            TextView player1Name = (TextView) findViewById(R.id.player1Name);
                            player1Name.setText(game.getPlayerNameByIndex(1) + "'s score: ");
                        }
                    })
                    .show();

            final EditText name0EditText = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Please input player1's name")
                    .setView(name0EditText)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            game.setPlayerNameByIndex(0, name0EditText.getText().toString());
                            TextView player0Name = (TextView) findViewById(R.id.player0Name);
                            player0Name.setText(game.getPlayerNameByIndex(0) + "'s score: ");
                        }
                    })
                    .show();

        } else{

            //this first player is a human player, the second is an AI player
            game.setPlayers(new Player[]{new HumanPlayer(), new AIPlayer()});

            // an alert dialog ask the player's name
            final EditText nameEditText = new EditText(this);
            new AlertDialog.Builder(this)
                    .setTitle("Please input your name")
                    .setView(nameEditText)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            game.setPlayerNameByIndex(0, nameEditText.getText().toString());
                            TextView player0Name = (TextView) findViewById(R.id.player0Name);
                            player0Name.setText(game.getPlayerNameByIndex(0) + "'s score: ");
                        }
                    })
                    .show();
            TextView player1Name = (TextView) findViewById(R.id.player1Name);
            player1Name.setText(game.getPlayerNameByIndex(1) + "'s score: ");

            showTips = true;
        }


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

        //begin the game
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

    private void addTips(String _tip){
        if (tips.size() >= 4 )
            tips.remove(0);
        tips.add(_tip);
    }

    public void gameOver(){

        SQLiteDatabase db = openOrCreateDatabase("cardGame.db", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS scoreRecords (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, score INTEGER)");
        db.execSQL("INSERT INTO scoreRecords VALUES (NULL, ?, ?)", new Object[]{game.getPlayerNameByIndex(0), game.getPlayerScoreByIndex(0)});
        db.execSQL("INSERT INTO scoreRecords VALUES (NULL, ?, ?)", new Object[]{game.getPlayerNameByIndex(1), game.getPlayerScoreByIndex(1)});

        Log.d("GameController", "Processing Game Over");
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }

    @Override
    public void update(Observable observable, Object data) {

        if (game.isGameOver()){
            gameOver();
            return;
        }

        //set the light and score
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

        player0Score.setText(Integer.toString(game.getPlayerScoreByIndex(0)));
        player1Score.setText(Integer.toString(game.getPlayerScoreByIndex(1)));

        // set the tips
        if(showTips){
            TextView tipsView = (TextView) findViewById(R.id.tipsView);
            String tipsString = "";
            for (String t : tips){
                tipsString = tipsString + t + "\n";
            }
            tipsView.setText(tipsString);
        }

        if(data instanceof int[]){

            final int[] indexes = (int[]) data;

            for(int i=0; i<indexes.length; i++){

                final int index = indexes[i];
                final int iInIndexes = i;

                if(game.getCards()[index] != null) {
                    //this card is not removed

                    if (game.getCards()[index].isChosen()) {
                        //this card is set chosen
                        String cardName = game.getCards()[index].getName();
                        int resId = getResources().getIdentifier(cardName, "drawable", this.getPackageName());
                        cardViews[index].setImageDrawable(getResources().getDrawable(resId));
                        addTips(cardName + " in: " + index);
                        cardViews[index].animate().alpha(1).setDuration(100).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if(iInIndexes == indexes.length-1)
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
                        //this card is set un chosen
                        cardViews[index].animate().alpha(1).setDuration(1000).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animation) {
                                if(game.getCards()[index] != null) {
                                    cardViews[index].setImageDrawable(getResources().getDrawable(R.drawable.cardback));
                                }
                                if(iInIndexes == indexes.length-1)
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

                    // this card is removed
                    cardViews[index].animate().alpha(0).setDuration(1000).setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if(!game.isGameOver()) {
                                if(iInIndexes == indexes.length-1)
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

    public void quitButton(View view){
        final GameControllerActivity self = this;
        new AlertDialog.Builder(this)
                .setTitle("Quit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        self.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
