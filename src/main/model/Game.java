package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

public class Game implements Writable {
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

    // EFFECTS: turn this Game into a JSONObject for saving
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);

        JSONArray jsonMods = new JSONArray();
        for (Mod m : mods) {
            jsonMods.put(m.toJson());
        }
        json.put("mods", jsonMods);

        return json;
    }

    //Getters
    public ArrayList<Mod> getMods() {
        return mods;
    }

    public String getName() {
        return name;
    }
}
