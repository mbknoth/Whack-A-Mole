package com.example.whackamole.ui.main;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class MoleViewModel extends ViewModel {

    private MutableLiveData<Integer> moleIndex;
    private MutableLiveData<Integer> missCount;
    private MutableLiveData<Integer> hideMoleIndex;

    private Integer moleDelay;
    private Handler handler;

    private boolean[] molesVisible;

    public MoleViewModel() {
        this.moleDelay = 500;
        missCount = new MutableLiveData<Integer>();
        moleIndex = new MutableLiveData<Integer>();
        hideMoleIndex = new MutableLiveData<Integer>();

        missCount.setValue(0);
        hideMoleIndex.setValue(-1);

        handler = new Handler();

        molesVisible = new boolean[10];
        for (int i = 0; i < molesVisible.length; i++) {
            molesVisible[i] = false;
        }
    }

    public MutableLiveData<Integer> getMissCount() {
        return missCount;
    }

    public void setMissCount(MutableLiveData<Integer> missCount){
        this.missCount = missCount;
    }

    public Integer getMoleDelay() {
        return moleDelay;
    }

    public void setMoleDelay(Integer moleDelay) {
        if (moleDelay <= 0) {
            this.moleDelay = 0;
        } else {
            this.moleDelay = moleDelay;
        }
    }

    public void setMolesVisibleAtIndex(int index, boolean visibility) {
        molesVisible[index] = visibility;
    }

    public boolean getMolesVisibleAtIndex(int index) {
        return this.molesVisible[index];
    }

    public MutableLiveData<Integer> getHiddenMoleIndex(){
        return this.hideMoleIndex;
    }

    public void setHiddenMoleIndex(int index){
        hideMoleIndex.setValue(index);
    }

    public MutableLiveData<Integer> getRandomMole() {
        return moleIndex;
    }

    public void runGame() {
        Runnable runnable = new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count < 25) {
                    int randNum = new Random(System.currentTimeMillis()).nextInt(9);
                    if (!getMolesVisibleAtIndex(randNum)) {
                        moleIndex.setValue(randNum);
                        setMolesVisibleAtIndex(randNum, true);
                    }
                    count++;
                } else {
                    count = 0;
                    setMoleDelay(moleDelay - 10);
                }
                handler.postDelayed(this, getMoleDelay());
            }
        };
        handler.postAtTime(runnable, SystemClock.uptimeMillis());
    }

    public void keepTrackOfMoles() {
        Runnable runnable = new Runnable() {
            int[] count = new int[10];

            @Override
            public void run() {
                for (int i = 0; i < count.length; i++) {
                    if (getMolesVisibleAtIndex(i)) {
                        if (count[i] >= 10) {
                            missCount.setValue(missCount.getValue() + 1);
                            setMolesVisibleAtIndex(i, false);
                            setHiddenMoleIndex(i);
                            count[i] = 0;
                        } else {
                            count[i]++;
                        }
                    }
                }

                handler.postDelayed(this, getMoleDelay());
            }
        };
        handler.postAtTime(runnable, SystemClock.uptimeMillis());
    }

    public void endGame(){
        handler.removeCallbacksAndMessages(null);
    }

}