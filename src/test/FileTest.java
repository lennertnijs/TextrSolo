import com.Textr.FileModel.File;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileTest {

    @Test
    public void testConstructorAndGetters(){
        File file1 = File.builder().path("/filePath1").text("testText1").build();
        File file2 = File.builder().path("/filePath2").text("testText2").build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(file1.getId(), 0),
                () -> Assertions.assertEquals(file1.getPath(), "/filePath1"),
                () -> Assertions.assertEquals(file1.getText(), "testText1"),
                () -> Assertions.assertEquals(file2.getId(), 1),
                () -> Assertions.assertEquals(file2.getPath(), "/filePath2"),
                () -> Assertions.assertEquals(file2.getText(), "testText2")
        );
    }
}
