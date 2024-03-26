package com.textr.file;

import com.textr.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class FileReaderTest {

    @BeforeEach
    public void initialise(){
        Settings.defaultLineSeparator = "\r\n";
    }
    @Test
    public void readValidFile(){
        String expected = "tested\r\ntest\r\nte";
        String reading = FileReader.readContents(new File("resources/test.txt"));
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected, reading)
        );
    }

    @Test
    public void readInvalidFilePath(){
        assertThrows(IllegalArgumentException.class, () -> FileReader.readContents(new File("resources/invalid.txt")));
        assertThrows(IllegalArgumentException.class, () -> FileReader.readContents(null));
    }

    @Test
    public void readFileInvalidLineSeparatorSetting(){
        Settings.defaultLineSeparator = "\s";
        assertThrows(IllegalStateException.class, () -> FileReader.readContents(new File("resources/test.txt")));
    }

    // Cannot test because standard newline on windows is CRLF
//    @Test
//    public void readFileAnotherLS(){
//        Settings.defaultLineSeparator = "\r";
//        String expected = "tested\rtest\rte";
//        String reading = FileReader.readContents("resources/test.txt");
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(expected, reading)
//        );
//    }
}
