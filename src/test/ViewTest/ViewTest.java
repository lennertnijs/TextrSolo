package ViewTest;

import com.Textr.Util.Point;
import com.Textr.View.View;
import com.Textr.View.Dimension2D;
import com.Textr.View.ViewIdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ViewTest {

    @BeforeEach
    public void initialise(){
        ViewIdGenerator.resetGenerator();
    }

    @Test
    public void testConstructorAndGetters(){
        Point point = Point.create(5,5);
        Dimension2D dimensions = Dimension2D.create(15, 15);
        Point anchor = Point.create(1,1);
        View view = View.builder().fileBufferId(0).position(point).dimensions(dimensions).anchor(anchor).build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(view.getId(), 0),
                () -> Assertions.assertEquals(view.getFileBufferId(), 0),
                () -> Assertions.assertEquals(view.getPosition(), point),
                () -> Assertions.assertEquals(view.getDimensions(), dimensions),
                () -> Assertions.assertEquals(view.getAnchor(), anchor)
        );
    }

    @Test
    public void testInvalidConstructor(){
        Point point = Point.create(5,5);
        Point anchor = Point.create(1,1);
        Dimension2D dimensions = Dimension2D.create(15, 15);
        View.Builder invalidId = View.builder().fileBufferId(-1).position(point)
                .dimensions(dimensions).anchor(anchor);
        View.Builder invalidPoint = View.builder().fileBufferId(0).position(null)
                .dimensions(dimensions).anchor(anchor);
        View.Builder invalidDimension = View.builder().fileBufferId(0).position(point)
                .dimensions(null).anchor(anchor);
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidId::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidPoint::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidDimension::build)
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Point point = Point.create(5,5);
        Point anchor = Point.create(1,1);
        Dimension2D dimensions = Dimension2D.create(15, 15);
        View view1 = View.builder().fileBufferId(1).position(point)
                .dimensions(dimensions).anchor(anchor).build();
        View view2 = View.builder().fileBufferId(2).position(point)
                .dimensions(dimensions).anchor(anchor).build();
        View view3 = View.builder().fileBufferId(1).position(point)
                .dimensions(dimensions).anchor(anchor).build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(view1, view1),
                () -> Assertions.assertEquals(view1, view3),
                () -> Assertions.assertNotEquals(view1, view2),
                () -> Assertions.assertNotEquals(view1, new Object()),
                () -> Assertions.assertEquals(view1.hashCode(), view1.hashCode()),
                () -> Assertions.assertEquals(view1.hashCode(), view3.hashCode()),
                () -> Assertions.assertNotEquals(view2.hashCode(), view1.hashCode())
        );
    }

    @Test
    public void testToString(){
        Point point = Point.create(5,5);
        Point anchor = Point.create(1,1);
        Dimension2D dimensions = Dimension2D.create(15, 15);
        View view = View.builder().fileBufferId(1).position(point)
                .dimensions(dimensions).anchor(anchor).build();
        String expected = "View[fileBufferId = 1, position = Point[x = 5, y = 5], dimensions = Dimension2D[width = 15, height = 15], anchor = Point[x = 1, y = 1]]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(view.toString(), expected)
        );
    }
}
