package SYSC4806.survey.tests;

import org.junit.Test;
import SYSC4806.survey.Main;

import static org.junit.Assert.*;

public class MainTest {
    @Test
     public void isTrue() {
        Main m = new Main();
        assertTrue(m.checkIfTrue(true));
        assertFalse(m.checkIfTrue(false));
    }
}