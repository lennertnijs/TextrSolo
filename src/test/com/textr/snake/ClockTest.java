package com.textr.snake;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;


public class ClockTest {

    private GameClock clock;
    private GameClock clock2;
    private GameClock clock3;

    @BeforeEach
    public void initialise(){
        clock = new GameClock(2.5f);
        clock2 = new GameClock(1.5f);
        clock3 = new GameClock(2.5f);
    }

    @Test
    public void testConstructorIllegal(){
        assertThrows(IllegalArgumentException.class, () -> new GameClock(0));
        assertThrows(IllegalArgumentException.class, () -> new GameClock(-1));
    }

    @Test
    public void testIsActiveAndStartAndStop(){
        assertFalse(clock.isActive());
        clock.start();
        assertTrue(clock.isActive());
        clock.stop();
        assertFalse(clock.isActive());
    }


    @Test
    public void testIncreaseTimeAndShouldUpdate(){
        for(int i = 0; i < 250; i++)
            clock.increaseTime(10);
        assertFalse(clock.shouldUpdate());
        clock.increaseTime(1);
        assertTrue(clock.shouldUpdate());
    }

    @Test
    public void testIncreaseTimeWithZero(){
        clock.increaseTime(0);
    }

    @Test
    public void testIncreaseTimeNegative(){
        assertThrows(IllegalArgumentException.class, () -> clock.increaseTime(-1));
    }

    @Test
    public void testSubtractThreshold(){
        clock.increaseTime(2550);
        assertTrue(clock.shouldUpdate());
        clock.subtractThreshHold();
        assertFalse(clock.shouldUpdate());
    }

    @Test
    public void testDecreaseThreshold(){
        clock.increaseTime(2450);
        assertFalse(clock.shouldUpdate());
        clock.decreaseThreshold(0.9f);
        assertTrue(clock.shouldUpdate());
    }

    @Test
    public void testDecreaseThresholdWithInvalidFactor(){
        assertThrows(IllegalArgumentException.class, () -> clock.decreaseThreshold(-0.1f));
        assertThrows(IllegalArgumentException.class, () -> clock.decreaseThreshold(0));
        assertThrows(IllegalArgumentException.class, () -> clock.decreaseThreshold(1));
        assertThrows(IllegalArgumentException.class, () -> clock.decreaseThreshold(1.5f));
    }

    @Test
    public void testEquals(){
        assertEquals(clock, clock3);
        assertNotEquals(clock, clock2);
        assertNotEquals(clock, new Object());
    }

    @Test
    public void testHashCode(){
        assertEquals(clock.hashCode(), clock3.hashCode());
        assertNotEquals(clock.hashCode(), clock2.hashCode());
    }

    @Test
    public void testCopy(){
        assertEquals(clock, clock.copy());
    }

    @Test
    public void testToString(){
        String expectedString = "GameClock[elapsedTimeInSeconds=0.000000, threshHoldInSeconds=2.500000, running=false]";
        assertEquals(clock.toString(), expectedString);
    }


}
