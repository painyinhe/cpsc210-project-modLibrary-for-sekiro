package model;

import java.util.ArrayList;

public class Game {
    private String name;
    private ArrayList<Mod> mods;

    public Game(String name) {
        this.name = name;
        mods = new ArrayList<>();
    }

    // MODIFIES: THIS
    // EFFECTS: ADD A MOD WHICH IS NOT IN THE MOD LIST OF THIS GAME
    public void addMod(Mod mod){
        if (!mods.contains(mod)) {
            mods.add(mod);
        }
    }



    //Getters
    public ArrayList<Mod> getMods() {
        return mods;
    }

    public String getName() {
        return name;
    }
}
