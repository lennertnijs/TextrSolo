package com.Textr.File;

import com.Textr.Settings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    static FileService fileService;
    static FileRepo repo;
    static String directoryPath = "test-resources/file-service";
    static java.io.File directory = new java.io.File(directoryPath);

    @BeforeAll
    static void initialiseDirectory(){
        // Check if valid test directory
        if (!directory.exists()) {
            assertTrue(directory.mkdirs(), "Failed to make test directory.");
        }
        // Set line separator
        Settings.defaultLineSeparator = "\n";
    }

    @BeforeEach
    void initialise(){
        repo = new FileRepo();
        fileService = new FileService(repo);
    }

    @Test
    void getFile() {
        // NOTE: In hindsight, this is mostly testing FileRepo functionality.
        /*
         * When a fileID present in the repo is given, return a copy of the file with said ID
         */
        var validFile = File.create("test-resources/file.txt");
        var validFileID = validFile.getId();
        repo.add(validFile); // Add the file to the repo without reading from disk

        var resultFile = fileService.getFile(validFileID);
        assertEquals(validFile, resultFile,
                "getFile returned the wrong file object (incorrect ID).");
        assertNotSame(validFile, resultFile,
                "File objects should be different instances.");

        /*
         * When a fileID not present in the repo is given, throw NoSuchElementException
         */
        var invalidFile = File.create("test-resources/file2.txt");
        var invalidFileID = invalidFile.getId();
        // (invalidFile not added to repo)

        assertThrows(NoSuchElementException.class, () -> fileService.getFile(invalidFileID),
                "getFile did not throw NoSuchElementException when given invalid file ID.");
    }

    @Test
    void getAllFiles() {
        var file1 = File.create("test-resources/file-service/get-all-files-1.txt");
        var file2 = File.create("test-resources/file-service/get-all-files-2.txt");
        var file3 = File.create("test-resources/file-service/get-all-files-3.txt");

        repo.add(file1);
        repo.add(file2);
        repo.add(file3);

        assertIterableEquals(Arrays.asList(file1, file2, file3), fileService.getAllFiles(),
                "getAllFiles did not return an iterable of the three files added to the repo.");
    }

    @Test
    void saveToFile() {
        String fileName = "save-to-file-1.txt";
        java.io.File fileToSaveIO = new java.io.File(directory, fileName);
        var fileToSave = File.create(fileToSaveIO.getPath());

        // Delete file if present
        if (fileToSaveIO.exists()) {
            assertTrue(fileToSaveIO.delete(), "Failed to remove already present test file.");
        }

        // Continue with testing...
        // Add file to repo and save some text to file
        repo.add(fileToSave);
        String textToSave = "Saved text\nLine 2";
        fileService.saveToFile(textToSave, fileToSave.getId());

        // Make sure file was saved to correctly
        assertTrue(fileToSaveIO.exists());
        String textInFile = "";
        try {
            textInFile = new String(Files.readAllBytes(Path.of(fileToSaveIO.toURI())));
        } catch (IOException e) {
            // The best way I could think of to still "throw" or "return" the error in the tests:
            assertDoesNotThrow(() -> {throw e;});
        }
        assertEquals(textToSave, textInFile);
    }

    @Test
    void initialiseFile() {
        String fileName = "initialise-file-1.txt";
        String textInFile = "Example text";
        java.io.File fileIO = new java.io.File(directory, fileName);

        // Add in initialise-file-1.txt with content "Line 1\nLine 2"
        try {
            var writer = new BufferedWriter(new FileWriter(fileIO));
            writer.write(textInFile);
            writer.close();
        } catch (IOException e) {
            assertDoesNotThrow(() -> {throw e;}, "Failed setting up test file " + fileName);
        }

        // Run initialiseFile and test if file instance was correctly added to repo
        fileService.initialiseFile(fileIO.getPath());

        assertEquals(1, repo.getAll().size(),
                "repo does not contain required amount of files.");
        assertEquals(fileIO, new java.io.File(repo.getAll().get(0).getUrl()),
                "File path was set incorrectly on file initialisation");
    }
}
