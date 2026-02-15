package ui;

import java.util.Scanner;

import model.Game;
import model.Mod;

//ModsLibrary application
public class ModsLibrary {

    private Scanner input;
    private Game game;

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
            doViewMod();;
        } else {
            System.out.println("Selection not valid...");
        }
    }


    //MODIFIES: this
    //EFFECTS: initiates users command
    private void init() {
        input = new Scanner(System.in);
        game = new Game("Sekiro: shadow die twice");
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add one mod to a specific game");
        System.out.println("\tv -> view all mods of a game");
        System.out.println("\tq -> quit");
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
}
