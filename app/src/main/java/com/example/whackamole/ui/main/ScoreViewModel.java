package com.example.whackamole.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ScoreViewModel extends ViewModel {
    public MutableLiveData<Integer> getHighScore() {
        return highScore;
    }

    public void setHighScore(MutableLiveData<Integer> highScore) {
        this.highScore = highScore;
    }

    private MutableLiveData<Integer> highScore;
}