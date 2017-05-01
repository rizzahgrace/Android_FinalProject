package com.example.julius.rpglayout;

import android.widget.TextView;

/**
 * Created by Rizzah on 5/2/2017.
 */

public class Character {
    private String mUsername = "";
    private String mCharname = "";
    private int mstr = 0;
    private int mint = 0;
    private int mdex = 0;
    private int mcon = 0;
    private int mwis = 0;
    private int mchar = 0;

    public Character(String username, String charname,
                  int strength, int intelligence, int dexerity, int constitution,
                  int wisdom, int charisma) {
        mUsername = username;
        mCharname = charname;
        mstr = strength;
        mint = intelligence;
        mdex = dexerity;
        mcon = constitution;
        mwis = wisdom;
        mchar = charisma;

        return;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getCharname() {
        return mCharname;
    }

    public int getStrength() {
        return mstr;
    }

    public int getIntelligence() {
        return mint;
    }

    public int getDexterity() {
        return mdex;
    }
    public int getConstitution() {
        return mcon;
    }

    public int getWisdom() {
        return mwis;
    }

    public int getCharisma() {
        return mchar;
    }

}
