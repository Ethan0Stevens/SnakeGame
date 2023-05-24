package com.example.snakegame;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class EasyLeaderboardFragment extends Fragment {
    TableLayout tableLayout;
    public EasyLeaderboardFragment() {
        super(R.layout.view_leaderboard_easy);
    }
    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tableLayout = view.findViewById(R.id.easyTableLayout);

        DataBaseHandler tableEasy = new DataBaseHandler(this.getContext(), "easy");

        ArrayList<ArrayList<String>> data = tableEasy.getDatas();

        if (data != null) {
            for (ArrayList<String> score: data) {

                TableRow newTableRow = new TableRow(view.getContext());
                newTableRow.setId(Integer.parseInt(score.get(0)));
                newTableRow.setBackgroundColor(Color.LTGRAY);
                newTableRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.FILL_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                TextView topText = new TextView(view.getContext());
                topText.setId(Integer.parseInt(score.get(0)) + 10);
                topText.setText(score.get(0));
                topText.setTextColor(Color.BLACK);
                topText.setGravity(Gravity.CENTER_HORIZONTAL);
                topText.setTextSize(14);
                topText.setPadding(10, 10, 10, 10);
                topText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 4));

                TextView nameText = new TextView(view.getContext());
                nameText.setId(Integer.parseInt(score.get(0)) + 11);
                nameText.setText(score.get(1));
                nameText.setTextColor(Color.BLACK);
                nameText.setGravity(Gravity.CENTER_HORIZONTAL);
                nameText.setTextSize(14);
                nameText.setPadding(10, 10, 10, 10);
                nameText.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT, 4));

                TextView scoreText = new TextView(view.getContext());
                scoreText.setId(Integer.parseInt(score.get(0)) + 12);
                scoreText.setText(score.get(2));
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
