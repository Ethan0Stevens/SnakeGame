package com.example.snakegame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class MediumLeaderboardFragment extends Fragment {
    TableLayout tableLayout;
    public MediumLeaderboardFragment() {
        super(R.layout.view_leaderboard_medium);
    }
    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        tableLayout = view.findViewById(R.id.mediumTableLayout);

        DataBaseHandler tableMedium = new DataBaseHandler(this.getContext(), "medium");

        LeaderBoardActivity.displayLeaderboard(tableMedium, view, tableLayout);
    }
}
