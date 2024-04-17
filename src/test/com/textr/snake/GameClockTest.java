package com.textr.snake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GameClockTest {

    private MockTimeProvider timeProvider;
    private GameClock gameClock;

    @BeforeEach
    public void initialise(){
        timeProvider = new MockTimeProvider(0);
        gameClock = new GameClock(timeProvider, 2.5f);
    }

    @Test
    public void testConstructorWithNull(){
        assertThrows(NullPointerException.class, () -> new GameClock(null, 2.5f));
    }

    @Test
    public void testConstructorWith0Float(){
        assertThrows(IllegalArgumentException.class, () -> new GameClock(timeProvider, 0));
    }

    @Test
    public void testShouldMove(){
        assertFalse(gameClock.shouldMove());
        timeProvider.increaseMockValue(2450);
        assertFalse(gameClock.shouldMove());
        timeProvider.increaseMockValue(50);
        assertFalse(gameClock.shouldMove());
        timeProvider.increaseMockValue(1);
        assertTrue(gameClock.shouldMove());
    }

    @Test
    public void testReset(){
        timeProvider.increaseMockValue(2501);
        assertTrue(gameClock.shouldMove());
        gameClock.reset();
        assertFalse(gameClock.shouldMove());
        timeProvider.increaseMockValue(2500);
        assertFalse(gameClock.shouldMove());
        timeProvider.increaseMockValue(1);
        assertTrue(gameClock.shouldMove());
    }

    @Test
    public void testChangeSecondsBetweenMoves(){
        gameClock.changeSecondsBetweenMove(0.8f);
        timeProvider.increaseMockValue(2000);
        assertFalse(gameClock.shouldMove());
        timeProvider.increaseMockValue(1);
        assertTrue(gameClock.shouldMove());
    }

    @Test
    public void testChangeSecondsBetweenMovesIllegal(){
        assertThrows(IllegalArgumentException.class, () -> gameClock.changeSecondsBetweenMove(0));
    }
}
