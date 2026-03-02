package persistence;

import model.Game;
import model.Mod;
import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonWriter extends TestJson {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGame() {
        try {
            Game game = new Game("Sekiro: shadow die twice");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGame.json");
            game = reader.read();
            assertEquals("Sekiro: shadow die twice", game.getName());
            assertEquals(0, game.getMods().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {
        try {
            Game game = new Game("Sekiro: shadow die twice");

            Mod m1 = new Mod("HUD Mod");
            m1.changeAuthor("Alice");
            m1.reviseIntroduction("Better HUD for Sekiro");

            Mod m2 = new Mod("Randomizer");
            m2.changeAuthor("Bob");
            m2.reviseIntroduction("Random enemies and items");

            game.addMod(m1);
            game.addMod(m2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            writer.open();
            writer.write(game);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            game = reader.read();

            assertEquals("Sekiro: shadow die twice", game.getName());
            List<Mod> mods = game.getMods();
            assertEquals(2, mods.size());
            checkMod("HUD Mod", "Alice", "Better HUD for Sekiro", mods.get(0));
            checkMod("Randomizer", "Bob", "Random enemies and items", mods.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
