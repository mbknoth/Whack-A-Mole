package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.whackamole.ui.main.ScoreViewModel;

public class EndGameActivity extends AppCompatActivity {

    private ScoreViewModel scoreViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_game);

        scoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        TextView score = (TextView) findViewById(R.id.end_game_score);
        String text = getString(R.string.end_game_screen_text, scoreViewModel.getCurrentScore().getValue());
        score.setText(text);

        Button play_again = (Button) findViewById(R.id.play_again);
        play_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_again();
            }
        });
    }

    public void play_again(){
        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(myIntent);
    }
}