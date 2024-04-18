package com.textr.snake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ClockTest {

    private GameClock clock;

    @BeforeEach
    public void initialise(){
        clock = new GameClock(2.5f);
    }

    @Test
    public void testConstructorIllegal(){
        assertThrows(IllegalArgumentException.class, () -> new GameClock(0));
    }

    @Test
    public void testShouldMove(){
        for(int i = 0; i < 250; i++)
            clock.increase(10);
        assertFalse(clock.shouldMove());
        clock.increase(1);
        assertTrue(clock.shouldMove());
    }

    @Test
    public void testIncreaseNegative(){
        assertThrows(IllegalArgumentException.class, () -> clock.increase(-1));
    }

    @Test
    public void testReset(){
        clock.increase(3000);
        clock.reset();
        clock.reset();
        clock.reset();
        assertFalse(clock.shouldMove());
    }

    @Test
    public void testChangeSecondsBetweenMoves(){
        clock.increase(2000);
        clock.changeSecondsBetweenMove(0.2f);
        assertTrue(clock.shouldMove());
    }
}
