package FileTest;

import com.Textr.File.File;
import com.Textr.File.FileIdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileTest {


    @BeforeEach
    public void initialise(){
        FileIdGenerator.resetGenerator();
    }



    @Test
    public void testConstructorAndGetters(){
        File file1 = File.builder().path("/filePath1").text("testText1").build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(file1.getId(), 0),
                () -> Assertions.assertEquals(file1.getPath(), "/filePath1"),
                () -> Assertions.assertEquals(file1.getText(), "testText1")
        );
    }

    @Test
    public void testConstructorIllegal(){
        File.Builder illegalPathFileBuilder = File.builder().path(null).text("text");
        File.Builder illegalTextFileBuilder = File.builder().path("path").text(null);
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, illegalPathFileBuilder::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, illegalTextFileBuilder::build)
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        File file1 = File.builder().path("path").text("text").build();
        File file2 = File.builder().path("pad").text("text").build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(file1, file1),
                () -> Assertions.assertNotEquals(file1, file2),
                () -> Assertions.assertNotEquals(file1, new Object()),
                () -> Assertions.assertEquals(file1.hashCode(), file1.hashCode()),
                () -> Assertions.assertNotEquals(file1.hashCode(), file2.hashCode())
        );
    }

    @Test
    public void testToString(){
        File file = File.builder().path("path").text("text").build();
        Assertions.assertEquals(file.toString(), "File[id = 0, path = path, text = text]");
    }
}
