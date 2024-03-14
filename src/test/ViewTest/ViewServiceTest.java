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
import org.junit.jupiter.api.Assertions;
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
    public void initialiseTest(){
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
    public void ViewServiceTest_NullViewRepo(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new ViewService(null);
        });
    }
    @Test
    void initialiseViewsTest() {
        Assertions.assertEquals(repo.getSize(), 6);

        String[] dummyArray = {};
        viewService.initialiseViews(dummyArray);
        Assertions.assertFalse(Settings.RUNNING);
    }

    @Test
    public void generateViewPositionsAndDimensionsTest(){
    }
    @Test
    void setActiveViewToNextTest() {
        viewService.setActiveViewToNext();
        Assertions.assertEquals(repo.getActive(), view2);
        viewService.setActiveViewToNext();
        Assertions.assertNotEquals(repo.getActive(), view1);
    }

    @Test
    void setActiveViewToPreviousTest() {
        viewService.setActiveViewToNext();
        viewService.setActiveViewToPrevious();
        Assertions.assertEquals(repo.getActive(), view1);
        Assertions.assertNotEquals(repo.getActive(), view2);
        viewService.setActiveViewToPrevious();
        Assertions.assertEquals(repo.getActive(), view6);
        Assertions.assertNotEquals(repo.getActive(), view1);
    }

    @Test
    public void moveCursorTest() {
        for( int i=0; i<3; i++){
            viewService.moveCursor(Direction.DOWN);
        }
        assertEquals(repo.getActive().getAnchor().getY(), repo.getActive().getBuffer().getCursor().getY());
    }

    @Test
    public void moveCursorTest_NullDirection() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            viewService.moveCursor(null);
        });
    }

    @Test
    public void createNewlineTest() {
        for( int i=0; i<3; i++){
            viewService.createNewline();
        }
        assertEquals(repo.getActive().getAnchor().getY(), repo.getActive().getBuffer().getCursor().getY());
    }

    @Test
    public void insertCharacterTest() {
        for( int i=0; i<20; i++){
            viewService.insertCharacter('d');
        }
        Assertions.assertTrue(repo.getActive().getAnchor().getX()<=repo.getActive().getBuffer().getCursor().getX());
        Assertions.assertTrue(repo.getActive().getAnchor().getX()+10>=repo.getActive().getBuffer().getCursor().getX());
        Assertions.assertEquals(repo.getActive().getBuffer().getState(), BufferState.DIRTY);

    }

    @Test
    public void deletePrevCharTest() {
        Assertions.assertEquals(repo.getActive().getBuffer().getState(), BufferState.CLEAN);
        for( int i=0; i<20; i++){
            viewService.insertCharacter('d');
        }
        for( int i=0; i<20; i++){
            viewService.deletePrevChar();
        }
        Assertions.assertTrue(repo.getActive().getAnchor().getX()<=repo.getActive().getBuffer().getCursor().getX());
        Assertions.assertTrue(repo.getActive().getAnchor().getX()+10>=repo.getActive().getBuffer().getCursor().getX());
        Assertions.assertEquals(repo.getActive().getBuffer().getState(), BufferState.DIRTY);
    }

    @Test
    public void deleteNextCharTest() {
        Assertions.assertEquals(repo.getActive().getBuffer().getState(), BufferState.CLEAN);
        for( int i=0; i<20; i++){
            viewService.insertCharacter('d');
        }
        for( int i=0; i<20; i++){
            viewService.moveCursor(Direction.LEFT);
            viewService.deleteNextChar();
        }
        Assertions.assertTrue(repo.getActive().getAnchor().getX()<=repo.getActive().getBuffer().getCursor().getX());
        Assertions.assertTrue(repo.getActive().getAnchor().getX()+10>=repo.getActive().getBuffer().getCursor().getX());
        Assertions.assertEquals(repo.getActive().getBuffer().getState(), BufferState.DIRTY);
    }

    @Test
    public void saveBufferTest() {
        Assertions.assertEquals(repo.getActive().getBuffer().getState(), BufferState.CLEAN);
        viewService.insertCharacter('d');
        Assertions.assertEquals(repo.getActive().getBuffer().getState(), BufferState.DIRTY);
        viewService.saveBuffer();
        Assertions.assertEquals(repo.getActive().getBuffer().getState(), BufferState.CLEAN);
        viewService.deletePrevChar();
        viewService.saveBuffer();
    }
}