package ui;

import java.util.Scanner;

import model.Game;
import model.Mod;

import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

//ModsLibrary application
public class ModsLibrary {

    private Scanner input;
    private Game game;
    private static final String JSON_STORE = "./data/game.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public ModsLibrary() {
        ModsLibrary();
    }

    private void ModsLibrary() {
        boolean keepGoing = true;
        String command = null;

        init();

        while(keepGoing) {
            displayMenu();
            command = input.next();
            input.nextLine();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddMod();
        } else if (command.equals("v")) {
            doViewMod();
        } else if (command.equals("r")) {
            doReviseIntroduction();
        } else if (command.equals("s")) {
            doViewIntro();
        } else if (command.equals("c")) {
            doUpdateAuthor();
        } else if (command.equals("m")) {
            doViewAuthor();
        } else if (command.equals("p")) {
            doSaveGame();
        } else if (command.equals("l")) {
            doLoadGame();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    //MODIFIES: this
    //EFFECTS: initiates users command
    private void init() {
        input = new Scanner(System.in);
        game = new Game("Sekiro: shadow die twice");

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add one mod to a specific game");
        System.out.println("\tv -> view all mods of a game");
        System.out.println("\tr -> revise the introduction of a mod");
        System.out.println("\ts -> view the introduction of a mod");
        System.out.println("\tc -> change the author of a mod");
        System.out.println("\tm -> view the author of a mod");
        System.out.println("\tq -> quit");
        System.out.println("\tp -> save game to file");
        System.out.println("\tl -> load game from file");
    }

    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void doAddMod() {
        System.out.println("Please enter the name of the mod:");
        String name = input.nextLine();

        if (name.equals("")) {
            System.out.println("Please enter a valid mod name");
        } else {
            Mod mod = new Mod(name);
            game.addMod(mod);
            System.out.println("Mod " + name + " has been added to " + game.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: conducts a deposit transaction
    private void doViewMod() {
        System.out.print("These are all the mods of this game:");

        for (Mod m: game.getMods()) {
            System.out.println(m.getName());
        }
    }


    //MODIFIES: this
    //EFFECTS: revise the introduction of a mod
    private void doReviseIntroduction() {
        System.out.print("Please enter the name of the mod:");
        String name = input.nextLine();

        boolean found = false;

        for (Mod m: game.getMods()) {
            if (name.equals(m.getName())) {

                found = true;
                System.out.println("Please write the introduction of this mod:");
                String intro = input.nextLine();
                m.reviseIntroduction(intro);
                System.out.println("The introduction of " + m.getName() + " has been updated");
                break;
            }
        }
        if (!found) {
            System.out.println("Mod " + name + " not found!");
        }
    }

    //MODIEFIES: this
    //EFFECTS: view the introduction of a mod
    private void doViewIntro() {
        System.out.print("Please enter the name of the mod that you want to see the introduction of:");
        String name = input.nextLine();

        boolean found = false;

        for (Mod m: game.getMods()) {
            if (name.equals(m.getName())) {

                found = true;
                System.out.println("Here is the introduction: " + m.getIntroduction());
                break;
            }
        }
        if (!found) {
            System.out.println("Mod " + name + " not found!");
        }
    }



    //MODIFIES: this
    //EFFECTS: update the author of a mod
    private void doUpdateAuthor() {
        System.out.print("Please enter the name of the mod:");
        String name = input.nextLine();

        boolean found = false;

        for (Mod m: game.getMods()) {
            if (name.equals(m.getName())) {

                found = true;
                System.out.println("Please enter the author's name of this mod:");
                String author = input.nextLine();
                m.changeAuthor(author);
                System.out.println("The author of " + m.getName() + "has been updated");
                break;
            }
        }
        if (!found) {
            System.out.println("Mod " + name + " not found!");
        }
    }

    //MODIEFIES: this
    //EFFECTS: view the author of a mod
    private void doViewAuthor() {
        System.out.print("Please enter the name of the mod:");
        String name = input.nextLine();

        boolean found = false;

        for (Mod m: game.getMods()) {
            if (name.equals(m.getName())) {

                found = true;
                System.out.println("The author of " + m.getName() + " is " + m.getAuthor());
                break;
            }
        }
        if (!found) {
            System.out.println("Mod " + name + " not found!");
        }
    }

    private void doSaveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            System.out.println("Saved " + game.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }
    
    private void doLoadGame() {
        try {
            game = jsonReader.read();
            System.out.println("Loaded " + game.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
