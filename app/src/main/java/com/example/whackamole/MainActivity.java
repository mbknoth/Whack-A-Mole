package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whackamole.ui.main.ScoreViewModel;

public class MainActivity extends AppCompatActivity {

    private ScoreViewModel scoreViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        TextView score = (TextView) findViewById(R.id.current_score);

        ImageView m1 = (ImageView) findViewById(R.id.mole1);
        ImageView m2 = (ImageView) findViewById(R.id.mole2);
        ImageView m3 = (ImageView) findViewById(R.id.mole3);
        ImageView m4 = (ImageView) findViewById(R.id.mole4);
        ImageView m5 = (ImageView) findViewById(R.id.mole5);
        ImageView m6 = (ImageView) findViewById(R.id.mole6);
        ImageView m7 = (ImageView) findViewById(R.id.mole7);
        ImageView m8 = (ImageView) findViewById(R.id.mole8);
        ImageView m9 = (ImageView) findViewById(R.id.mole9);

        scoreViewModel = new ViewModelProvider(this).get(ScoreViewModel.class);


    }
}