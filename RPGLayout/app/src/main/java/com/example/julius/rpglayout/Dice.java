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

        Button button6 = (Button) findViewById(R.id.btn_6);
        Button button10 = (Button) findViewById(R.id.btn_10);
        Button button12 = (Button) findViewById(R.id.btn_dice);
        final TextView sixsides = (TextView) findViewById(R.id.txt_6);
        final TextView tensides = (TextView) findViewById(R.id.txt_10);
        final TextView twelve = (TextView) findViewById(R.id.txt_12);

        final TextView all = (TextView) findViewById(R.id.all);


        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int min = 1;
                int max = 6;
                int max2 = 10;
                int max3 = 12;
                int max4 = 20;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                int random2 = ThreadLocalRandom.current().nextInt(min, max2+1);
                int random3 = ThreadLocalRandom.current().nextInt(min, max3+1);
                int random4 = ThreadLocalRandom.current().nextInt(min, max4+1);
                sixsides.setText(new StringBuilder().append(random));
                tensides.setText(new StringBuilder().append(random2));
                twelve.setText(new StringBuilder().append(random3));
                all.setText(new StringBuilder().append(random));*/

                int min = 1;
                int max = 6;
                int max2 = 10;
                int max3 = 12;
                int max4 = 20;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                all.setText(new StringBuilder().append(random));
            }
        });

        button10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int min = 1;
                int max = 6;
                int max2 = 10;
                int max3 = 12;
                int max4 = 20;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                int random2 = ThreadLocalRandom.current().nextInt(min, max2+1);
                int random3 = ThreadLocalRandom.current().nextInt(min, max3+1);
                int random4 = ThreadLocalRandom.current().nextInt(min, max4+1);
                sixsides.setText(new StringBuilder().append(random));
                tensides.setText(new StringBuilder().append(random2));
                twelve.setText(new StringBuilder().append(random3));
                all.setText(new StringBuilder().append(random));*/

                int min = 1;
                int max = 10;
                int max2 = 10;
                int max3 = 12;
                int max4 = 20;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                all.setText(new StringBuilder().append(random));
            }
        });
        button12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* int min = 1;
                int max = 6;
                int max2 = 10;
                int max3 = 12;
                int max4 = 20;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                int random2 = ThreadLocalRandom.current().nextInt(min, max2+1);
                int random3 = ThreadLocalRandom.current().nextInt(min, max3+1);
                int random4 = ThreadLocalRandom.current().nextInt(min, max4+1);
                sixsides.setText(new StringBuilder().append(random));
                tensides.setText(new StringBuilder().append(random2));
                twelve.setText(new StringBuilder().append(random3));
                all.setText(new StringBuilder().append(random));*/

                int min = 1;
                int max = 20;
                int max2 = 10;
                int max3 = 12;
                int max4 = 20;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                all.setText(new StringBuilder().append(random));
            }
        });
    }

}
