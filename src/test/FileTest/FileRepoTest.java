package FileTest;

import com.Textr.File.File;
import com.Textr.File.FileIdGenerator;
import com.Textr.File.FileRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileRepoTest {

    @BeforeEach
    public void initialise(){
        FileIdGenerator.resetGenerator();
    }

    @Test
    public void testFileRepo(){
        FileRepo repo = new FileRepo();
        File file1 = File.builder().path("url").text("text").build();
        File file2 = File.builder().path("url").text("text2").build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(repo.getSize(), 0),

                () -> repo.add(file1),
                () -> Assertions.assertEquals(repo.getSize(), 1),
                () -> Assertions.assertEquals(repo.getAll().size(), 1),
                () -> Assertions.assertEquals(repo.get(0).get(), file1),

                () -> repo.add(file2),
                () -> Assertions.assertEquals(repo.getSize(), 2),
                () -> Assertions.assertEquals(repo.getAll().size(), 2),
                () -> Assertions.assertEquals(repo.get(0).get(), file1),
                () -> Assertions.assertEquals(repo.get(1).get(), file2),

                () -> repo.remove(0),
                () -> Assertions.assertEquals(repo.getSize(), 1),
                () -> Assertions.assertEquals(repo.getAll().size(), 1),
                () -> Assertions.assertEquals(repo.get(1).get(), file2),

                () -> repo.remove(1),
                () -> Assertions.assertEquals(repo.getSize(), 0),

                () -> repo.add(file1),
                () -> repo.add(file2),
                () -> Assertions.assertEquals(repo.getSize(), 2),
                repo::removeAll,
                () -> Assertions.assertEquals(repo.getSize(), 0),

                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> repo.add(null))
        );
    }
}
