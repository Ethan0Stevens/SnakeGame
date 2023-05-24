package com.example.snakegame;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

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
}
