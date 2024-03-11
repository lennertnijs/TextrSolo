package ViewTest;
import com.Textr.View.ViewTreeRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

class LayoutGeneratorTest {
    private ViewTreeRepo repo;


    @BeforeEach
    public void initialise(){
        repo = new ViewTreeRepo();
    }
    @BeforeEach
    void setUp() {
    }

    @Test
    void setViewRepo() {
    }

    @Test
    void generate() {
    }



//    @Test
//    public void test(){
//        Dimension2D dimension2D = Dimension2D.create(60, 62);
//        repo.rotateClockWise(1, 2);
//        repo.rotateClockWise(2, 3);
//        repo.rotateClockWise(2, 3);
//        LayoutGenerator.generateViews(repo, dimension2D);
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(repo.getAll().get(0).getDimensions(), Dimension2D.create(60, 31)),
//                () -> Assertions.assertEquals(repo.getAll().get(0).getPosition(), Point.create(0,0)),
//                () -> Assertions.assertEquals(repo.getAll().get(1).getDimensions(), Dimension2D.create(60, 16)),
//                () -> Assertions.assertEquals(repo.getAll().get(1).getPosition(), Point.create(0, 31)),
//                () -> Assertions.assertEquals(repo.getAll().get(2).getDimensions(), Dimension2D.create(60, 8)),
//                () -> Assertions.assertEquals(repo.get(2).getPosition(), Point.create(0, 47)),
//                () -> Assertions.assertEquals(repo.get(3).getDimensions(), Dimension2D.create(60, 7)),
//                () -> Assertions.assertEquals(repo.get(3).getPosition(), Point.create(0, 55))
//        );
//    }
//
//    @Test
//    public void test2(){
//        Dimension2D dimension2D = Dimension2D.create(60, 63);
//        repo.rotateClockWise(0, 1);
//        LayoutGenerator.generateViews(repo, dimension2D);
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(repo.getTree().getDepthOfValue(0), 2),
//                () -> Assertions.assertEquals(repo.getTree().getDepthOfValue(1), 2),
//                () -> Assertions.assertEquals(repo.getTree().getDepthOfValue(2), 1),
//                () -> Assertions.assertEquals(repo.getTree().getDepthOfValue(3), 1),
//                () -> Assertions.assertEquals(repo.get(0).getDimensions(), Dimension2D.create(60, 11)),
//                () -> Assertions.assertEquals(repo.get(0).getPosition(), Point.create(0,0)),
//                () -> Assertions.assertEquals(repo.get(1).getDimensions(), Dimension2D.create(60, 10)),
//                () -> Assertions.assertEquals(repo.get(1).getPosition(), Point.create(0,11)),
//                () -> Assertions.assertEquals(repo.get(2).getDimensions(), Dimension2D.create(60, 21)),
//                () -> Assertions.assertEquals(repo.get(2).getPosition(), Point.create(0,21)),
//                () -> Assertions.assertEquals(repo.get(3).getDimensions(), Dimension2D.create(60, 21)),
//                () -> Assertions.assertEquals(repo.get(3).getPosition(), Point.create(0,42))
//        );
//    }

}