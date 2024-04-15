package com.textr.view;

import com.textr.drawer.ViewDrawer;
import com.textr.filebuffer.BufferState;
import com.textr.Settings;
import com.textr.terminal.ITerminalService;
import com.textr.terminal.TerminalService;
import com.textr.util.Dimension2D;
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
    private final ITerminalService terminal = new TerminalService(); // TODO: Replace with Mock

    private BufferView view1 ;

    private BufferView view2 ;

    private BufferView view6 ;

    @BeforeEach
    void initialise(){
        List<View> views = new ArrayList<>();
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
        viewService = new ViewService(repo, new ViewDrawer(terminal), terminal);
        Dimension2D terminalDimensions = Dimension2D.create(10, 12);
        LayoutGenerator.generate(terminalDimensions);
    }

    @AfterEach
    void tearDown() {}

    @Test
    void ViewService_NullViewRepo(){
        assertThrows(IllegalArgumentException.class, () -> new ViewService(null, new ViewDrawer(terminal), terminal));
    }

    // TODO: Add null terminal/drawer tests (preferably add into one general constructor test)

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
    void saveBuffer() {
        assertEquals(view1.getBuffer().getState(), BufferState.CLEAN);
        view1.insertCharacter('d');
        assertEquals(view1.getBuffer().getState(), BufferState.DIRTY);
        viewService.saveBuffer();
        assertEquals(view1.getBuffer().getState(), BufferState.CLEAN);
        view1.deletePrevChar();
        viewService.saveBuffer();
    }
}