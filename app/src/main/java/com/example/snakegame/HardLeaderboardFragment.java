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

public class HardLeaderboardFragment extends Fragment {
    TableLayout tableLayout;
    public HardLeaderboardFragment() {
        super(R.layout.view_leaderboard_hard);
    }
    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tableLayout = view.findViewById(R.id.easyTableLayout);

        DataBaseHandler tableHard = new DataBaseHandler(this.getContext(), "hard");

        LeaderBoardActivity.displayLeaderboard(tableHard, view, tableLayout);
    }
}
