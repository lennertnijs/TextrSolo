package ViewTest;
import com.Textr.Settings;
import com.Textr.Util.Dimension2D;
import com.Textr.Util.Point;
import com.Textr.View.LayoutGenerator;
import com.Textr.View.ViewTreeRepo;
import com.Textr.View.View;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.Textr.View.LayoutGenerator.setViewRepo;

public class ViewTreeRepoTest {

    private View view1 ;

    private View view2 ;

    private View view3 ;

    private View view4 ;

    private View view5 ;

    private View view6 ;

    private Dimension2D terminalDimensions;
    private ViewTreeRepo repo;

    private List<View> views;


    @BeforeEach
    public void initialise(){
        views = new ArrayList<>();
        Point initPoint = Point.create(0,0);
        Dimension2D initDimension = Dimension2D.create(10,10);
        Settings.defaultLineSeparator = "\r\n";
        view1 = View.createFromFilePath("resources/test.txt", initPoint, initDimension);
        views.add(view1);
        view2 = View.createFromFilePath("resources/test2ndfile.txt", initPoint, initDimension);
        views.add(view2);
        view3 = View.createFromFilePath("resources/test3rdfile.txt", initPoint, initDimension);
        views.add(view3);
        view4 = View.createFromFilePath("resources/save.txt", initPoint, initDimension);
        views.add(view4);
        view5 = View.createFromFilePath("resources/test2.txt", initPoint, initDimension);
        views.add(view5);
        view6 = View.createFromFilePath("resources/write1.txt", initPoint, initDimension);
        views.add(view6);
        repo = new ViewTreeRepo();
        setViewRepo(repo);
        repo.addAll(views);
        terminalDimensions = Dimension2D.create(100,100);
        LayoutGenerator.generate(terminalDimensions);
    }

    @Test
    public void testAdd(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(repo.get(0), views.get(0)),
                () -> Assertions.assertEquals(repo.get(1), views.get(1)),
                () -> Assertions.assertEquals(repo.get(2), views.get(2)),
                () -> Assertions.assertEquals(repo.get(3), views.get(3)),
                () -> Assertions.assertEquals(repo.get(4), views.get(4)),
                () -> Assertions.assertEquals(repo.get(5), views.get(5)),
                () -> Assertions.assertEquals(repo.getSize(), 6),
                () -> Assertions.assertTrue(repo.getAllAtDepth(1).contains(view3) )
        );
    }

    @Test
    public void rotateTest(){
        repo.setActive(view2);
        repo.rotate(true);
        Assertions.assertAll(
                () -> Assertions.assertEquals(repo.getSize(), 6),
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(null);
                    actual.add(view4);
                    actual.add(view5);
                    actual.add(view6);
                    Assertions.assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> Assertions.assertEquals(repo.getAllAtDepth(2), List.of(view3, view2))
        );
        LayoutGenerator.generate(terminalDimensions);
        repo.setActive(view3);
        repo.rotate(true);
        Assertions.assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(view2);
                    actual.add(view4);
                    actual.add(view5);
                    actual.add(view6);
                    Assertions.assertEquals(repo.getAllAtDepth(1), actual);
                }
        );
        LayoutGenerator.generate(terminalDimensions);
        repo.setActive(view2);
        repo.rotate(false);
        Assertions.assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(null);
                    actual.add(view5);
                    actual.add(view6);
                    Assertions.assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> Assertions.assertEquals(repo.getAllAtDepth(2), List.of(view2, view4))
        );
        LayoutGenerator.generate(terminalDimensions);
        repo.setActive(view4);
        repo.rotate(true);
        Assertions.assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(null);
                    actual.add(view6);
                    Assertions.assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> Assertions.assertEquals(repo.getAllAtDepth(2), List.of(view2, view5, view4))
        );
        LayoutGenerator.generate(terminalDimensions);
        repo.setActive(view4);
        repo.rotate(false);
        Assertions.assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(null);
                    Assertions.assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> Assertions.assertEquals(repo.getAllAtDepth(2), List.of(view2, view5, view4, view6))
        );
        LayoutGenerator.generate(terminalDimensions);

        repo.setActive(view3);
        repo.rotate(false);
        Assertions.assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(view2);
                    actual.add(null);
                    Assertions.assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> Assertions.assertEquals(repo.getAllAtDepth(2), List.of(view5, view4, view6))
        );
        LayoutGenerator.generate(terminalDimensions);

        repo.setActive(view2);
        repo.rotate(true);
        Assertions.assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(view5);
                    actual.add(view2);
                    actual.add(null);
                    Assertions.assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> Assertions.assertEquals(repo.getAllAtDepth(2), List.of(view4, view6))
        );
        LayoutGenerator.generate(terminalDimensions);

        repo.setActive(view3);
        repo.rotate(false);
        Assertions.assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(null);
                    actual.add(view2);
                    actual.add(null);
                    Assertions.assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> Assertions.assertEquals(repo.getAllAtDepth(2), List.of(view3,view5,view4, view6))

        );
        LayoutGenerator.generate(terminalDimensions);
    }

}
