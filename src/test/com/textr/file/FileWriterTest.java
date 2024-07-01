package com.textr.file;

import com.textr.Settings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public final class FileWriterTest {

    private final FileWriter fileWriter = new FileWriter();
    private final File file = new File("test-resources/test-file.txt");
    private final String s = "this is a writer text";

    @BeforeEach
    public void initialise(){
        Settings.defaultLineSeparator = "\n";
    }

    @Test
    public void testWrite() throws IOException {
        fileWriter.write(s, file);
    }

    @Test
    public void testWriteWithNullString(){
        assertThrows(NullPointerException.class,
                () -> fileWriter.write(null, file));
    }

    @Test
    public void testWriteWithNullFile(){
        assertThrows(NullPointerException.class,
                () -> fileWriter.write(s, null));
    }
}
