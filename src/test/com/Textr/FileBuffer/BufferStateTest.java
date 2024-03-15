package com.Textr.FileBuffer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BufferStateTest {

    // The main reason for this test case is to boost coverage.

    @Test
    void valueOf() {
        assertEquals(BufferState.CLEAN, BufferState.valueOf("CLEAN"));
        assertEquals(BufferState.DIRTY, BufferState.valueOf("DIRTY"));
    }
}