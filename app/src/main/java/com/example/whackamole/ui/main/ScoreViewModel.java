package com.example.whackamole.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {
    /**
     * Mutable current score variable
     */
    private MutableLiveData<Integer> currentScore;

    /**
     * Mutable high score variable
     */
    private MutableLiveData<Integer> highScore;

    /**
     * Getter for high score
     * @return
     *  high score
     */
    public MutableLiveData<Integer> getHighScore() {
        return highScore;
    }

    /**
     * setter for high score
     * @param highScore
     *  the value to update the high score
     */
    public void setHighScore(MutableLiveData<Integer> highScore) {
        this.highScore = highScore;
    }

    /**
     * getter for current score
     * @return
     *  the current score of that specific game
     */
    public MutableLiveData<Integer> getCurrentScore() {
        return currentScore;
    }

    public void setCurrentScore(MutableLiveData<Integer> currentScore) {
        this.currentScore = currentScore;
    }

    /**
     * Resets the games current score back to zero
     */
    public void resetCurrentScore(){
        this.currentScore.setValue(0);
    }


}