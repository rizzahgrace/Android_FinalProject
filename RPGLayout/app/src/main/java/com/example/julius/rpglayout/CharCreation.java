package com.example.julius.rpglayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ThreadLocalRandom;

public class CharCreation extends AppCompatActivity {
    Intent recvdIntent = getIntent();
    String mCharName = recvdIntent.getStringExtra("CHARNAME");
    TextView strength;
    TextView intelligence;
    TextView dexterity;
    TextView constitution;
    TextView wisdom;
    TextView charisma;


    int str, inte, dex, con, wis, cha;
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

                str = stat.roll(3,6);
                inte = stat.roll(3,6);
                dex = stat.roll(3,6);
                con = stat.roll(3,6);
                wis = stat.roll(3,6)
                cha = stat.roll(3,6);

                submitCharStats();

                strength.setText(Integer.toString(str));
                intelligence.setText(Integer.toString(inte));
                dexterity.setText(Integer.toString(dex));
                constitution.setText(Integer.toString(con));
                wisdom.setText(Integer.toString(wis));
                charisma.setText(Integer.toString(cha));
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

    private void submitCharStats() {

        DNDApp app = (DNDApp) getApplication();
        app.saveCharacterData(mCharName, str, inte, dex, con, wis, cha);
        finish();
        return;
    }
}
