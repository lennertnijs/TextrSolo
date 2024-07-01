package com.textr.view;

import com.textr.drawer.ViewDrawer;
import com.textr.Settings;
import com.textr.terminal.MockCommunicator;
import com.textr.terminal.TerminalService;
import com.textr.terminal.TermiosTerminalService;
import com.textr.util.Dimension2D;
import com.textr.util.Point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.textr.view.LayoutGenerator.setViewRepo;
import static org.junit.jupiter.api.Assertions.*;

class ViewServiceTest {
    private ViewTreeRepo repo ;
    private ViewService viewService;
    private final TerminalService terminal = new TermiosTerminalService(); // TODO: Replace with Mock
    private final MockCommunicator serviceCommunicator = new MockCommunicator();

    private View view1;
    private View view2;
    private View view6;

//    @BeforeEach
//    void initialise(){
//        List<View> views = new ArrayList<>();
//        Point initPoint = new Point(0,0);
//        Dimension2D initDimension = new Dimension2D(10,10);
//        Settings.defaultLineSeparator = "\r\n";
//        view1 = new MockView(initPoint, initDimension);
//        views.add(view1);
//        view2 = new MockView(initPoint, initDimension);
//        views.add(view2);
//        View view3 = new MockView(initPoint, initDimension);
//        views.add(view3);
//        View view4 = new MockView(initPoint, initDimension);
//        views.add(view4);
//        View view5 = new MockView(initPoint, initDimension);
//        views.add(view5);
//        view6 = new MockView(initPoint, initDimension);
//        views.add(view6);
//        repo = new ViewTreeRepo();
//        setViewRepo(repo);
//        repo.addAll(views);
//        repo.setActive(view1);
//        viewService = new ViewService(repo, new ViewDrawer(terminal), terminal.getTerminalArea());
//        serviceCommunicator.setPermissions(true);
//        Dimension2D terminalDimensions = new Dimension2D(10, 12);
//        LayoutGenerator.generate(terminalDimensions);
//    }

//    @Test
//    void ViewService_NullViewRepo(){
//        assertThrows(NullPointerException.class, () -> new ViewService(null,
//                new ViewDrawer(terminal),
//                terminal.getTerminalArea()));
//    }

    // TODO: Add null terminal/drawer tests (preferably add into one general constructor test)

//    @Test
//    void initialiseViews() {
//        assertEquals(repo.getSize(), 6);
//
//        String[] dummyArray = {};
//        viewService.initialiseViews(dummyArray, serviceCommunicator);
//        assertFalse(Settings.RUNNING);
//    }

//    @Test
//    void setActiveViewToNext() {
//        viewService.setActiveViewToNext();
//        assertSame(repo.getActive(), view2);
//        viewService.setActiveViewToNext();
//        assertNotSame(repo.getActive(), view1);
//    }

//    @Test
//    void setActiveViewToPrevious() {
//        viewService.setActiveViewToNext();
//        viewService.setActiveViewToPrevious();
//        assertSame(repo.getActive(), view1);
//        assertNotSame(repo.getActive(), view2);
//        viewService.setActiveViewToPrevious();
//        assertSame(repo.getActive(), view6);
//        assertNotSame(repo.getActive(), view1);
//    }
}