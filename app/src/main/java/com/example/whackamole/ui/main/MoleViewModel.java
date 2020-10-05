package com.example.whackamole.ui.main;

import android.os.Handler;
import android.os.SystemClock;
import android.widget.ImageView;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

/**
 * This ViewModel will be used across the Main Activity in order to achieve
 * the functionality of running the game, the population of moles on screen,
 * and keeping track of number of misses a player has in a game. When the game
 * continues on and the player is still under the allowed number of misses, the game
 * will start to slowly speed up and become faster and faster, and the moles
 * will appear more rapidly and disappear just as fast
 *
 * @author mbknoth
 */
public class MoleViewModel extends ViewModel {

    /**
     * The index of the mole that is chosen to appear on screen
     */
    private MutableLiveData<Integer> moleIndex;

    /**
     * The number of misses a player has in a game
     */
    private MutableLiveData<Integer> missCount;

    /**
     * The index of the mole that has reached its lifespan and must be removed off screen
     */
    private MutableLiveData<Integer> hideMoleIndex;

    /**
     * The global timing variable used in the speeding up process of the game.
     * Will constantly be decremented to make the delays shorter and shorter the longer
     * the game goes on.
     */
    private Integer moleDelay;

    /**
     * Global handler used when working with the games Runnables
     */
    private Handler handler;

    /**
     * This boolean array is used as a reference to the states of the moles on screen.
     * Each index matches to that corresponding mole on screen. If the value is TRUE,
     * then the mole is visible on screen. If FALSE, the mole is not visible on screen
     */
    private boolean[] molesVisible;

    /**
     * Public constructor of the Mole ViewModel
     *
     * Initializes all global variables values
     */
    public MoleViewModel() {
        this.moleDelay = 500;
        missCount = new MutableLiveData<Integer>();
        moleIndex = new MutableLiveData<Integer>();
        hideMoleIndex = new MutableLiveData<Integer>();

        missCount.setValue(0);

        //Set to -1 to indicate no index currently chosen
        hideMoleIndex.setValue(-1);

        handler = new Handler();

        molesVisible = new boolean[10];
        for (int i = 0; i < molesVisible.length; i++) {
            molesVisible[i] = false;
        }
    }

    /**
     * Getter for Miss Count
     * @return
     *  the miss count of the players current game
     */
    public MutableLiveData<Integer> getMissCount() {
        return missCount;
    }

    /**
     * Setter for the Miss Count
     * @param missCount
     *  The current number of misses the player has
     */
    public void setMissCount(MutableLiveData<Integer> missCount){
        this.missCount = missCount;
    }

    /**
     * Getter for Mole Delay
     * @return
     *  Mole Delay AKA the timing delay variable used in the Runnables
     */
    public Integer getMoleDelay() {
        return moleDelay;
    }

    /**
     * Setter for the Mole Delay
     * @param moleDelay
     *  New value for Mole Delay - Smaller the number the faster the game play, bigger the number
     *  the slower the game play
     */
    public void setMoleDelay(Integer moleDelay) {
        if (moleDelay <= 0) {
            this.moleDelay = 0;
        } else {
            this.moleDelay = moleDelay;
        }
    }

    /**
     * Setter of the moles visibility on screen indicated by their corresponding index
     * @param index
     *  index of mole to be updated
     * @param visibility
     *  visibility of mole, true = Visible, false = Invisible
     */
    public void setMolesVisibleAtIndex(int index, boolean visibility) {
        molesVisible[index] = visibility;
    }

    /**
     * Getter for Mole Visibility at Index
     * @param index
     *  Index of mole
     * @return
     *  The visibility of the mole at index
     */
    public boolean getMolesVisibleAtIndex(int index) {
        return this.molesVisible[index];
    }

    /**
     * Getter for mole that needs to be hidden
     * @return
     *  Mole that needs to be taken off screen
     */
    public MutableLiveData<Integer> getHiddenMoleIndex(){
        return this.hideMoleIndex;
    }

    /**
     * Setter for the index of the mole that needs to be taken off screen
     * @param index
     *  Index of chosen mole
     */
    public void setHiddenMoleIndex(int index){
        hideMoleIndex.setValue(index);
    }

    /**
     * Getter for the random mole to appear on screen
     * @return
     *  The index of the random mole to appear of screen - random part comes from runGame method
     */
    public MutableLiveData<Integer> getRandomMole() {
        return moleIndex;
    }

    /**
     * Public helper method that is in charge of running the game and speeding up the game
     * when the player has gone thru a certain amount of clock cycles. This helper method is in
     * charge of updating the randomly chosen mole's index which is read by the Main Activity
     */
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

    /**
     * Public helper method that is used to keep track of the number of misses the player has
     * in a game. If the mole is not pressed for more than 10 clock cycles, then the mole will
     * be taken off and counted as a loss
     */
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

    /**
     * Public helper method that ends all runnables when the game has ended
     */
    public void endGame(){
        handler.removeCallbacksAndMessages(null);
    }

}