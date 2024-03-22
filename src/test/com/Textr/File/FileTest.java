package com.Textr.File;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

// TODO: Delete this test (now using java Files)
public class FileTest {

    private File file1;
    private File file2;

    @BeforeEach
    public void initialise(){
        file1 = new File("path1");
        file2 = new File("path2");
    }


    @Test
    public void testCreationAndGetters(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(file1.getPath(), "path1")
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Assertions.assertAll(
                () -> Assertions.assertNotEquals(file1, file2),
                () -> Assertions.assertNotEquals(file1, new Object()),
                () -> Assertions.assertNotEquals(file1.hashCode(), file2.hashCode())
        );
    }
}
