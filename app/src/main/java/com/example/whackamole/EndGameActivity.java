package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.whackamole.ui.main.ScoreViewModel;

public class EndGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        TextView score = (TextView) findViewById(R.id.end_game_score);
        TextView high_score = (TextView) findViewById(R.id.end_game_hi_score);
        Bundle scores = getIntent().getExtras();
        int end_game_score;
        int high_score_of_game;
        if (scores == null) {
            end_game_score = 0;
            high_score_of_game = 0;
        } else {
            end_game_score = scores.getInt("Current Score");
            high_score_of_game = scores.getInt("High Score");
        }
        String text = getString(R.string.end_game_screen_text, end_game_score);
        score.setText(text);

        String high_score_text = getString(R.string.game_hi_score, high_score_of_game);
        high_score.setText(high_score_text);

        Button play_again = (Button) findViewById(R.id.play_again);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_again(high_score_of_game);
            }
        });
    }

    public void play_again(int high_score) {
        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        myIntent.putExtra("High Score", high_score);
        startActivity(myIntent);
    }
}