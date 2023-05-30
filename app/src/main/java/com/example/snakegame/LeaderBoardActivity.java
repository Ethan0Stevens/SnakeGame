package com.example.snakegame;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Objects;



public class LeaderBoardActivity extends AppCompatActivity {
    ViewPager2 viewPager;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        viewPager = findViewById(R.id.viewpager);
        adapter = new Adapter(getSupportFragmentManager(), getLifecycle());

        adapter.addFragment(new EasyLeaderboardFragment());
        adapter.addFragment(new MediumLeaderboardFragment());
        adapter.addFragment(new HardLeaderboardFragment());

        viewPager.setAdapter(adapter);

        Bundle extras = getIntent().getExtras();
        String difficulty = "";
        if(extras != null) {
            if (extras.getString("difficulty") != null) {
                difficulty = extras.getString("difficulty");
            }
        }
    }

    public void leaveLeaderboard(View view) {
        finish();
    }

    public static void displayLeaderboard(DataBaseHandler table, View view, TableLayout tableLayout) {
        ArrayList<ArrayList<String>> data = table.getDatas();
        if (data != null) {
            for (ArrayList<String> score: data) {

                TableRow newTableRow = new TableRow(view.getContext());
                newTableRow.setId(Integer.parseInt(score.get(1)));
                newTableRow.setBackgroundColor(Color.LTGRAY);
                newTableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                TextView topText = new TextView(view.getContext());
                topText.setId(Integer.parseInt(score.get(1)) + 10);
                topText.setText(score.get(1));
                topText.setTextColor(Color.BLACK);
                topText.setGravity(Gravity.CENTER_HORIZONTAL);
                topText.setTextSize(14);
                topText.setPadding(10, 10, 10, 10);
                topText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 4));

                TextView nameText = new TextView(view.getContext());
                nameText.setId(Integer.parseInt(score.get(1)) + 11);
                nameText.setText(score.get(2));
                nameText.setTextColor(Color.BLACK);
                nameText.setGravity(Gravity.CENTER_HORIZONTAL);
                nameText.setTextSize(14);
                nameText.setPadding(10, 10, 10, 10);
                nameText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 4));

                TextView scoreText = new TextView(view.getContext());
                scoreText.setId(Integer.parseInt(score.get(1)) + 12);
                scoreText.setText(score.get(3));
                scoreText.setTextColor(Color.BLACK);
                scoreText.setGravity(Gravity.CENTER_HORIZONTAL);
                scoreText.setTextSize(14);
                scoreText.setPadding(10, 10, 10, 10);
                scoreText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 4));

                newTableRow.addView(topText);
                newTableRow.addView(nameText);
                newTableRow.addView(scoreText);

                tableLayout.addView(newTableRow, new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.FILL_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    }
}
