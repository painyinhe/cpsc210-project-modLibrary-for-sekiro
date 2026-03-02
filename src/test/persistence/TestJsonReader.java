package persistence;

import model.Game;
import model.Mod;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestJsonReader extends TestJson {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/\0illegal:fileName.json");
            writer.open();
            fail("FileNotFoundException expected");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyGame() {
        try {
            Game g = new Game("Sekiro: shadow die twice");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGame.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGame.json");
            g = reader.read();
            assertEquals("Sekiro: shadow die twice", g.getName());
            assertEquals(0, g.getMods().size());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {
        try {
            Game g = new Game("Sekiro: shadow die twice");

            Mod m1 = new Mod("HUD Mod");
            m1.changeAuthor("Alice");
            m1.reviseIntroduction("Better HUD for Sekiro");

            Mod m2 = new Mod("Randomizer");
            m2.changeAuthor("Bob");
            m2.reviseIntroduction("Random enemies and items");

            g.addMod(m1);
            g.addMod(m2);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            g = reader.read();

            assertEquals("Sekiro: shadow die twice", g.getName());
            List<Mod> mods = g.getMods();
            assertEquals(2, mods.size());

            checkMod("HUD Mod", "Alice", "Better HUD for Sekiro", mods.get(0));
            checkMod("Randomizer", "Bob", "Random enemies and items", mods.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

}
