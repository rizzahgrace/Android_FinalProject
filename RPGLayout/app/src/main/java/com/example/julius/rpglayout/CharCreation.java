package com.example.julius.rpglayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

public class CharCreation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_creation);
        Button buttongen = (Button) findViewById(R.id.btn_gen);
        final TextView all = (TextView) findViewById(R.id.all);
        buttongen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int min = 1;
                int max = 4;
                int random = ThreadLocalRandom.current().nextInt(min, max+1);
                all.setText(new StringBuilder().append(random));
            }
        });

        Button next = (Button) findViewById(R.id.btn_next2);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reg = new Intent(CharCreation.this,BackStory.class);
                startActivity(reg);
            }
        });
    }
}
