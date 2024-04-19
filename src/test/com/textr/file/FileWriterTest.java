package com.textr.file;

import com.textr.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class FileWriterTest {

    @BeforeEach
    public void initialise(){
        Settings.defaultLineSeparator = "\r\n";
    }

    @Test
    public void testWrite(){
        String text = "first line\nsecond line\nthird line";
        String text2 = "first line\r\nsecond line\r\nthird line";
        Assertions.assertDoesNotThrow(() -> FileWriter.write(text, new File("resources/write1.txt")));
        Assertions.assertEquals(text2, FileReader.readContents(new File("resources/write1.txt")));
    }

    @Test
    public void testWriteNullParam(){
        assertThrows(NullPointerException.class, () -> FileWriter.write(null, new File("resources/write1.txt")));
        assertThrows(NullPointerException.class, () -> FileWriter.write("text", null));
    }

    @Test
    public void testWriteInvalidPath(){
        Path p = Paths.get("resources");
        Assertions.assertDoesNotThrow(() -> FileWriter.write("text", p.toFile()));
    }
}
