package FileTest;

import com.Textr.File.File;
import com.Textr.File.FileIdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileTest {

    private File file1;
    private File file2;

    @BeforeEach
    public void initialise(){
        FileIdGenerator.resetGenerator();
        file1 = File.create("path1", "text1");
        file2 = File.create("path2", "text2");
    }


    @Test
    public void testCreationAndGetters(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(file1.getId(), 0),
                () -> Assertions.assertEquals(file1.getUrl(), "path1"),
                () -> Assertions.assertEquals(file1.getText(), "text1")
        );
    }

    @Test
    public void testCreationIllegal(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> File.create(null, "Text")),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> File.create("Url", null))
        );
    }

    @Test
    public void testSetText(){
        file1.setText("new text");
        Assertions.assertAll(
                () -> Assertions.assertEquals(file1.getText(), "new text"),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> file1.setText(null))
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
        String expected = "File[id = 0, url = path1, text = text1]";
        Assertions.assertEquals(file1.toString(), expected);
    }

    @Test
    public void testClone(){
        File clone = file1.clone();
        Assertions.assertAll(
                () -> Assertions.assertEquals(clone, file1),
                () -> clone.setText("new text"),
                () -> System.out.println(clone.getText()),
                () -> Assertions.assertNotEquals(clone, file1)
        );
    }
}
