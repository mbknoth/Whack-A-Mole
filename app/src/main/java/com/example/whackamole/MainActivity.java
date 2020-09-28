package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whackamole.ui.main.ScoreViewModel;

import static android.view.View.INVISIBLE;

public class MainActivity extends AppCompatActivity {

    private ScoreViewModel scoreViewModel;
    private Observer<Integer> scoreObserver;
    private Handler handler;
    private Runnable runnable;
    private int endGameTime = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        scoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        TextView score = (TextView) findViewById(R.id.current_score);
        TextView hi_score = (TextView) findViewById(R.id.high_score);

        score.setText(0);

        scoreObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                hi_score.setText(integer);
            }
        };
        scoreViewModel.getHighScore().observe(this, scoreObserver);

        ImageView m1 = (ImageView) findViewById(R.id.mole1);
        ImageView m2 = (ImageView) findViewById(R.id.mole2);
        ImageView m3 = (ImageView) findViewById(R.id.mole3);
        ImageView m4 = (ImageView) findViewById(R.id.mole4);
        ImageView m5 = (ImageView) findViewById(R.id.mole5);
        ImageView m6 = (ImageView) findViewById(R.id.mole6);
        ImageView m7 = (ImageView) findViewById(R.id.mole7);
        ImageView m8 = (ImageView) findViewById(R.id.mole8);
        ImageView m9 = (ImageView) findViewById(R.id.mole9);

        Button start_game_button = (Button) findViewById(R.id.start_button);

        start_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_game_button.setClickable(false);
                start_game_button.setVisibility(INVISIBLE);
                startGame();
            }
        });



    }

    // Step 1. Game Starts -> Moles start appearing (Handler and Runnable for moles appearing + score?)
    // Step 1a. make moles clickable and visible to appear, then in the onclicklistener change them back to invisible and non clickable
    // Step 2. Game continues -> Keeps track of number of misses (3 strikes you're out)
    // Step 3. Every 20 seconds the game gets progressively faster and faster
    // Step 4. End of game, it displays

    // Question: How to increase the time and appearance of mole? Tie it together with variable and update the runnable postdelay?
    // Question:

    public void startGame() {
        int final_score = 0;
        Long startTime = SystemClock.uptimeMillis();
        runnable = new Runnable() {
            @Override
            public void run() {
                long diff = SystemClock.uptimeMillis() - startTime;
                if(diff >= endGameTime){
                    endGame(final_score);
                }
                handler.postDelayed(this, 10);
            }
        };
    }

    public void endGame(int score) {

    }
}