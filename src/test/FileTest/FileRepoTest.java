package FileTest;

import com.Textr.File.File;
import com.Textr.File.FileIdGenerator;
import com.Textr.File.FileRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class FileRepoTest {

    private File file1;
    private File file2;
    private File file3;
    private FileRepo repo;
    @BeforeEach
    public void initialise(){
        FileIdGenerator.resetGenerator();
        repo = new FileRepo();
        file1 = File.create("url");
        file2 = File.create("url");
        file3 = File.create("url");
    }

    @Test
    public void testAddAndRemove(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(repo.getSize(), 0),

                () -> repo.add(file1),
                () -> Assertions.assertEquals(repo.getSize(), 1),
                () -> Assertions.assertEquals(repo.getAll().size(), 1),
                () -> Assertions.assertEquals(repo.get(0), file1),

                () -> repo.add(file2),
                () -> Assertions.assertEquals(repo.getSize(), 2),
                () -> Assertions.assertEquals(repo.getAll().size(), 2),
                () -> Assertions.assertEquals(repo.get(0), file1),
                () -> Assertions.assertEquals(repo.get(1), file2),

                () -> repo.remove(0),
                () -> Assertions.assertEquals(repo.getSize(), 1),
                () -> Assertions.assertEquals(repo.getAll().size(), 1),
                () -> Assertions.assertEquals(repo.get(1), file2),

                () -> repo.remove(1),
                () -> Assertions.assertEquals(repo.getSize(), 0),

                () -> repo.addAll(new ArrayList<>(List.of(file1, file2, file3))),
                () -> repo.removeAll(),
                () -> Assertions.assertEquals(repo.getSize(), 0)


        );
    }

    @Test
    public void testContains(){
        Assertions.assertAll(
                () -> Assertions.assertFalse(repo.contains(0)),

                () -> repo.add(file1),
                () -> Assertions.assertTrue(repo.contains(0)),
                () -> Assertions.assertFalse(repo.contains(1)),

                () -> repo.add(file2),
                () -> Assertions.assertTrue(repo.contains(1)),

                () -> repo.remove(0),
                () -> Assertions.assertFalse(repo.contains(0)),
                () -> Assertions.assertTrue(repo.contains(1))
        );
    }

    @Test
    public void testIllegal(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> repo.add(null)),
                () -> Assertions.assertThrows(NoSuchElementException.class, () -> repo.get(0)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> repo.addAll(null))
        );
    }
}
