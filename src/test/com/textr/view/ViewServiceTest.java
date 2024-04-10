package com.textr.view;

import com.textr.filebuffer.BufferState;
import com.textr.Settings;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.textr.view.LayoutGenerator.setViewRepo;
import static org.junit.jupiter.api.Assertions.*;

class ViewServiceTest {
    private ViewTreeRepo repo ;
    private ViewService viewService;

    private BufferView view1 ;

    private BufferView view2 ;

    private BufferView view6 ;

    @BeforeEach
    void initialise(){
        List<BufferView> views = new ArrayList<>();
        Point initPoint = Point.create(0,0);
        Dimension2D initDimension = Dimension2D.create(10,10);
        Settings.defaultLineSeparator = "\r\n";
        view1 = BufferView.createFromFilePath("resources/write1.txt", initPoint, initDimension);
        views.add(view1);
        view2 = BufferView.createFromFilePath("resources/test2ndfile.txt", initPoint, initDimension);
        views.add(view2);
        BufferView view3 = BufferView.createFromFilePath("resources/test3rdfile.txt", initPoint, initDimension);
        views.add(view3);
        BufferView view4 = BufferView.createFromFilePath("resources/save.txt", initPoint, initDimension);
        views.add(view4);
        BufferView view5 = BufferView.createFromFilePath("resources/test2.txt", initPoint, initDimension);
        views.add(view5);
        view6 = BufferView.createFromFilePath("resources/test.txt", initPoint, initDimension);
        views.add(view6);
        repo = new ViewTreeRepo();
        setViewRepo(repo);
        repo.addAll(views);
        repo.setActive(view1);
        viewService = new ViewService(repo);
        Dimension2D terminalDimensions = Dimension2D.create(10, 12);
        LayoutGenerator.generate(terminalDimensions);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void ViewService_NullViewRepo(){
        assertThrows(IllegalArgumentException.class, () -> new ViewService(null));
    }

    @Test
    void initialiseViews() {
        assertEquals(repo.getSize(), 6);

        String[] dummyArray = {};
        viewService.initialiseViews(dummyArray);
        assertFalse(Settings.RUNNING);
    }

    @Test
    void setActiveViewToNext() {
        viewService.setActiveViewToNext();
        assertSame(repo.getActive(), view2);
        viewService.setActiveViewToNext();
        assertNotSame(repo.getActive(), view1);
    }

    @Test
    void setActiveViewToPrevious() {
        viewService.setActiveViewToNext();
        viewService.setActiveViewToPrevious();
        assertSame(repo.getActive(), view1);
        assertNotSame(repo.getActive(), view2);
        viewService.setActiveViewToPrevious();
        assertSame(repo.getActive(), view6);
        assertNotSame(repo.getActive(), view1);
    }

    @Test
    void moveCursor() {
        for(int i=0; i<3; i++){
            viewService.moveCursor(Direction.DOWN);
        }
        assertEquals(repo.getActive().getAnchor().getY(), repo.getActive().getBuffer().getCursor().getY());
    }

    @Test
    void moveCursor_NullDirection() {
        assertThrows(IllegalArgumentException.class, () -> viewService.moveCursor(null));
    }

    @Test
    void createNewline() {
        for( int i=0; i<3; i++){
            viewService.createNewline();
        }
        assertEquals(repo.getActive().getAnchor().getY(), repo.getActive().getBuffer().getCursor().getY());
    }

    @Test
    void insertCharacter() {
        for( int i=0; i<20; i++){
            viewService.insertCharacter('d');
        }
        assertTrue(repo.getActive().getAnchor().getX()<=repo.getActive().getBuffer().getCursor().getX());
        assertTrue(repo.getActive().getAnchor().getX()+10>=repo.getActive().getBuffer().getCursor().getX());
        assertEquals(repo.getActive().getBuffer().getState(), BufferState.DIRTY);

    }

    @Test
    void deletePrevChar() {
        assertEquals(repo.getActive().getBuffer().getState(), BufferState.CLEAN);
        for( int i=0; i<20; i++){
            viewService.insertCharacter('d');
        }
        for( int i=0; i<20; i++){
            viewService.deletePrevChar();
        }
        assertTrue(repo.getActive().getAnchor().getX()<=repo.getActive().getBuffer().getCursor().getX());
        assertTrue(repo.getActive().getAnchor().getX()+10>=repo.getActive().getBuffer().getCursor().getX());
        assertEquals(repo.getActive().getBuffer().getState(), BufferState.DIRTY);
    }

    @Test
    void deleteNextChar() {
        assertEquals(repo.getActive().getBuffer().getState(), BufferState.CLEAN);
        for( int i=0; i<20; i++){
            viewService.insertCharacter('d');
        }
        for( int i=0; i<20; i++){
            viewService.moveCursor(Direction.LEFT);
            viewService.deleteNextChar();
        }
        assertTrue(repo.getActive().getAnchor().getX()<=repo.getActive().getBuffer().getCursor().getX());
        assertTrue(repo.getActive().getAnchor().getX()+10>=repo.getActive().getBuffer().getCursor().getX());
        assertEquals(repo.getActive().getBuffer().getState(), BufferState.DIRTY);
    }

    @Test
    void saveBuffer() {
        assertEquals(repo.getActive().getBuffer().getState(), BufferState.CLEAN);
        viewService.insertCharacter('d');
        assertEquals(repo.getActive().getBuffer().getState(), BufferState.DIRTY);
        viewService.saveBuffer();
        assertEquals(repo.getActive().getBuffer().getState(), BufferState.CLEAN);
        viewService.deletePrevChar();
        viewService.saveBuffer();
    }
}