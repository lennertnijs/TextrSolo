package ViewTest;
import com.Textr.Settings;
import com.Textr.Util.Dimension2D;
import com.Textr.Util.Point;
import com.Textr.View.LayoutGenerator;
import com.Textr.View.View;
import com.Textr.View.ViewTreeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static com.Textr.View.LayoutGenerator.setViewRepo;

class LayoutGeneratorTest {
    private ViewTreeRepo repo;

    private Dimension2D dimension2D;
    private View view1 ;

    private View view2 ;

    private View view3 ;

    private View view4 ;
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
        repo = new ViewTreeRepo();
        setViewRepo(repo);
        repo.addAll(views);
        Dimension2D dimension2D = Dimension2D.create(60, 62);
        LayoutGenerator.generate(dimension2D);
    }



    @Test
    public void test(){
        Dimension2D dimension2D = Dimension2D.create(60, 62);
        repo.setActive(view2);
        repo.rotate(true);
        LayoutGenerator.generate(dimension2D);
        repo.rotate(true);
        LayoutGenerator.generate(dimension2D);
        repo.rotate(true);
        LayoutGenerator.generate(dimension2D);
        Assertions.assertAll(
                () -> Assertions.assertEquals(repo.getAll().get(0), view1),
                () -> Assertions.assertEquals(repo.getAll().get(1), view3),
                () -> Assertions.assertEquals(repo.getAll().get(2), view4),
                () -> Assertions.assertEquals(repo.getAll().get(3), view2),
                () -> Assertions.assertEquals(repo.getAll().get(0).getDimensions(), Dimension2D.create(60, 31)),
                () -> Assertions.assertEquals(repo.getAll().get(0).getPosition(), Point.create(0,0)),
                () -> Assertions.assertEquals(repo.getAll().get(1).getDimensions(), Dimension2D.create(20, 31)),
                () -> Assertions.assertEquals(repo.getAll().get(1).getPosition(), Point.create(0, 31)),
                () -> Assertions.assertEquals(repo.getAll().get(2).getDimensions(), Dimension2D.create(20, 31)),
                () -> Assertions.assertEquals(repo.get(2).getPosition(), Point.create(20, 31)),
                () -> Assertions.assertEquals(repo.get(3).getDimensions(), Dimension2D.create(20, 31)),
                () -> Assertions.assertEquals(repo.get(3).getPosition(), Point.create(40, 31))
        );
    }

}