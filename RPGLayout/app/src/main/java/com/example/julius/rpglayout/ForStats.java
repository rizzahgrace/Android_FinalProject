package com.example.julius.rpglayout;

import java.util.Random;


public class ForStats {
    private Random d = new Random();

    String roll(int dice, int side) {
        int adx = 0;
        for( int mDice = dice; mDice>0; mDice--){
            int r = d.nextInt(side - 1) + 1;
            adx = adx + r;
        }
        return Integer.toString(adx);
    }
}
