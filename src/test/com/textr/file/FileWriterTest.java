package com.textr.file;

import com.textr.Settings;
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
        Assertions.assertAll(
                () -> FileWriter.write(text, new File("resources/write1.txt")),
                () -> Assertions.assertEquals(text, FileReader.readContents(new File("resources/write1.txt")))
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
