package com.example.julius.rpglayout;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewChar extends AppCompatActivity {
    ArrayList<Character> mCharList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_char);

        Intent receivedIntent = getIntent();

        final String username = receivedIntent.getStringExtra("USERNAME");
        System.out.println("LUH " + username);


        DNDApp app = (DNDApp) getApplication();

        app.loadCharacterStats(username);

        mCharList = app.getCharList();

        String charName = mCharList.get(0).getCharname();

        String str = Integer.toString(mCharList.get(0).getStrength());
        String inte = Integer.toString(mCharList.get(0).getIntelligence());
        String dex = Integer.toString(mCharList.get(0).getDexterity());
        String con = Integer.toString(mCharList.get(0).getConstitution());
        String wis = Integer.toString(mCharList.get(0).getWisdom());
        String cha = Integer.toString(mCharList.get(0).getCharisma());

       /* String str = app.getCharStr();
        String inte = app.getCharInt();
        String dex = app.getCharDex();
        String con = app.getCharCon();
        String wis = app.getCharWis();
        String cha = app.getCharCha();*/

       TextView txvName = (TextView) findViewById(R.id.txt_vname);

        EditText txvStr = (EditText) findViewById(R.id.txt_vstr);
        EditText txvInt = (EditText) findViewById(R.id.txt_vint);
        EditText txvDex = (EditText) findViewById(R.id.txt_vdex);
        EditText txvCon = (EditText) findViewById(R.id.txt_vcon);
        EditText txvWis = (EditText) findViewById(R.id.txt_vwis);
        EditText txvCha = (EditText) findViewById(R.id.txt_vcha);


        txvName.setText(charName);
        txvStr.setText(str);
        txvInt.setText(inte);
        txvDex.setText(dex);
        txvCon.setText(con);
        txvWis.setText(wis);
        txvCha.setText(cha);

    }
}
