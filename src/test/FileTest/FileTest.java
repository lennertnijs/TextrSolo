package FileTest;

import com.Textr.File.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileTest {

    private File file1;
    private File file2;

    @BeforeEach
    public void initialise(){
        file1 = File.create("path1");
        file2 = File.create("path2");
    }


    @Test
    public void testCreationAndGetters(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(file1.getUrl(), "path1")
        );
    }

    @Test
    public void testCreationIllegal(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> File.create(null))
        );
    }



    @Test
    public void testEqualsAndHashCode(){
        Assertions.assertAll(
                () -> Assertions.assertNotEquals(file1, file2),
                () -> Assertions.assertNotEquals(file1, new Object()),
                () -> Assertions.assertNotEquals(file1.hashCode(), file2.hashCode())
        );
    }
    @Test
    public void testToString(){
        String expected = "File[url = path1]";
        Assertions.assertEquals(file1.toString(), expected);
    }
}
