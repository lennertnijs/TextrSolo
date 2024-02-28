package FileBuffer;

import com.Textr.FileBuffer.Text;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TextTest {

    private String text1;
    private String[] text2;
    private Text firstText;
    private Text secText;
    @BeforeEach
    public void initialise(){
        text1 = "TEXT1\r\nTEXT 1\r\nText 1";
        text2 = new String[]{"TEXT2", "TEXT 2", "Text 2"};
        firstText = Text.create(text1);
        secText = Text.create(text2);
    }

    @Test
    public void testConstructors(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(firstText.getAmountOfLines(), 3),
                () -> Assertions.assertEquals(secText.getAmountOfLines(), 3),
                () -> Assertions.assertEquals(firstText.getAmountOfChars(), 17),
                () -> Assertions.assertEquals(secText.getAmountOfChars(), 17),
                () -> Assertions.assertEquals(firstText.getLinesAsText(), text1),
                () -> Assertions.assertEquals(secText.getLinesAsText(), String.join(System.lineSeparator(), text2)),
                () -> Assertions.assertArrayEquals(firstText.getLines(), text1.split(System.lineSeparator())),
                () -> Assertions.assertArrayEquals(secText.getLines(), text2)
        );
    }

    @Test
    public void testAddACharacter(){
        Text shouldBeText1 = Text.create("TEXT1\r\nTEXT 19\r\nText 1");
        Text shouldBeText2 = Text.create(new String[]{"9TEXT2", "TEXT 2", "Text 2"});
        firstText.addCharacter('9', 1, 6);
        secText.addCharacter('9', 0, 0);
        Assertions.assertAll(
                () -> Assertions.assertEquals(shouldBeText1, firstText),
                () -> Assertions.assertEquals(shouldBeText2, secText)
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Text text = Text.create("TEXT1\r\nTEXT 1\r\nText 1");
        Assertions.assertAll(
                () -> Assertions.assertEquals(text, firstText),
                () -> Assertions.assertNotEquals(text, secText),
                () -> Assertions.assertEquals(text, text),
                () -> Assertions.assertNotEquals(text, new Object()),
                () -> Assertions.assertEquals(text.hashCode(), firstText.hashCode()),
                () -> Assertions.assertEquals(text.hashCode(), text.hashCode()),
                () -> Assertions.assertNotEquals(text.hashCode(), secText.hashCode())
        );
    }

    @Test
    public void testToString(){
        String expected = "Text[Lines: TEXT1\r\nTEXT 1\r\nText 1]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(expected, firstText.toString())
        );
    }
}
