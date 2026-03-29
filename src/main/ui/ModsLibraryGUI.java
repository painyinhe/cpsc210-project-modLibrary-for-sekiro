package ui;

import model.Event;
import model.EventLog;
import model.Game;
import model.Mod;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ModsLibraryGUI extends JFrame {

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
        game = new Game("My Mod Library");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        initializeFrame();
        initializeComponents();
        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: sets up the main frame
    private void initializeFrame() {
        setTitle("Game Mod Library");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        addWindowClosingBehaviour();
    }

    private void addWindowClosingBehaviour() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                handleWindowClosing();
            }
        });
    }

    private void handleWindowClosing() {
        printEventLog();
        dispose();
        System.exit(0);
    }

    private void printEventLog() {
        for (Event event : EventLog.getInstance()) {
            System.out.println(event);
            System.out.println();
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes all GUI components
    private void initializeComponents() {
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createButtonPanel(), BorderLayout.SOUTH);
    }


    // EFFECTS: creates the top panel
    //with title and visual component
    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        gameNameLabel = new JLabel("Current Game Library: " + game.getName(), SwingConstants.CENTER);
        gameNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        topPanel.add(gameNameLabel, BorderLayout.NORTH);

        ImageIcon icon = new ImageIcon("./data/modbanner.jpg");
        imageLabel = new JLabel(icon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topPanel.add(imageLabel, BorderLayout.CENTER);

        return topPanel;
    }


    // EFFECTS: creates the center panel with mod list and details
    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        centerPanel.add(createModListPanel());
        centerPanel.add(createDetailsPanel());

        return centerPanel;
    }


    // EFFECTS: creates the mod list panel
    private JPanel createModListPanel() {
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("Mods in Library"));

        modListModel = new DefaultListModel<>();
        modJList = new JList<>(modListModel);
        modJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        modJList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                displaySelectedMod();
            }
        });

        JScrollPane scrollPane = new JScrollPane(modJList);
        listPanel.add(scrollPane, BorderLayout.CENTER);

        return listPanel;
    }


    // EFFECTS: creates the mod details panel
    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel(new BorderLayout(10, 10));
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Mod Details"));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        nameLabel = new JLabel("Name: ");
        authorLabel = new JLabel("Author: ");

        nameLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        authorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(10));
        infoPanel.add(authorLabel);
        infoPanel.add(Box.createVerticalStrut(10));

        introductionArea = new JTextArea(10, 20);
        introductionArea.setEditable(false);
        introductionArea.setLineWrap(true);
        introductionArea.setWrapStyleWord(true);
        JScrollPane introScrollPane = new JScrollPane(introductionArea);
        introScrollPane.setBorder(BorderFactory.createTitledBorder("Introduction"));

        detailsPanel.add(infoPanel, BorderLayout.NORTH);
        detailsPanel.add(introScrollPane, BorderLayout.CENTER);

        return detailsPanel;
    }


    // EFFECTS: creates the button panel
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();

        JButton addModButton = new JButton("Add Mod");
        JButton updateAuthorButton = new JButton("Update Author");
        JButton updateIntroButton = new JButton("Update Introduction");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        addModButton.addActionListener(e -> addMod());
        updateAuthorButton.addActionListener(e -> updateAuthor());
        updateIntroButton.addActionListener(e -> updateIntroduction());
        saveButton.addActionListener(e -> saveGame());
        loadButton.addActionListener(e -> loadGame());

        buttonPanel.add(addModButton);
        buttonPanel.add(updateAuthorButton);
        buttonPanel.add(updateIntroButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        return buttonPanel;
    }



    // MODIFIES: this
    // EFFECTS: adds a mod to the game and updates the list display
    private void addMod() {
        String modName = JOptionPane.showInputDialog(this, "Enter mod name:");
        if (modName == null || modName.trim().isEmpty()) {
            return;
        }

        Mod mod = new Mod(modName.trim());

        String author = JOptionPane.showInputDialog(this, "Enter author (optional):");
        if (author != null && !author.trim().isEmpty()) {
            mod.changeAuthor(author.trim());
        }

        String introduction = JOptionPane.showInputDialog(this, "Enter introduction (optional):");
        if (introduction != null && !introduction.trim().isEmpty()) {
            mod.reviseIntroduction(introduction.trim());
        }

        game.addMod(mod);
        refreshModList();
    }


     // MODIFIES: this
    // EFFECTS: updates the author of the selected mod
    private void updateAuthor() {
        Mod selectedMod = getSelectedMod();
        if (selectedMod == null) {
            showMessage("Please select a mod first.");
            return;
        }

        String newAuthor = JOptionPane.showInputDialog(this, "Enter new author:", selectedMod.getAuthor());
        if (newAuthor != null && !newAuthor.trim().isEmpty()) {
            selectedMod.changeAuthor(newAuthor.trim());
            displaySelectedMod();
        }
    }


    // MODIFIES: this
    // EFFECTS: updates the introduction of the selected mod
    private void updateIntroduction() {
        Mod selectedMod = getSelectedMod();
        if (selectedMod == null) {
            showMessage("Please select a mod first.");
            return;
        }

        String newIntroduction = JOptionPane.showInputDialog(this,
                "Enter new introduction:",
                selectedMod.getIntroduction());

        if (newIntroduction != null && !newIntroduction.trim().isEmpty()) {
            selectedMod.reviseIntroduction(newIntroduction.trim());
            displaySelectedMod();
        }
    }


    // MODIFIES: this
    // EFFECTS: displays details of the selected mod
    private void displaySelectedMod() {
        Mod selectedMod = getSelectedMod();

        if (selectedMod == null) {
            nameLabel.setText("Name: ");
            authorLabel.setText("Author: ");
            introductionArea.setText("");
            return;
        }

        nameLabel.setText("Name: " + selectedMod.getName());
        authorLabel.setText("Author: " + selectedMod.getAuthor());
        introductionArea.setText(selectedMod.getIntroduction());
    }


    // EFFECTS: returns the currently selected mod, or null if none is selected
    private Mod getSelectedMod() {
        int selectedIndex = modJList.getSelectedIndex();
        if (selectedIndex < 0 || selectedIndex >= game.getMods().size()) {
            return null;
        }
        return game.getMods().get(selectedIndex);
    }


    // MODIFIES: this
    // EFFECTS: refreshes the mod list display
    private void refreshModList() {
        modListModel.clear();
        for (Mod mod : game.getMods()) {
            modListModel.addElement(mod.getName());
        }
    }




    // EFFECTS: saves the game to file
    private void saveGame() {
        try {
            jsonWriter.open();
            jsonWriter.write(game);
            jsonWriter.close();
            showMessage("Saved " + game.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            showMessage("Unable to write to file: " + JSON_STORE);
        }
    }


    // MODIFIES: this
    // EFFECTS: loads the game from file
    private void loadGame() {
        try {
            game = jsonReader.read();
            gameNameLabel.setText("Current Game Library: " + game.getName());
            refreshModList();
            displaySelectedMod();
            showMessage("Loaded " + game.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            showMessage("Unable to read from file: " + JSON_STORE);
        }
    }


    // EFFECTS: shows a message dialog
    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
