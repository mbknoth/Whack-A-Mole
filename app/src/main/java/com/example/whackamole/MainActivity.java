package com.example.whackamole;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        TextView score = (TextView) findViewById(R.id.score);

        ImageView m1 = (ImageView) findViewById(R.id.mole1);
        ImageView m2 = (ImageView) findViewById(R.id.mole2);
        ImageView m3 = (ImageView) findViewById(R.id.mole3);
        ImageView m4 = (ImageView) findViewById(R.id.mole4);
        ImageView m5 = (ImageView) findViewById(R.id.mole5);
        ImageView m6 = (ImageView) findViewById(R.id.mole6);
        ImageView m7 = (ImageView) findViewById(R.id.mole7);
        ImageView m8 = (ImageView) findViewById(R.id.mole8);
        ImageView m9 = (ImageView) findViewById(R.id.mole9);




    }
}