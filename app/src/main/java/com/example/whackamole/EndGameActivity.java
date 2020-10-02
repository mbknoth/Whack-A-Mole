package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * This is the End Game Activity which gives the player a summery of how they did.
 * When the player is ready to play again, this activity directs them back to the main activity
 *
 * @author mbknoth
 */
public class EndGameActivity extends AppCompatActivity {

    /**
     * When the End Game Activity is created, it will pull the "extras" that were sent over
     * from the Main Activity and use the information to display them on screen
     *
     * When the user is ready to play again they will press the corresponding button
     * @param savedInstanceState
     */
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

    /**
     * This public helper method is in charge of directing the player back to the Main Activity
     * and also sends the high_score information so it is not lost in transition between
     * the two activities
     *
     * @param high_score
     *  the all time high score of the game
     */
    public void play_again(int high_score) {
        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        myIntent.putExtra("High Score", high_score);
        startActivity(myIntent);
    }
}