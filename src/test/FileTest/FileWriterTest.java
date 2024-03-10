package FileTest;

import com.Textr.File.FileReader;
import com.Textr.File.FileWriter;
import com.Textr.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileWriterTest {

    @BeforeEach
    public void initialise(){
        Settings.defaultLineSeparator = "\r\n";
    }

    @Test
    public void testWrite(){
        String text = "first line\r\nsecond line\r\nthird line";
        String text2 = "first line\rsecond line";
        Assertions.assertAll(
                () -> FileWriter.write(text, "resources/write1.txt"),
                () -> Assertions.assertEquals(text, FileReader.readContents("resources/write1.txt")),

                () -> FileWriter.write(text2, "resources/write2.txt"),
                () -> Assertions.assertNotEquals(text2, FileReader.readContents("resources/write2.txt"))
        );
    }

    @Test
    public void testWriteNullParam(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> FileWriter.write(null, "path")),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> FileWriter.write("text", null))
        );
    }

    @Test
    public void testWriteInvalidPath(){
        FileWriter.write("text", "invalid");
    }
}
