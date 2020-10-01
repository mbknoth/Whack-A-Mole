package com.example.whackamole.ui.main;

import android.os.Handler;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class MoleViewModel extends ViewModel {
    /**
     * Mutable current score variable
     */
    private MutableLiveData<Boolean> moleOnScreen;
    private MutableLiveData<Integer> moleIndex;

    private Integer moleDelay;
    private Handler handler;

    public MoleViewModel() {
        this.moleDelay = 50;
    }

    public Integer getMoleDelay() {
        return moleDelay;
    }

    public void setMoleDelay(Integer moleDelay) {
        if(moleDelay <= 0){
            this.moleDelay = 0;
        }
        else {
            this.moleDelay = moleDelay;
        }
    }

    public void runGame(){
        Runnable runnable = new Runnable() {
            int count = 0;
            @Override
            public void run() {
                if(count < 10){
                    int randNum = new Random(System.currentTimeMillis()).nextInt(8);
                    moleIndex.setValue(randNum);
                }
                else{
                    count = 0;
                    moleDelay = moleDelay - 5;
                    setMoleDelay(moleDelay);
                }
                handler.postDelayed(this, moleDelay);
            }
        };

    }

    public MutableLiveData<Boolean> showMoleOnScreen(){
        return moleOnScreen;
    }

    public void setMoleOnScreen(Boolean isVisible){
        moleOnScreen.setValue(isVisible);
    }

    public MutableLiveData<Integer> getRandomMole(){
        return moleIndex;
    }

}