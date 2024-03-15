package FileTest;

import com.Textr.File.FileReader;
import com.Textr.Settings;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class FileReaderTest {

    @BeforeEach
    public void initialise(){
        Settings.defaultLineSeparator = "\r\n";
    }
    @Test
    public void readValidFile(){
        String expected = "tested\r\ntest\r\nte";
        String reading = FileReader.readContents("resources/test.txt");
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected, reading)
        );
    }

    @Test
    public void readInvalidFilePath(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> FileReader.readContents("resources/invalid.txt"));
        Assertions.assertThrows(IllegalArgumentException.class, () -> FileReader.readContents(null));
    }

    @Test
    public void readFileInvalidLineSeparatorSetting(){
        Settings.defaultLineSeparator = "\s";
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalStateException.class, () -> FileReader.readContents("resources/test.txt"))
        );
    }

    // Cannot test because standard newline on windows is CRLF
//    @Test
//    public void readFileAnotherLS(){
//        Settings.defaultLineSeparator = "\r";
//        String expected = "tested\rtest\rte";
//        String reading = FileReader.readContents("resources/test.txt");
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(expected, reading)
//        );
//    }
}
