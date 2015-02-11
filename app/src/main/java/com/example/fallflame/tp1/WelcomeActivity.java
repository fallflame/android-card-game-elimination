package com.example.fallflame.tp1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class WelcomeActivity extends ActionBarActivity {


    public final static String GAME_MODE = "com.example.fallflame.tp1.GAME_MODE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
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

    public void humanVsHumanButton(View view){
        Intent intent = new Intent(this, GameControllerActivity.class);
        intent.putExtra(GAME_MODE, "HumanVsHuman");
        startActivity(intent);
    }

    public void humanVsAIButton(View view){
        Intent intent = new Intent(this, GameControllerActivity.class);
        intent.putExtra(GAME_MODE, "HumanVsAI");
        startActivity(intent);
    }

    public void historyButton(View view){
        Intent intent = new Intent(this, StatisticsActivity.class);
        startActivity(intent);
    }
}
