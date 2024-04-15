package com.textr.view;

import com.textr.filebuffer.FileBuffer;
import com.textr.util.Dimension2D;
import com.textr.util.Point;
import com.textr.Settings;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AnchorUpdaterTest {

    private final String url = "resources/test.txt";
    private final Point initPoint = Point.create(0,0);
    private final Dimension2D initDimension = Dimension2D.create(10,10);

    @Test
    public void testUpdateAnchor_NullAnchor(){
        Settings.defaultLineSeparator = "\r\n";
        BufferView view1 = BufferView.createFromFilePath(url, initPoint, initDimension);
        FileBuffer b = FileBuffer.createFromFilePath(url);
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnchorUpdater.updateAnchor(null, b.getCursor(), view1.getDimensions()));
    }

    @Test
    public void testUpdateAnchor_NullCursor(){
        Settings.defaultLineSeparator = "\r\n";
        BufferView view1 = BufferView.createFromFilePath(url, initPoint, initDimension);
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnchorUpdater.updateAnchor(view1.getAnchor(), null, view1.getDimensions()));
    }

    @Test
    public void testUpdateAnchor_NullDimensions(){
        Settings.defaultLineSeparator = "\r\n";
        BufferView view1 = BufferView.createFromFilePath(url, initPoint, initDimension);
        FileBuffer b = FileBuffer.createFromFilePath(url);
        Assertions.assertThrows(IllegalArgumentException.class, () -> AnchorUpdater.updateAnchor(view1.getAnchor(), b.getCursor(), null));
    }

    @Test
    public void testUpdateAnchor_ValidArguments(){
        Settings.defaultLineSeparator = "\r\n";
        BufferView view1 = BufferView.createFromFilePath(url, initPoint, initDimension);
        FileBuffer b = FileBuffer.createFromFilePath(url);
        Point anchor = view1.getAnchor();
        Point cursor = b.getCursor();
        AnchorUpdater.updateAnchor(anchor, cursor, view1.getDimensions());
        Assertions.assertTrue(anchor.getX() >= cursor.getX());
        Assertions.assertTrue(anchor.getY() >= cursor.getY());
    }
}
