package com.example.fallflame.tp1;

import android.app.ActionBar;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class GameController extends ActionBarActivity {

    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_view);

        Player player1 = new Player();
        Player player2 = new Player();
        this.game = new Game(getApplicationContext(), player1, player2);
        GridLayout grid = (GridLayout)findViewById(R.id.GridLayout);

        for (Card card : game.getCards()){
            CardView cardView = new CardView(getApplicationContext(), card, this);

            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
            grid.addView(cardView, layoutParams);
            cardView.setScaleType(ImageView.ScaleType.FIT_XY);
            cardView.getLayoutParams().height = 230;
            cardView.getLayoutParams().width = 240;

        }

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

    public void chooseACard(Card card){
        this.game.choose(card);
    }
}
