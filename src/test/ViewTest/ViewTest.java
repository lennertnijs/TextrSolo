package ViewTest;

import com.Textr.FileBuffer.Point;
import com.Textr.View.Point1B;
import com.Textr.View.View;
import com.Textr.View.Dimension2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ViewTest {

    @Test
    public void testConstructorAndGetters(){
        Point1B point1B = Point1B.create(5,5);
        Dimension2D dimensions = Dimension2D.create(15, 15);
        Point anchor = Point.create(1,1);
        View view = View.builder().fileBufferId(0).position(point1B).dimensions(dimensions).anchorPoint(anchor).build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(view.getFileBufferId(), 0),
                () -> Assertions.assertEquals(view.getPosition(), point1B),
                () -> Assertions.assertEquals(view.getDimensions(), dimensions)
        );
    }

    @Test
    public void testInvalidConstructor(){
        Point1B point1B = Point1B.create(5,5);
        Point anchor = Point.create(1,1);
        Dimension2D dimensions = Dimension2D.create(15, 15);
        View.Builder invalidId = View.builder().fileBufferId(-1).position(point1B)
                .dimensions(dimensions).anchorPoint(anchor);
        View.Builder invalidPoint = View.builder().fileBufferId(0).position(null)
                .dimensions(dimensions).anchorPoint(anchor);
        View.Builder invalidDimension = View.builder().fileBufferId(0).position(point1B)
                .dimensions(null).anchorPoint(anchor);
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidId::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidPoint::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidDimension::build)
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Point1B point1B = Point1B.create(5,5);
        Point anchor = Point.create(1,1);
        Dimension2D dimensions = Dimension2D.create(15, 15);
        View view1 = View.builder().fileBufferId(1).position(point1B)
                .dimensions(dimensions).anchorPoint(anchor).build();
        View view2 = View.builder().fileBufferId(2).position(point1B)
                .dimensions(dimensions).anchorPoint(anchor).build();
        View view3 = View.builder().fileBufferId(1).position(point1B)
                .dimensions(dimensions).anchorPoint(anchor).build();
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
        Point1B point1B = Point1B.create(5,5);
        Point anchor = Point.create(1,1);
        Dimension2D dimensions = Dimension2D.create(15, 15);
        View view = View.builder().fileBufferId(1).position(point1B)
                .dimensions(dimensions).anchorPoint(anchor).build();
        String expected = "BufferView[fileId = 1, point = Point[x = 5, y = 5], dimensions = Dimension2D[width = 15, height = 15]]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(view.toString(), expected)
        );
    }
}
