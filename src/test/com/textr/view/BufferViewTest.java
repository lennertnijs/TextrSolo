package com.textr.view;

import com.textr.filebuffer.BufferState;
import com.textr.filebuffer.FileBuffer;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;
import com.textr.Settings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BufferViewTest {
//    private final String url = "resources/test.txt";
//    private final String url2 = "resources/test2.txt";
//    private final Point initPoint = Point.create(0,0);
//    private final Point initPoint2 = Point.create(1, 5);
//    private final Dimension2D initDimension = Dimension2D.create(10,10);
//    private final Dimension2D initDimension2 = Dimension2D.create(15,15);
//    private final Dimension2D initDimension3 = Dimension2D.create(10,2);
//    private final BufferView view1 = BufferView.createFromFilePath("resources/write1.txt", initPoint, initDimension3);
//    @Test
//    void testCreateFromFilePath_NullURL() {
//        assertThrows(IllegalArgumentException.class, () -> BufferView.createFromFilePath(null, initPoint, initDimension));
//    }
//
//    @Test
//    void testCreateFromFilePath_NullPosition() {
//        assertThrows(IllegalArgumentException.class, () -> BufferView.createFromFilePath(url, null, initDimension));
//    }
//
//    @Test
//    void testCreateFromFilePath_NullDimensions() {
//        assertThrows(IllegalArgumentException.class, () -> BufferView.createFromFilePath(url, initPoint, null));
//    }
//
//    @Test
//    void testCreateFromFilePath_ValidArguments() {
//        BufferView view1 = BufferView.createFromFilePath(url, initPoint, initDimension);
//        FileBuffer b = FileBuffer.createFromFilePath(url);
//
//        assertNotNull(view1);
//        assertEquals(initPoint, view1.getPosition());
//        assertEquals(initDimension, view1.getDimensions());
//        assertEquals(b, view1.getBuffer());
//        assertEquals(Point.create(0, 0), view1.getAnchor());
//    }
//
//    @Test
//    void testSetPosition_Invalid() {
//        BufferView view1 = BufferView.createFromFilePath(url, initPoint, initDimension);
//        assertThrows(IllegalArgumentException.class, () -> view1.setPosition(null));
//    }
//    @Test
//    void testSetDimensions_Invalid() {
//        BufferView view1 = BufferView.createFromFilePath(url, initPoint, initDimension);
//        assertThrows(IllegalArgumentException.class, () -> view1.setDimensions(null));
//
//    }
//    @Test
//    void testSetArguments_Valid() {
//        BufferView view1 = BufferView.createFromFilePath(url, initPoint, initDimension);
//        view1.setPosition(initPoint2);
//        view1.setDimensions(initDimension2);
//        assertEquals(initPoint2, view1.getPosition());
//        assertEquals(initDimension2, view1.getDimensions());
//        assertEquals(Point.create(0, 0), view1.getAnchor());
//
//    }
//
//    @Test
//    void moveCursor() {
//        for(int i=0; i<3; i++){
//            view1.moveCursor(Direction.DOWN);
//        }
//        assertEquals(view1.getAnchor().getY(), view1.getBuffer().getCursor().getY());
//    }
//    @Test
//    void moveCursor_NullDirection() {
//        assertThrows(IllegalArgumentException.class, () -> view1.moveCursor(null));
//    }
//    @Test
//    void createNewline() {
//        for( int i=0; i<3; i++){
//            view1.createNewline();
//        }
//        assertEquals(view1.getAnchor().getY(), view1.getBuffer().getCursor().getY());
//    }
//    @Test
//    void insertCharacter() {
//        for( int i=0; i<20; i++){
//            view1.insertCharacter('d');
//        }
//        assertTrue(view1.getAnchor().getX()<=view1.getBuffer().getCursor().getX());
//        assertTrue(view1.getAnchor().getX()+10>=view1.getBuffer().getCursor().getX());
//        assertEquals(view1.getBuffer().getState(), BufferState.DIRTY);
//    }
//    @Test
//    void deletePrevChar() {
//        assertEquals(view1.getBuffer().getState(), BufferState.CLEAN);
//        for( int i=0; i<20; i++){
//            view1.insertCharacter('d');
//        }
//        for( int i=0; i<20; i++){
//            view1.deletePrevChar();
//        }
//        assertTrue(view1.getAnchor().getX()<=view1.getBuffer().getCursor().getX());
//        assertTrue(view1.getAnchor().getX()+10>=view1.getBuffer().getCursor().getX());
//        assertEquals(view1.getBuffer().getState(), BufferState.DIRTY);
//    }
//    @Test
//    void deleteNextChar() {
//        assertEquals(view1.getBuffer().getState(), BufferState.CLEAN);
//        for( int i=0; i<20; i++){
//            view1.insertCharacter('d');
//        }
//        for( int i=0; i<20; i++){
//            view1.moveCursor(Direction.LEFT);
//            view1.deleteNextChar();
//        }
//        assertTrue(view1.getAnchor().getX()<=view1.getBuffer().getCursor().getX());
//        assertTrue(view1.getAnchor().getX()+10>=view1.getBuffer().getCursor().getX());
//        assertEquals(view1.getBuffer().getState(), BufferState.DIRTY);
//    }
//
//    @Test
//    void testEquals() {
//        BufferView view1 = BufferView.createFromFilePath(url, initPoint, initDimension);
//        BufferView view2 = view1.copy();
//        BufferView view3 = BufferView.createFromFilePath(url2, initPoint, initDimension);
//        BufferView view4 = view1.copy();
//        view4.setPosition(initPoint2);
//        BufferView view5 = BufferView.createFromFilePath(url, initPoint, initDimension2);
//        assertEquals(view1, view2);
//        assertNotEquals(view1, view3);
//        assertNotEquals(view1, view4);
//        assertNotEquals(view1, view5);
//    }
//
//    @Test
//    void testHashCode() {
//        BufferView view1 = BufferView.createFromFilePath(url, initPoint, initDimension);
//        BufferView view2 = BufferView.createFromFilePath(url2, initPoint2, initDimension2);
//        int result = view1.getBuffer().hashCode();
//        result = result * 31 + view1.getDimensions().hashCode();
//        result = result * 31 + view1.getPosition().hashCode();
//        result = result * 31 + view1.getAnchor().hashCode();
//        assertEquals(view1.hashCode(), result);
//        assertNotEquals(view2.hashCode(), result);
//    }
//
//    @Test
//    void testToString() {
//        Settings.defaultLineSeparator = "\r\n";
//        BufferView view1 = BufferView.createFromFilePath(url, initPoint, initDimension);
//        assertEquals(view1.toString(), String.format("BufferView[buffer = %s, position = %s, dimensions = %s, anchor = %s]",
//                view1.getBuffer(), view1.getPosition(), view1.getDimensions(), view1.getAnchor()));
//    }
}
