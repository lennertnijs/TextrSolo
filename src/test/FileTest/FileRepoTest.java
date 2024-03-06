package FileTest;

import com.Textr.File.File;
import com.Textr.File.FileIdGenerator;
import com.Textr.File.FileRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

public class FileRepoTest {

    @BeforeEach
    public void initialise(){
        FileIdGenerator.resetGenerator();
    }

    @Test
    public void testFileRepo(){
        FileRepo repo = new FileRepo();
        File file1 = File.create("url", "text");
        File file2 = File.create("url", "text2");
        Assertions.assertAll(
                () -> Assertions.assertEquals(repo.getSize(), 0),

                () -> Assertions.assertFalse(repo.contains(1)),
                () -> repo.add(file1),
                () -> Assertions.assertTrue(repo.contains(0)),
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

                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> repo.add(null)),
                () -> Assertions.assertThrows(NoSuchElementException.class, () -> repo.get(0))
        );
    }
}
