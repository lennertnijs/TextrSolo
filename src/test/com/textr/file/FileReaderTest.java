package com.textr.file;

import com.textr.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public final class FileReaderTest {

    private final FileReader fileReader = new FileReader();
    private final File file = new File("test-resources/test-file.txt");
    private final String s = "this is a writer text";

    @BeforeEach
    public void initialise(){
        Settings.defaultLineSeparator = "\n";
    }

    @Test
    public void testRead(){
        String content = fileReader.read(file);
        assertEquals(content, "this is a writer text");
    }

    @Test
    public void testReadWithInvalidCharacter(){
        File f = new File("test-resources/invalid-character.txt");
        assertThrows(IllegalArgumentException.class,
                () -> fileReader.read(f));
    }
}
