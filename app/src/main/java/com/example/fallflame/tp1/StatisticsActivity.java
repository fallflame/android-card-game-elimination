package com.example.fallflame.tp1;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;


public class StatisticsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        SQLiteDatabase db = openOrCreateDatabase("cardGame.db", Context.MODE_PRIVATE, null);

        db.execSQL("CREATE TABLE IF NOT EXISTS scoreRecords (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, score INTEGER)");

        /*db.execSQL("INSERT INTO scoreRecords VALUES (NULL, 'Yan', 5)");
        db.execSQL("INSERT INTO scoreRecords VALUES (NULL, 'Yan', 10)");
        db.execSQL("INSERT INTO scoreRecords VALUES (NULL, 'AI', 6)");
        db.execSQL("INSERT INTO scoreRecords VALUES (NULL, 'AI', 12)");
        db.execSQL("INSERT INTO scoreRecords VALUES (NULL, 'Qian', 3)");
        db.execSQL("INSERT INTO scoreRecords VALUES (NULL, 'Qian', 8)");*/

        //db.execSQL("CREATE TABLE IF NOT EXISTS history (_id INTEGER PRIMARY KEY AUTOINCREMENT, records VARCHAR)");
        Cursor c = db.rawQuery("SELECT name, max(score) FROM scoreRecords GROUP BY name ORDER BY max(score) DESC", null);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.records);

        Log.d("DB", c.getCount() + " record(s)");
        while (c.moveToNext()){
            String record = c.getString(c.getColumnIndex("name")) + ": " + c.getString(c.getColumnIndex("max(score)"));
            TextView textView = new TextView(getBaseContext());
            textView.setTextColor(0xFF000000);
            textView.setText(record);
            linearLayout.addView(textView);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_statistics, menu);
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
}
