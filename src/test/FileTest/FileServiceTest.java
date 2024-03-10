package FileTest;

import com.Textr.File.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class FileServiceTest {


    private File file1;
    private File file2;
    private IFileRepo repo;
    private FileService fileService;


    @BeforeEach
    public void initialise(){
        FileIdGenerator.resetGenerator();
        file1 = File.create("resources/test.txt", "text1");
        file2 = File.create("resources/test2.txt", "text2");
        repo = new FileRepo();
        fileService = new FileService(repo);
    }

    @Test
    public void testGet(){
        repo.add(file1);
        File file1Clone = fileService.getFile(0);
        Assertions.assertAll(
                () -> Assertions.assertEquals(file1Clone, file1),
                () -> file1Clone.setText("new text"),
                () -> Assertions.assertNotEquals(file1Clone, file1)
        );
    }

    @Test
    public void testGetAll(){
        repo.add(file1);
        repo.add(file2);
        List<File> files = fileService.getAllFiles();
        Assertions.assertAll(
                () -> Assertions.assertEquals(files, new ArrayList<>(List.of(file1, file2))),
                () -> files.get(0).setText("new text for test"),
                () -> Assertions.assertNotEquals(files, new ArrayList<>(List.of(file1, file2)))
        );
    }

    @Test
    public void testInitialiseFile(){
        fileService.initialiseFile("resources/test.txt");
        Assertions.assertAll(
                () -> Assertions.assertEquals(fileService.getAllFiles().size(), 1),
                () -> Assertions.assertThrows(IllegalArgumentException.class,
                        () -> fileService.initialiseFile("random_url")),
                () -> Assertions.assertEquals(fileService.getAllFiles().size(), 1)
        );
    }
 // make file3 to save to
//    @Test
//    public void testSaveToFile(){
//        repo.add(file1);
//        repo.add(file2);
//        String text = "save";
//        fileService.saveToFile(text, 0);
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(fileService.getFile(0).getText(), text),
//                () -> Assertions.assertEquals(FileReader.readContents("resources/save.txt"), text)
//        );
//    }
}
