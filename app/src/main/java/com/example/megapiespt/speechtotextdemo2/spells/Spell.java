package com.example.megapiespt.speechtotextdemo2.spells;

import java.util.ArrayList;

/**
 * Created by MegapiesPT on 2/2/2560.
 */

public class Spell {
    protected ArrayList<String> words = new ArrayList<>();

    public boolean check(String spell){
        for(String word : words){
            if(word.equalsIgnoreCase(spell))
                return true;
        }
        return false;
    }
}
