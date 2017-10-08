package com.example.megapiespt.speechtotextdemo2;

import com.example.megapiespt.speechtotextdemo2.spells.CameraSpell;
import com.example.megapiespt.speechtotextdemo2.spells.CloseSpell;
import com.example.megapiespt.speechtotextdemo2.spells.FacebookSpell;
import com.example.megapiespt.speechtotextdemo2.spells.FlashLightSpell;
import com.example.megapiespt.speechtotextdemo2.spells.GallerySpell;
import com.example.megapiespt.speechtotextdemo2.spells.Spell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by MegapiesPT on 2/2/2560.
 */

public class SpellChecker {
    public static final String NONE = "None";
    public static final String FLASH_LIGHT = "Flash Light";
    public static final String CLOSE = "Close";
    public static final String CAMERA = "Camera";
    public static final String FACEBOOK = "Facebook";
    public static final String GALLERY = "Gallery";

    private Map<String, Spell> spells = new HashMap<>();

    public SpellChecker(){
        spells.put(FLASH_LIGHT, new FlashLightSpell());
        spells.put(CLOSE, new CloseSpell());
        spells.put(CAMERA, new CameraSpell());
        spells.put(FACEBOOK, new FacebookSpell());
        spells.put(GALLERY, new GallerySpell());
    }
    public String castSpell(ArrayList<String> spells){
        for(String spell : spells){
            if(this.spells.get(FLASH_LIGHT).check(spell))
                return FLASH_LIGHT;
            else if(this.spells.get(CLOSE).check(spell))
                return CLOSE;
            else if(this.spells.get(CAMERA).check(spell))
                return CAMERA;
            else if(this.spells.get(FACEBOOK).check(spell))
                return FACEBOOK;
            else if(this.spells.get(GALLERY).check(spell))
                return GALLERY;
        }
        return NONE;
    }



}
