package model;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class testMod {
    private Mod testMod;

    @BeforeEach
    void runBefore() {
        testMod = new Mod("The Convergence");

    }

    @Test
    public void testConstructor() {
        assertEquals("The Convergence", testMod.getName());
        assertEquals("Unknown", testMod.getAuthor());
        assertEquals("The uploader haven't add any introduction", testMod.getIntroduction());
    }


    @Test
    public void testChangeAuthor() {
        assertEquals("Unknown", testMod.getAuthor());
        testMod.changeAuthor("Maybe me");
        assertEquals("Maybe me", testMod.getAuthor());
    }

    @Test
    public void testReviseInformation() {
        assertEquals("The uploader haven't add any introduction", testMod.getIntroduction());
        testMod.reviseIntroduction("This is the best mod for Elden Ring");
        assertEquals("This is the best mod for Elden Ring", testMod.getIntroduction());
    }

}
