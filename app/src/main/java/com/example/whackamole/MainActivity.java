package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whackamole.ui.main.MoleViewModel;
import com.example.whackamole.ui.main.ScoreViewModel;

import java.util.Random;

import static android.view.View.INVISIBLE;

public class MainActivity extends AppCompatActivity {

    private ScoreViewModel scoreViewModel;
    private Observer<Integer> scoreObserver;

    private MoleViewModel moleViewModel;
    private Observer<Integer> moleObserver;

    private ImageView showMoleHere;
    private ImageView m1;
    private ImageView m2;
    private ImageView m3;
    private ImageView m4;
    private ImageView m5;
    private ImageView m6;
    private ImageView m7;
    private ImageView m8;
    private ImageView m9;
    private TextView score;

    private Integer missCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        scoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        missCount = 0;

        score = (TextView) findViewById(R.id.current_score);
        TextView hi_score = (TextView) findViewById(R.id.high_score);

        scoreObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                score.setText(integer);
            }
        };
        scoreViewModel.getCurrentScore().observe(this, scoreObserver);

        scoreObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                hi_score.setText(integer);
            }
        };
        scoreViewModel.getHighScore().observe(this, scoreObserver);

        m1 = (ImageView) findViewById(R.id.mole1);
        m2 = (ImageView) findViewById(R.id.mole2);
        m3 = (ImageView) findViewById(R.id.mole3);
        m4 = (ImageView) findViewById(R.id.mole4);
        m5 = (ImageView) findViewById(R.id.mole5);
        m6 = (ImageView) findViewById(R.id.mole6);
        m7 = (ImageView) findViewById(R.id.mole7);
        m8 = (ImageView) findViewById(R.id.mole8);
        m9 = (ImageView) findViewById(R.id.mole9);

        Button start_game_button = (Button) findViewById(R.id.start_button);

        start_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_game_button.setClickable(false);
                start_game_button.setVisibility(INVISIBLE);
                score.setVisibility(View.VISIBLE);
                startGame();
            }
        });

        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m1.setVisibility(INVISIBLE);
                m1.setClickable(false);

                upScore();
            }
        });

        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m2.setVisibility(INVISIBLE);
                m2.setClickable(false);

                upScore();
            }
        });

        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m3.setVisibility(INVISIBLE);
                m3.setClickable(false);

                upScore();
            }
        });

        m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m4.setVisibility(INVISIBLE);
                m4.setClickable(false);

                upScore();
            }
        });

        m5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m5.setVisibility(INVISIBLE);
                m5.setClickable(false);

                upScore();
            }
        });

        m6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m6.setVisibility(INVISIBLE);
                m6.setClickable(false);

                upScore();
            }
        });

        m7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m7.setVisibility(INVISIBLE);
                m7.setClickable(false);

                upScore();
            }
        });

        m8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m8.setVisibility(INVISIBLE);
                m8.setClickable(false);

                upScore();
            }
        });

        m9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m9.setVisibility(INVISIBLE);
                m9.setClickable(false);

                upScore();
            }
        });
    }

    public void upScore() {
        int currentScore = scoreViewModel.getCurrentScore().getValue();
        MutableLiveData<Integer> updatedScore = new MutableLiveData<Integer>();
        updatedScore.setValue(currentScore + 1);

        scoreViewModel.setCurrentScore(updatedScore);
        String text = getString(R.string.game_current_score, scoreViewModel.getCurrentScore().getValue());
        score.setText(text);
    }

    public void startGame() {
        moleViewModel = new ViewModelProvider(this).get(MoleViewModel.class);
        moleViewModel.runGame();
        moleViewModel.keepTrackOfMoles();

        moleObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer) {
                    case 0:
                        showMoleHere = m1;
                        break;
                    case 1:
                        showMoleHere = m2;
                        break;
                    case 2:
                        showMoleHere = m3;
                        break;
                    case 3:
                        showMoleHere = m4;
                        break;
                    case 4:
                        showMoleHere = m5;
                        break;
                    case 5:
                        showMoleHere = m6;
                        break;
                    case 6:
                        showMoleHere = m7;
                        break;
                    case 7:
                        showMoleHere = m8;
                        break;
                    case 8:
                        showMoleHere = m9;
                        break;
                }
            }
        };
        moleViewModel.getRandomMole().observe(this, moleObserver);
        showMoleHere.setVisibility(View.VISIBLE);
        showMoleHere.setClickable(true);

        moleObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                missCount = integer;
            }
        };
        moleViewModel.getMissCount().observe(this, moleObserver);
        if (missCount >= 3) {
            endGame();
        }

    }

    public void endGame() {
        Intent myIntent = new Intent(getBaseContext(), EndGameActivity.class);
        startActivity(myIntent);
    }
}