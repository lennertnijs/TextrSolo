package ViewTest;

import com.Textr.FileBuffer.BufferState;
import com.Textr.Settings;
import com.Textr.Util.Dimension2D;
import com.Textr.Util.Direction;
import com.Textr.Util.Point;
import com.Textr.View.LayoutGenerator;
import com.Textr.View.View;
import com.Textr.View.ViewService;
import com.Textr.View.ViewTreeRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.Textr.View.LayoutGenerator.setViewRepo;
import static org.junit.jupiter.api.Assertions.*;

class ViewServiceTest {
    private ViewTreeRepo repo ;
    private ViewService viewService;

    private View view1 ;

    private View view2 ;

    private View view6 ;

    @BeforeEach
    void initialise(){
        List<View> views = new ArrayList<>();
        Point initPoint = Point.create(0,0);
        Dimension2D initDimension = Dimension2D.create(10,10);
        Settings.defaultLineSeparator = "\r\n";
        view1 = View.createFromFilePath("resources/write1.txt", initPoint, initDimension);
        views.add(view1);
        view2 = View.createFromFilePath("resources/test2ndfile.txt", initPoint, initDimension);
        views.add(view2);
        View view3 = View.createFromFilePath("resources/test3rdfile.txt", initPoint, initDimension);
        views.add(view3);
        View view4 = View.createFromFilePath("resources/save.txt", initPoint, initDimension);
        views.add(view4);
        View view5 = View.createFromFilePath("resources/test2.txt", initPoint, initDimension);
        views.add(view5);
        view6 = View.createFromFilePath("resources/test.txt", initPoint, initDimension);
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