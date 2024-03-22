package com.Textr.File;

import com.Textr.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FileWriterTest {

    @BeforeEach
    public void initialise(){
        Settings.defaultLineSeparator = "\r\n";
    }

    @Test
    public void testWrite(){
        String text = "first line\r\nsecond line\r\nthird line";
        String text2 = "first line\rsecond line";
        Assertions.assertAll(
                () -> FileWriter.write(text, new File("resources/write1.txt")),
                () -> Assertions.assertEquals(text, FileReader.readContents(new File("resources/write1.txt"))),

                () -> FileWriter.write(text2, new File("resources/write2.txt")),
                () -> Assertions.assertNotEquals(text2, FileReader.readContents(new File("resources/write2.txt")))
        );
    }

    @Test
    public void testWriteNullParam(){
        assertThrows(IllegalArgumentException.class, () -> FileWriter.write(null, new File("path")));
        assertThrows(IllegalArgumentException.class, () -> FileWriter.write("text", null));
    }

    @Test
    public void testWriteInvalidPath(){
        FileWriter.write("text", new File("invalid"));
    }
}
