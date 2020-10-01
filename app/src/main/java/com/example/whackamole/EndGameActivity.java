package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.whackamole.ui.main.ScoreViewModel;

public class EndGameActivity extends AppCompatActivity {

    private ScoreViewModel scoreViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);
    }
}