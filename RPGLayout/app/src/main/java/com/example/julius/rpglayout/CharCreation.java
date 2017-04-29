package com.example.julius.rpglayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

public class CharCreation extends AppCompatActivity {
    TextView strength;
    TextView intelligence;
    TextView dexterity;
    TextView constitution;
    TextView wisdom;
    TextView charisma;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_char_creation);

        Button buttonGen = (Button) findViewById(R.id.btn_gen);
        strength = (TextView) findViewById(R.id.txt_str);
        intelligence = (TextView) findViewById(R.id.txt_int);
        dexterity = (TextView) findViewById(R.id.txt_dex);
        constitution = (TextView) findViewById(R.id.txt_con);
        wisdom = (TextView) findViewById(R.id.txt_wis);
        charisma = (TextView) findViewById(R.id.txt_cha);

        buttonGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForStats stat = new ForStats();
                strength.setText(stat.roll(3,6));
                intelligence.setText(stat.roll(3,6));
                dexterity.setText(stat.roll(3,6));
                constitution.setText(stat.roll(3,6));
                wisdom.setText(stat.roll(3,6));
                charisma.setText(stat.roll(3,6));
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
