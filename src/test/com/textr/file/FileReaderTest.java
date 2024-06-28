package com.textr.file;

import com.textr.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class FileReaderTest {

    @BeforeEach
    public void initialise(){
        Settings.defaultLineSeparator = System.lineSeparator();
    }

    @Test
    public void readValidFile(){
        String expected = "tested" + System.lineSeparator() + "test"  + System.lineSeparator() + "te";
        String reading = FileReader.readContents(new File("resources/test.txt"));
        assertEquals(expected, reading);
    }

    @Test
    public void testReadFileWithUnsupportedCharacter() throws IOException {
        java.io.FileWriter fileWriter = new FileWriter("resources/test2.txt");
        fileWriter.write('\t');
        fileWriter.close();
        assertThrows(IllegalArgumentException.class,
                () -> FileReader.readContents(new File("resources/test2.txt")));
    }

    @Test
    public void readInvalidFilePath(){
        assertThrows(IllegalArgumentException.class,
                () -> FileReader.readContents(new File("resources/invalid.txt")));
        assertThrows(NullPointerException.class,
                () -> FileReader.readContents(null));
    }

    @Test
    public void readFileInvalidLineSeparatorSetting(){
        Settings.defaultLineSeparator = "\s";
        assertThrows(IllegalStateException.class,
                () -> FileReader.readContents(new File("resources/test.txt")));
    }

    @Test
    public void readFileWithDefaultSeparatorLF() throws IOException {
        Settings.defaultLineSeparator = "\n";
        java.io.FileWriter fileWriter = new FileWriter("resources/test2.txt");
        fileWriter.write("Text");
        fileWriter.write('\n');
        fileWriter.close();
        FileReader.readContents(new File("resources/test2.txt"));
    }

    @Test
    public void readFileWithDefaultSeparatorLFAndContainsCR() throws IOException {
        Settings.defaultLineSeparator = "\n";
        java.io.FileWriter fileWriter = new FileWriter("resources/test2.txt");
        fileWriter.write("Text");
        fileWriter.write('\r');
        fileWriter.close();
        assertThrows(IllegalArgumentException.class,
                () -> FileReader.readContents(new File("resources/test2.txt")));
    }

    @Test
    public void readFileWithDefaultSeparatorCRLFAndContainsLF() throws IOException {
        Settings.defaultLineSeparator = "\r\n";
        java.io.FileWriter fileWriter = new FileWriter("resources/test2.txt");
        fileWriter.write("Text");
        fileWriter.write('\n');
        fileWriter.close();
        assertThrows(IllegalArgumentException.class,
                () -> FileReader.readContents(new File("resources/test2.txt")));
    }

    @Test
    public void readFileWithDefaultSeparatorCRLFAndContainsCR() throws IOException {
        Settings.defaultLineSeparator = "\r\n";
        java.io.FileWriter fileWriter = new FileWriter("resources/test2.txt");
        fileWriter.write("Text");
        fileWriter.write('\r');
        fileWriter.close();
        assertThrows(IllegalArgumentException.class,
                () -> FileReader.readContents(new File("resources/test2.txt")));
    }

    @Test
    public void readFileWithDefaultSeparatorCRLFAndContainsCR2() throws IOException {
        Settings.defaultLineSeparator = "\r\n";
        java.io.FileWriter fileWriter = new FileWriter("resources/test2.txt");
        fileWriter.write("Text");
        fileWriter.write('\r');
        fileWriter.write("Text2");
        fileWriter.close();
        assertThrows(IllegalArgumentException.class,
                () -> FileReader.readContents(new File("resources/test2.txt")));
    }
}
