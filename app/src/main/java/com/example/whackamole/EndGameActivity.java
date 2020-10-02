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

    private ScoreViewModel scoreViewModel;
    private Observer<Integer> scoreObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        scoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        TextView score = (TextView) findViewById(R.id.end_game_score);
        TextView high_score = (TextView) findViewById(R.id.end_game_hi_score);

        String text = getString(R.string.end_game_screen_text, scoreViewModel.getCurrentScore().getValue());
        score.setText(text);

        high_score.setText(R.string.game_default_hi_score);
        if (scoreViewModel.getCurrentScore().getValue() > scoreViewModel.getHighScore().getValue()) {
            scoreViewModel.setHighScore(scoreViewModel.getCurrentScore());
            scoreObserver = new Observer<Integer>() {
                @Override
                public void onChanged(Integer integer) {
                    int new_hi_score = scoreViewModel.getHighScore().getValue();
                    String text = getString(R.string.game_hi_score, new_hi_score);
                    high_score.setText(text);
                }
            };
        }

        Button play_again = (Button) findViewById(R.id.play_again);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scoreViewModel.resetCurrentScore();
                play_again();
            }
        });
    }

    public void play_again() {
        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(myIntent);
    }
}