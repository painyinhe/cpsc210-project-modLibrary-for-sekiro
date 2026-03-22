package ui;

import model.Game;
import model.Mod;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ModsLibraryGUI extends JFrame{

    private static final String JSON_STORE = "./data/game.json";

    private Game game;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private DefaultListModel<String> modListModel;
    private JList<String> modJList;

    private JLabel gameNameLabel;
    private JLabel nameLabel;
    private JLabel authorLabel;
    private JTextArea introductionArea;
    private JLabel imageLabel;


    public ModsLibraryGUI() {

    }

    // MODIFIES: this
    // EFFECTS: sets up the main frame
    private void initializeFrame() {

    }


    // MODIFIES: this
    // EFFECTS: initializes all GUI components
    private void initializeComponents() {

    }


    // EFFECTS: creates the top panel
    //with title and visual component
    private JPanel createTopPanel() {

    }


    // EFFECTS: creates the center panel with mod list and details
    private JPanel createCenterPanel() {

    }


    // EFFECTS: creates the mod list panel
    private JPanel createModListPanel() {

    }


    // EFFECTS: creates the mod details panel
    private JPanel createDetailsPanel() {

    }


    // EFFECTS: creates the button panel
    private JPanel createButtonPanel() {


    }



    // MODIFIES: this
    // EFFECTS: adds a mod to the game and updates the list display
    private void addMod() {

    }


     // MODIFIES: this
    // EFFECTS: updates the author of the selected mod
    private void updateAuthor() {

    }


    // MODIFIES: this
    // EFFECTS: updates the introduction of the selected mod
    private void updateIntroduction() {

    }


    // MODIFIES: this
    // EFFECTS: displays details of the selected mod
    private void displaySelectedMod() {


    }


    // EFFECTS: returns the currently selected mod, or null if none is selected
    private Mod getSelectedMod() {


    }


    // MODIFIES: this
    // EFFECTS: refreshes the mod list display
    private void refreshModList() {


    }




    // EFFECTS: saves the game to file
    private void saveGame() {


    }


    // MODIFIES: this
    // EFFECTS: loads the game from file
    private void loadGame() {


    }


    // EFFECTS: shows a message dialog
    private void showMessage(String message) {


    }
}
