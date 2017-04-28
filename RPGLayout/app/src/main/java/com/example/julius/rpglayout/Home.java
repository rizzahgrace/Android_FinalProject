package com.example.julius.rpglayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_home);

        Button dice = (Button) findViewById(R.id.btn_roll);
        dice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent testdice = new Intent(Home.this,Dice.class);
                startActivity(testdice);
            }
        });
        Button view = (Button) findViewById(R.id.btn_chara);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chara = new Intent(Home.this,ViewChar.class);
                startActivity(chara);
            }
        });
        Button chat = (Button) findViewById(R.id.btn_chat);
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chat = new Intent(Home.this,Chat.class);
                startActivity(chat);
            }
        });

        Button out = (Button) findViewById(R.id.btn_exit);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent out = new Intent(Home.this,MainActivity.class);
                startActivity(out);
            }
        });
    }
}
