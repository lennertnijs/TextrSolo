package com.textr.filebuffer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextSkeletonTest {


    private TextSkeleton skeleton1;
    private TextSkeleton skeleton2;
    private TextSkeleton skeleton3;
    @BeforeEach
    public void initialise(){
        List<Integer> lineLengths1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        List<Integer> lineLengths2 = new ArrayList<>(Arrays.asList(5, 4, 3));
        List<Integer> lineLengths3 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        skeleton1 = new TextSkeleton(lineLengths1);
        skeleton2 = new TextSkeleton(lineLengths2);
        skeleton3 = new TextSkeleton(lineLengths3);
    }

    @Test
    public void testGetters(){
        assertEquals(skeleton1.getCharAmount(), 15);
        assertEquals(skeleton1.getLineAmount(), 5);
    }

    @Test
    public void testConstructorInvalid(){
        assertThrows(NullPointerException.class, () -> new TextSkeleton(null));
        assertThrows(NullPointerException.class, () -> new TextSkeleton(new ArrayList<>(Arrays.asList(1, null))));
        assertThrows(IllegalArgumentException.class, () -> new TextSkeleton(new ArrayList<>(Arrays.asList(-1, 5))));
    }

    @Test
    public void testGetLineLength(){
        assertEquals(skeleton1.getLineLength(0), 1);
        assertEquals(skeleton1.getLineLength(1), 2);
        assertEquals(skeleton1.getLineLength(2), 3);
        assertEquals(skeleton1.getLineLength(3), 4);
        assertEquals(skeleton1.getLineLength(4), 5);
    }

    @Test
    public void testGetLineLengthInvalid(){
        assertThrows(IllegalArgumentException.class, () -> skeleton1.getLineLength(-1));
        assertThrows(IllegalArgumentException.class, () -> skeleton1.getLineLength(5));
    }

    @Test
    public void testEquals(){
        assertEquals(skeleton1, skeleton3);
        assertNotEquals(skeleton1, skeleton2);
        assertNotEquals(skeleton1, new Object());
    }

    @Test
    public void testHashCode(){
        assertEquals(skeleton1.hashCode(), skeleton3.hashCode());
        assertNotEquals(skeleton1.hashCode(), skeleton2.hashCode());
    }

    @Test
    public void testToString(){
        String expectedString = "TextSkeleton[LineLengths=[1, 2, 3, 4, 5]]";
        assertEquals(skeleton1.toString(), expectedString);
    }
}
