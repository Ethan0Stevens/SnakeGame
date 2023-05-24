package com.example.snakegame;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class LeaderBoardActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new LeaderboardPagerAdapter(this));

        Bundle extras = getIntent().getExtras();
        String difficulty = "";
        if(extras != null) {
            if (extras.getString("difficulty") != null) {
                difficulty = extras.getString("difficulty");
            }
        }
    }
}
