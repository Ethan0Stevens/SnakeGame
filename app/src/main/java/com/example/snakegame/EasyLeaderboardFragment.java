package com.example.snakegame;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;

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
        // tableEasy.resetTable();

        LeaderBoardActivity.displayLeaderboard(tableEasy, view, tableLayout);
    }
}
