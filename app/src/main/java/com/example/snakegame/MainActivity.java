package com.example.snakegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
    }

    @SuppressLint("NonConstantResourceId")
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
}