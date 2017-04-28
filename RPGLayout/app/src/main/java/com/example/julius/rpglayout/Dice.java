package com.example.julius.rpglayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

public class Dice extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        Button button4 = (Button) findViewById(R.id.btn_4);
        Button button6 = (Button) findViewById(R.id.btn_6);
        Button button10 = (Button) findViewById(R.id.btn_10);
        Button button12 = (Button) findViewById(R.id.btn_12);
        Button button20 = (Button) findViewById(R.id.btn_20);
        Button button2 = (Button) findViewById(R.id.btn_0);
        final TextView all = (TextView) findViewById(R.id.all);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int min = 1;
                int max = 4;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                all.setText(new StringBuilder().append(random));
            }
        });

        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int min = 1;
                int max = 6;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                all.setText(new StringBuilder().append(random));
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int min = 1;
                int max = 10;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                all.setText(new StringBuilder().append(random));
            }
        });

        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int min = 1;
                int max = 12;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                all.setText(new StringBuilder().append(random));
            }
        });
        button20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int min = 1;
                int max = 20;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                all.setText(new StringBuilder().append(random));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int min = 0;
                int max = 1;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                all.setText(new StringBuilder().append(random));
            }
        });

    }

}
