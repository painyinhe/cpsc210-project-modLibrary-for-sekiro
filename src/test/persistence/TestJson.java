package persistence;

import model.Game;
import model.Mod;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestJson {

    protected void checkMod(String name, String author, String introduction, Mod mod) {
        assertEquals(name, mod.getName());
        assertEquals(author, mod.getAuthor());
        assertEquals(introduction, mod.getIntroduction());
    }

}
