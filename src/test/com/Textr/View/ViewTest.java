package com.Textr.View;

import com.Textr.FileBuffer.FileBuffer;
import com.Textr.Util.Dimension2D;
import com.Textr.Util.Point;
import com.Textr.Settings;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest {
    private final String url = "resources/test.txt";
    private final String url2 = "resources/test2.txt";
    private final Point initPoint = Point.create(0,0);
    private final Point initPoint2 = Point.create(1, 5);
    private final Dimension2D initDimension = Dimension2D.create(10,10);
    private final Dimension2D initDimension2 = Dimension2D.create(15,15);

    @Test
    void testCreateFromFilePath_NullURL() {
        assertThrows(IllegalArgumentException.class, () -> View.createFromFilePath(null, initPoint, initDimension));
    }

    @Test
    void testCreateFromFilePath_NullPosition() {
        assertThrows(IllegalArgumentException.class, () -> View.createFromFilePath(url, null, initDimension));
    }

    @Test
    void testCreateFromFilePath_NullDimensions() {
        assertThrows(IllegalArgumentException.class, () -> View.createFromFilePath(url, initPoint, null));
    }

    @Test
    void testCreateFromFilePath_ValidArguments() {
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        FileBuffer b = FileBuffer.createFromFilePath(url);

        assertNotNull(view1);
        assertEquals(initPoint, view1.getPosition());
        assertEquals(initDimension, view1.getDimensions());
        assertEquals(b, view1.getBuffer());
        assertEquals(Point.create(0, 0), view1.getAnchor());
    }

    @Test
    void testSetPosition_Invalid() {
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        assertThrows(IllegalArgumentException.class, () -> view1.setPosition(null));
    }
    @Test
    void testSetDimensions_Invalid() {
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        assertThrows(IllegalArgumentException.class, () -> view1.setDimensions(null));

    }
    @Test
    void testSetArguments_Valid() {
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        view1.setPosition(initPoint2);
        view1.setDimensions(initDimension2);
        assertEquals(initPoint2, view1.getPosition());
        assertEquals(initDimension2, view1.getDimensions());
        assertEquals(Point.create(0, 0), view1.getAnchor());

    }

    @Test
    void testEquals() {
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        View view2 = view1.copy();
        View view3 = View.createFromFilePath(url2, initPoint, initDimension);
        View view4 = view1.copy();
        view4.setPosition(initPoint2);
        View view5 = View.createFromFilePath(url, initPoint, initDimension2);
        assertEquals(view1, view2);
        assertNotEquals(view1, view3);
        assertNotEquals(view1, view4);
        assertNotEquals(view1, view5);
    }

    @Test
    void testHashCode() {
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        View view2 = View.createFromFilePath(url2, initPoint2, initDimension2);
        int result = view1.getBuffer().hashCode();
        result = result * 31 + view1.getDimensions().hashCode();
        result = result * 31 + view1.getPosition().hashCode();
        result = result * 31 + view1.getAnchor().hashCode();
        assertEquals(view1.hashCode(), result);
        assertNotEquals(view2.hashCode(), result);
    }

    @Test
    void testToString() {
        Settings.defaultLineSeparator = "\r\n";
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        assertEquals(view1.toString(), String.format("View[buffer = %s, position = %s, dimensions = %s, anchor = %s]",
                view1.getBuffer(), view1.getPosition(), view1.getDimensions(), view1.getAnchor()));
    }

    @Test
    void testLeftOff() {
        View view1 = View.createFromFilePath(url, initPoint, initDimension);
        View view2 = View.createFromFilePath(url2, initPoint2, initDimension2);
        View view3 = view1.copy();
        assertTrue(view1.leftOff(view2));
        assertFalse(view1.leftOff(view3));
    }
}
