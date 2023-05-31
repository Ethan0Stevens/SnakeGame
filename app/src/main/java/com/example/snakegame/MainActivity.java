package com.example.snakegame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    // Déclaration des variables
    ViewPager2 viewPager;
    Adapter adapter;

    /**
     * Code executé a la creation de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewpager);
        adapter = new Adapter(getSupportFragmentManager(), getLifecycle());

        adapter.addFragment(new EasyLevelFragment());
        adapter.addFragment(new MediumLevelFragment());
        adapter.addFragment(new HardLevelFragment());
        adapter.addFragment(new CustomLevelFragment());

        viewPager.setAdapter(adapter);
    }

    /**
     * Démarre le jeu avec le bon parametre de difficulté
     */
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

    /**
     * Swipe d'une page vers la droite
     */
    public void jumpToNextPage(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
    }

    /**
     * Swipe d'une page vers la gauche
     */
    public void jumpToPreviousPage(View view) {
        viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
    }

    /**
     * Lancer le jeu en mode personnalisé
     */
    public void startCustomGame(View view) {
        Intent intent = new Intent(this, CustomGameActivity.class);
        startActivity(intent);
    }

    /**
     * Aller aux pages de leaderboards correspondante
     */
    public void goToLeaderboard(View view) {
        int buttonId = view.getId();
        Intent intent = new Intent(this, LeaderBoardActivity.class);

        if (buttonId == R.id.leaderboardEasyText || buttonId == R.id.LeaderboardEasyArrow) {
            intent.putExtra("difficulty", "easy");
        } else if (buttonId == R.id.leaderboardMediumText || buttonId == R.id.LeaderboardMediumArrow) {
            intent.putExtra("difficulty", "medium");
        } else if (buttonId == R.id.leaderboardHardText || buttonId == R.id.LeaderboardHardArrow) {
            intent.putExtra("difficulty", "hard");
        }

        startActivity(intent);
    }
}