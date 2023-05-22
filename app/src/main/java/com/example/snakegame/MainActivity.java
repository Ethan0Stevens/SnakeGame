package com.example.snakegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    SeekBar fruitsSeekerBar;
    TextView nbFruitsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new CustomPagerAdapter(this));
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
}