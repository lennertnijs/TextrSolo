package com.Textr.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class FileServiceTest {

    FileService fileService;
    FileRepo repo;

    @BeforeEach
    void initialise(){
        repo = new FileRepo();
        fileService = new FileService(repo);
    }

    @Test
    void getFile() {
        // NOTE: after writing, I noticed that this is actually testing FileRepo. What else could I have done?
        /* When a fileID present in the repo is given, return the file with said ID */
        var validFile = File.create("test-resources/file.txt", "Some text\n");
        var validFileID = validFile.getId();
        repo.add(validFile); // Add the file to the repo without reading from disk

        assertSame(validFile, fileService.getFile(validFileID),
                "getFile returned the wrong file instance (incorrect ID).");

        /* When a fileID not present in the repo is given, throw NoSuchElementException */
        var invalidFile = File.create("test-resources/file2.txt", "Text");
        var invalidFileID = invalidFile.getId();
        // invalidFile not added to repo

        assertThrows(NoSuchElementException.class, () -> fileService.getFile(invalidFileID),
                "getFile did not throw NoSuchElementException when given invalid file ID.");
    }

    @Test
    void getAllFiles() {
        var file1 = File.create("test-resources/file-service/getAllFiles/file1.txt", "Text1");
        var file2 = File.create("test-resources/file-service/getAllFiles/file2.txt", "Text2");
        var file3 = File.create("test-resources/file-service/getAllFiles/file3.txt", "Text3");

        repo.add(file1);
        repo.add(file2);
        repo.add(file3);

        assertIterableEquals(Arrays.asList(file1, file2, file3), fileService.getAllFiles(),
                "getAllFiles did not return an iterable of the three files added to the repo.");
    }

    @Test
    void saveToFile() {
        
    }

    @Test
    void initialiseFile() {
    }
}