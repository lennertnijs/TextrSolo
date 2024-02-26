import com.Textr.FileModel.File;
import com.Textr.FileModel.FileRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileRepoTest {

    @Test
    public void testFileRepo(){
        FileRepo repo = new FileRepo();
        File file1 = File.builder().id(0).path("url").text("text").build();
        File file2 = File.builder().id(1).path("url").text("text2").build();
        File file3 = File.builder().id(1).path("url").text("text2").build();
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

                () -> repo.add(file3),
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
                () -> repo.add(file3),
                () -> Assertions.assertEquals(repo.getSize(), 2),
                () -> repo.removeAll(),
                () -> Assertions.assertEquals(repo.getSize(), 0)

        );
    }
}
