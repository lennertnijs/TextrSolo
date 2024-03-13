package ViewTest;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.Util.Dimension2D;
import com.Textr.Util.Point;
import com.Textr.View.View;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import com.Textr.Settings;
public class ViewTest {
    private final String url = "resources/test.txt";
    private final String url2 = "resources/test2.txt";
    private final Point initPoint = Point.create(0,0);
    private final Point initPoint2 = Point.create(1, 5);
    private final Dimension2D initDimension = Dimension2D.create(10,10);
    private final Dimension2D initDimension2 = Dimension2D.create(15,15);
    @Test
    public void testCreateFromFilePath_NullURL() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            View.createFromFilePath(null, initPoint, initDimension);
        });
    }

    @Test
    public void testCreateFromFilePath_NullPosition() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            View.createFromFilePath(url, null, initDimension);
        });
    }

    @Test
    public void testCreateFromFilePath_NullDimensions() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            View.createFromFilePath(url, initPoint, null);
        });
    }

    @Test
    public void testCreateFromFilePath_ValidArguments() {

        Settings.defaultLineSeparator = "\r\n";
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        FileBuffer b = FileBuffer.createFromFilePath(url);

        Assertions.assertNotNull(view1);
        Assertions.assertEquals(initPoint, view1.getPosition());
        Assertions.assertEquals(initDimension, view1.getDimensions());
        Assertions.assertEquals(b, view1.getBuffer());
        Assertions.assertEquals(Point.create(0, 0), view1.getAnchor());
    }

    @Test
    public void testSetPosition_Invalid() {
        Settings.defaultLineSeparator = "\r\n";
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            view1.setPosition(null);
        });
    }
    @Test
    public void testSetDimensions_Invalid() {
        Settings.defaultLineSeparator = "\r\n";
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            view1.setDimensions(null);
        });

    }
    @Test
    public void testSetArguments_Valid() {
        Settings.defaultLineSeparator = "\r\n";
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        view1.setPosition(initPoint2);
        view1.setDimensions(initDimension2);
        Assertions.assertEquals(initPoint2, view1.getPosition());
        Assertions.assertEquals(initDimension2, view1.getDimensions());
        Assertions.assertEquals(Point.create(0, 0), view1.getAnchor());

    }

    @Test
    public void testEquals() {
        Settings.defaultLineSeparator = "\r\n";
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        View view2 = view1.copy();
        View view3 = View.createFromFilePath(url2, initPoint, initDimension);
        View view4 = view1.copy();
        view4.setPosition(initPoint2);
        View view5 = View.createFromFilePath(url, initPoint, initDimension2);
        Assertions.assertTrue(view1.equals(view2));
        Assertions.assertFalse(view1.equals(view3));
        Assertions.assertFalse(view1.equals(view4));
        Assertions.assertFalse(view1.equals(view5));
    }

    @Test
    public void testHashCode() {
        Settings.defaultLineSeparator = "\r\n";
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        View view2 = View.createFromFilePath(url2, initPoint2, initDimension2);
        int result = view1.getBuffer().hashCode();
        result = result * 31 + view1.getDimensions().hashCode();
        result = result * 31 + view1.getPosition().hashCode();
        result = result * 31 + view1.getAnchor().hashCode();
        Assertions.assertEquals(view1.hashCode(), result);
        Assertions.assertNotEquals(view2.hashCode(), result);
    }

    @Test
    public void testToString() {

        Settings.defaultLineSeparator = "\r\n";
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        Assertions.assertEquals(view1.toString(), String.format("View[buffer = %s, position = %s, dimensions = %s, anchor = %s]",
                view1.getBuffer(), view1.getPosition(), view1.getDimensions(), view1.getAnchor()));
    }

    @Test
    public void testLeftOff() {
        Settings.defaultLineSeparator = "\r\n";
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        View view2 = View.createFromFilePath(url2, initPoint2, initDimension2);
        View view3 = view1.copy();
        Assertions.assertTrue(view1.leftOff(view2));
        Assertions.assertFalse(view1.leftOff(view3));
    }
}
