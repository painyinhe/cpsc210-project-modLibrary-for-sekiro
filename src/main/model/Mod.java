package model;

import org.json.JSONObject;

import persistence.Writable;

public class Mod implements Writable {
    
    private String name;
    private String author;
    private String introduction;

    public Mod(String name) {
        this.name = name;
        author = "Unknown";
        introduction = "The uploader haven't add any introduction";
    }

    public Mod(JSONObject json) {
        this.name = json.getString("name");
        this.author = json.getString("author");
        this.introduction = json.getString("introduction");
    }



    //MODIFIES: this
    //EFFECTS: ADD INFORMATION TO THE MOD.
    public void reviseIntroduction(String introduction) {
        this.introduction = introduction;
    }

    //MODIFIES: this
    //EFFECTS: CHANGE THE NAME OF THE AUTHOR
    public void changeAuthor(String author) {
        this.author = author;
    }

    // EFFECTS: turn this Mod into a JSONObject for saving
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("author", author);
        json.put("introduction", introduction);
        return json;
    }


    //GETTERS
    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getIntroduction() {
        return introduction;
    }
}
