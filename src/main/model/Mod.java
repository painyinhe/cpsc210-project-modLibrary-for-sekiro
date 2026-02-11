package model;

public class Mod {
    
    private String name;
    private String author;
    private String introduction;

    public Mod(String name) {
        this.name = name;
        author = "Unknown";
        introduction = "The uploader haven't add any introduction";
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
