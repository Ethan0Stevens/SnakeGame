package com.example.snakegame;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;

public class LeaderBoardActivity extends AppCompatActivity {
    // Déclaration des variables
    ViewPager2 viewPager;
    Adapter adapter;

    /**
     * Code executé a la creation de l'activité
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        setViewPager();
    }

    /**
     * Initialisation du ViewPager de leaderboards
     */
    private void setViewPager() {
        viewPager = findViewById(R.id.viewpager);
        adapter = new Adapter(getSupportFragmentManager(), getLifecycle());

        adapter.addFragment(new EasyLeaderboardFragment());
        adapter.addFragment(new MediumLeaderboardFragment());
        adapter.addFragment(new HardLeaderboardFragment());

        viewPager.setAdapter(adapter);
    }

    /**
     * Mettre fin a l'activité
     */
    public void leaveLeaderboard(View view) {
        finish();
    }

    /**
     * Afficher chaques données de la base dans un tableau
     * @param table la table de la base de données
     * @param tableLayout le TableLayout dans lequel afficher les scores
     */
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

                createTextView(newTableRow, view, score.get(1), Integer.parseInt(score.get(1)) + 10);
                createTextView(newTableRow, view, score.get(2), Integer.parseInt(score.get(1)) + 11);
                createTextView(newTableRow, view, score.get(3), Integer.parseInt(score.get(1)) + 12);

                tableLayout.addView(newTableRow, new TableLayout.LayoutParams(
                                TableLayout.LayoutParams.FILL_PARENT,
                                TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    }

    /**
     * Creation d'un TextView
     * @param text le text a afficher dans la textView
     * @param id id de la textView
     */
    private static void createTextView(TableRow newTableRow, View view, String text, int id) {
        TextView textView = new TextView(view.getContext());
        textView.setId(id);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);
        textView.setTextSize(14);
        textView.setPadding(10, 10, 10, 10);
        textView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 4));

        newTableRow.addView(textView);
    }
}
