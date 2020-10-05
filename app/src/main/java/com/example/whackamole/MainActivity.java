package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whackamole.ui.main.MoleViewModel;
import com.example.whackamole.ui.main.ScoreViewModel;

import static android.view.View.INVISIBLE;

/**
 * The main activity for the Whack-a-mole project - this activity is the main playing screen for the
 * game and keeps track of the score, high score, number of misses, and what moles are going to appear
 *
 * @author mbknoth
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The Score ViewModel used to reference the highscore and current score
     */
    private ScoreViewModel scoreViewModel;

    /**
     * The Mole ViewModel used to reference the appearance of moles and which
     * ones are being randomly selected
     */
    private MoleViewModel moleViewModel;

    /**
     * The observer used to watch the MutableLiveData of the current score
     */
    private Observer<Integer> scoreObserver;

    /**
     * The observer used to watch the MutableLiveData of the high score
     */
    private Observer<Integer> highScoreObserver;

    /**
     * The observer used to watch which Mole index is being selected
     */
    private Observer<Integer> moleObserver;

    /**
     * The observer used to watch the visibility of the mole at selected index
     */
    private  Observer<Integer> moleVisibilityObserver;

    /**
     * The observer used to watch how many misses the user records
     */
    private Observer<Integer> endGameObserver;

    /**
     * Global variable of the "chosen" random mole to appear on screen
     */
    private ImageView showMoleHere;

    //Global declaration of all the moles found on screen from 1-9
    private ImageView m1;
    private ImageView m2;
    private ImageView m3;
    private ImageView m4;
    private ImageView m5;
    private ImageView m6;
    private ImageView m7;
    private ImageView m8;
    private ImageView m9;

    /**
     * Global variable used to keep track of how many misses the player currently has
     */
    private Integer missCount;

    /**
     * The method that is called for the creation of the Main screen of the Whack-a-mole game
     * This initializes all of the global variables and finds all the view objects from the
     * main activity
     *
     * @param savedInstanceState a saved instance of state (not being used)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        //Used for playing sound when mole is tapped on
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.hit);

        scoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);

        missCount = 0;

        TextView score = (TextView) findViewById(R.id.current_score);
        score.setText(R.string.game_default_current_score);

        TextView hi_score = (TextView) findViewById(R.id.high_score);

        //getting saved state from old games - it is getting this from when
        //the end game activity moves to this one. Had to go this route because
        //it is hard to have the same ViewModelStore shared between activities.
        //I would've had to use a ViewModelFactory but given circumstances
        //I felt that this was the better work around
        Bundle extras = getIntent().getExtras();
        SharedPreferences pref = getSharedPreferences("highScore", MODE_PRIVATE);
        if (extras != null) {
            int high_score_prev = extras.getInt("High Score");
            MutableLiveData<Integer> high_score_prev_mutable = new MutableLiveData<Integer>();
            high_score_prev_mutable.setValue(high_score_prev);
            scoreViewModel.setHighScore(high_score_prev_mutable);
        }
        //For persistent data, storing the high score value using shared preferences
        //Referencing the key value pair that is stored at the end of the game and
        //updating the viewmodel to contain the correct high score value, even after
        //game is over and app is force exited
        else if(pref.contains("game_high_score")){
            int high_score_prev = pref.getInt("game_high_score", 0);
            MutableLiveData<Integer> high_score_prev_mutable = new MutableLiveData<Integer>();
            high_score_prev_mutable.setValue(high_score_prev);
            scoreViewModel.setHighScore(high_score_prev_mutable);
        }

        highScoreObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                String new_high_score = getString(R.string.game_hi_score, integer);
                hi_score.setText(new_high_score);
            }
        };
        scoreViewModel.getHighScore().observe(this, highScoreObserver);

        //All of the mole image views on screen
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
        start_game_button.setText(R.string.start_game);

        // this is the button that activates the handler and runnables in the mole view model
        // and in turn "starts" the game
        start_game_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_game_button.setClickable(false);
                start_game_button.setVisibility(INVISIBLE);
                score.setVisibility(View.VISIBLE);
                startGame();
            }
        });

        //The mole on click listeners when they are clickable and visible
        //Once the onClick method is called, the mole will become invisible and not clickable
        //We update the mole visibility array in the mole view model and follow by upping the score
        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m1.setVisibility(INVISIBLE);
                m1.setClickable(false);
                moleViewModel.setMolesVisibleAtIndex(0, false);

                mp.start();
                upScore();
            }
        });

        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m2.setVisibility(INVISIBLE);
                m2.setClickable(false);
                moleViewModel.setMolesVisibleAtIndex(1, false);

                mp.start();
                upScore();
            }
        });

        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m3.setVisibility(INVISIBLE);
                m3.setClickable(false);
                moleViewModel.setMolesVisibleAtIndex(2, false);

                mp.start();
                upScore();
            }
        });

        m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m4.setVisibility(INVISIBLE);
                m4.setClickable(false);
                moleViewModel.setMolesVisibleAtIndex(3, false);

                mp.start();
                upScore();
            }
        });

        m5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m5.setVisibility(INVISIBLE);
                m5.setClickable(false);
                moleViewModel.setMolesVisibleAtIndex(4, false);

                mp.start();
                upScore();
            }
        });

        m6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m6.setVisibility(INVISIBLE);
                m6.setClickable(false);
                moleViewModel.setMolesVisibleAtIndex(5, false);

                mp.start();
                upScore();
            }
        });

        m7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m7.setVisibility(INVISIBLE);
                m7.setClickable(false);
                moleViewModel.setMolesVisibleAtIndex(6, false);


                mp.start();
                upScore();
            }
        });

        m8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m8.setVisibility(INVISIBLE);
                m8.setClickable(false);
                moleViewModel.setMolesVisibleAtIndex(7, false);


                mp.start();
                upScore();
            }
        });

        m9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m9.setVisibility(INVISIBLE);
                m9.setClickable(false);
                moleViewModel.setMolesVisibleAtIndex(8, false);


                mp.start();
                upScore();
            }
        });
    }

    /**
     * This is the public helper method that is used to be in charge of upping the
     * players score when they successfully tap a mole within the given time of visibility
     * <p>
     * It makes calls to the Score ViewModel for updates as well as using the information
     * to update the UI with the scoreObserver
     */
    public void upScore() {
        int currentScore = scoreViewModel.getCurrentScore().getValue();
        MutableLiveData<Integer> updatedScore = new MutableLiveData<Integer>();
        updatedScore.setValue(currentScore + 1);

        scoreViewModel.setCurrentScore(updatedScore);

        //updating the UI with observer
        scoreObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                String text = getString(R.string.game_current_score, integer);
                TextView score = (TextView) findViewById(R.id.current_score);
                score.setText(text);
            }
        };
        scoreViewModel.getCurrentScore().observe(this, scoreObserver);

    }

    /**
     * Public helper method to initialize the start of the game and have all that
     * logic centralized into one method. This method updates which moles are randomly selected
     * and also keeps track of the miss count and when the miss count is above 3, this method
     * calls the game to end.
     *
     * This method also watches over which moles have reached their allowed time to be on screen
     * and updates the UI to take them away as the game will count this as a miss.
     */
    public void startGame() {
        moleViewModel = new ViewModelProvider(this).get(MoleViewModel.class);
        moleViewModel.runGame();
        moleViewModel.keepTrackOfMoles();

        //used to gather which mole is randomly selected from the Mole ViewModel
        // and set that mole to be visible and clickable
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
                showMoleHere.setVisibility(View.VISIBLE);
                showMoleHere.setClickable(true);
            }
        };
        moleViewModel.getRandomMole().observe(this, moleObserver);

        moleVisibilityObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer index) {
                switch (index){
                    case 0:
                        ImageView mole1 = (ImageView) findViewById(R.id.mole1);
                        mole1.setVisibility(INVISIBLE);
                        mole1.setClickable(false);
                        break;
                    case 1:
                        ImageView mole2 = (ImageView) findViewById(R.id.mole2);
                        mole2.setVisibility(INVISIBLE);
                        mole2.setClickable(false);
                        break;
                    case 2:
                        ImageView mole3 = (ImageView) findViewById(R.id.mole3);
                        mole3.setVisibility(INVISIBLE);
                        mole3.setClickable(false);
                        break;
                    case 3:
                        ImageView mole4 = (ImageView) findViewById(R.id.mole4);
                        mole4.setVisibility(INVISIBLE);
                        mole4.setClickable(false);
                        break;
                    case 4:
                        ImageView mole5 = (ImageView) findViewById(R.id.mole5);
                        mole5.setVisibility(INVISIBLE);
                        mole5.setClickable(false);
                        break;
                    case 5:
                        ImageView mole6 = (ImageView) findViewById(R.id.mole6);
                        mole6.setVisibility(INVISIBLE);
                        mole6.setClickable(false);
                        break;
                    case 6:
                        ImageView mole7 = (ImageView) findViewById(R.id.mole7);
                        mole7.setVisibility(INVISIBLE);
                        mole7.setClickable(false);
                        break;
                    case 7:
                        ImageView mole8 = (ImageView) findViewById(R.id.mole8);
                        mole8.setVisibility(INVISIBLE);
                        mole8.setClickable(false);
                        break;
                    case 8:
                        ImageView mole9 = (ImageView) findViewById(R.id.mole9);
                        mole9.setVisibility(INVISIBLE);
                        mole9.setClickable(false);
                        break;
                }
            }
        };
        moleViewModel.getHiddenMoleIndex().observe(this, moleVisibilityObserver);

        //used to reference how many misses the player has in the game. If above
        // the threshold the game is called to end
        endGameObserver = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                missCount = integer;
                if (missCount >= 3) {
                    endGame();
                }
            }
        };
        moleViewModel.getMissCount().observe(this, endGameObserver);
    }

    /**
     * The public helper method that is used to end the game when the player has missed more than
     * the game will allow. The current player's score and high score are compared and updated accordingly.
     *
     * This method will send the user to the End Game Activity to show the user a summery of how they did
     * The current score in the view model is reset to be prepared for the next game
     *
     * The all-time high score of the game is stored in Shared Preferences, that way when the game is closed
     * the high score will persist and be available when app is opened. Storing the high score with the key value
     * relationship that comes with Shared Preferences
     */
    public void endGame() {
        moleViewModel.endGame();

        if (scoreViewModel.getCurrentScore().getValue() > scoreViewModel.getHighScore().getValue()) {
            MutableLiveData<Integer> new_high_score = scoreViewModel.getCurrentScore();
            scoreViewModel.setHighScore(new_high_score);
        }
        SharedPreferences pref = getSharedPreferences("highScore", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("game_high_score", scoreViewModel.getHighScore().getValue());
        editor.commit();

        Intent myIntent = new Intent(getBaseContext(), EndGameActivity.class);
        myIntent.putExtra("Current Score", scoreViewModel.getCurrentScore().getValue());
        myIntent.putExtra("High Score", scoreViewModel.getHighScore().getValue());

        scoreViewModel.resetCurrentScore();
        startActivity(myIntent);
    }
}