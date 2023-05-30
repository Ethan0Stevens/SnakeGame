package com.example.snakegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new LevelPagerAdapter(this));
    }

    public void startGame(View view) {

        int buttonId = view.getId();
        Intent intent = new Intent(this, GameActivity.class);

        if (buttonId == R.id.startEasyBtn) {
            intent.putExtra("difficulty", "easy");
        } else if (buttonId == R.id.startMediumBtn) {
            intent.putExtra("difficulty", "medium");
        } else if (buttonId == R.id.startHardBtn) {
            intent.putExtra("difficulty", "hard");
        }

        startActivity(intent);
    }

    public void jumpToNextPage(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    public void jumpToPreviousPage(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

    public void startCustomGame(View view) {
        Intent intent = new Intent(this, CustomGameActivity.class);
        startActivity(intent);
    }

    public void goToLeaderboard(View view) {

        int viewId = view.getId();
        Intent intent = new Intent(this, LeaderBoardActivity.class);

        if (viewId == R.id.leaderboardEasyText || viewId == R.id.LeaderboardEasyArrow) {
            intent.putExtra("difficulty", "easy");
        } else if (viewId == R.id.leaderboardMediumText || viewId == R.id.LeaderboardMediumArrow) {
            intent.putExtra("difficulty", "medium");
        } else if (viewId == R.id.leaderboardHardText || viewId == R.id.LeaderboardHardArrow) {
            intent.putExtra("difficulty", "hard");
        }

        startActivity(intent);
    }
}