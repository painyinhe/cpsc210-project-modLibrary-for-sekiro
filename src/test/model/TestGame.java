package model;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestGame {
    private Game testGame;
    private Mod testMod1;
    private Mod testMod2;
    
    @BeforeEach
    void runBefore() {
        testGame = new Game("Elden Ring");
        testMod1 = new Mod("Garden Of Eye");
        testMod2 = new Mod("The Convergence");


    }

    @Test
    void testConstructor() {
        assertEquals(0, testGame.getMods().size());
        assertEquals("Elden Ring", testGame.getName());
    }

    @Test
    void testAddMod() {
        testGame.addMod(testMod1);
        assertEquals(testMod1, testGame.getMods().get(0));
        testGame.addMod(testMod1);
        assertEquals(1, testGame.getMods().size());
        testGame.addMod(testMod2);
        assertEquals(2, testGame.getMods().size());
        assertEquals(testMod2, testGame.getMods().get(1));
    }
}
