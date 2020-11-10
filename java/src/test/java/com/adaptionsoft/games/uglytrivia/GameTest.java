package com.adaptionsoft.games.uglytrivia;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Random;

import static org.junit.Assert.*;

public class GameTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    Game gameTest = new Game();
    int numPlayers = 3;


    String cadena = "Player 1 was added\n" +
            "They are player number 1\n" +
            "Player 2 was added\n" +
            "They are player number 2\n" +
            "Player 3 was added\n" +
            "They are player number 3\n" +
            "Player 1 is the current player\n" +
            "They have rolled a 3\n" +
            "Player 1's new location is 3\n" +
            "The category is Pop\n" +
            "Pop Question 0";

    String[] arrayResponse = cadena.split("\n");
    @Before
    public void setUp() throws Exception {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));

        gameTest.add("Player 1");
        gameTest.add("Player 2");
        gameTest.add("Player 3");




    }

    @After
    public void tearDown() throws Exception {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }




    @Test
    public void add() {
        boolean playerAdded = gameTest.add("Chet");
        assertTrue(playerAdded);
    }

    @Test
    public void howManyPlayers() {
        int players = gameTest.howManyPlayers();
        assertEquals(numPlayers,players);
    }



    @Test
    public void roll() {
        gameTest.roll(3);
        String[] actualResponse = outContent.toString().trim().split("\n");
        assertEquals(arrayResponse[7],actualResponse[7].trim());
        assertEquals(arrayResponse[8],actualResponse[8].trim());
        assertEquals(arrayResponse[9],actualResponse[9].trim());
        assertEquals(arrayResponse[10],actualResponse[10].trim());
    }

    @Test
    public void wasCorrectlyAnswered() {
        assertTrue(gameTest.wasCorrectlyAnswered());
    }

    @Test
    public void wrongAnswer() {
        assertTrue(gameTest.wrongAnswer());
    }






}