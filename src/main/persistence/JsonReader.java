package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Event;
import model.EventLog;
import model.Game;
import model.Mod;

public class JsonReader {

    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads game from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Game read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        Game game = parseGame(jsonObject);
        EventLog.getInstance().logEvent(
                new Event("Game \"" + game.getName() + "\" loaded from file \"" + source + "\"."));
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses game from JSON object and returns it
    private Game parseGame(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        Game g = new Game(name);
        addMods(g, jsonObject);
        return g;
    }

    // MODIFIES: g
    // EFFECTS: parses mods from JSON object and adds them to game
    private void addMods(Game g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("mods");
        for (Object json : jsonArray) {
            JSONObject jsonMod = (JSONObject) json;
            Mod m = new Mod(jsonMod);
            g.addModFromLoad(m);
        }
    }
}
