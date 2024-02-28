package ViewTest;

import com.Textr.View.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PointTest {

    @Test
    public void testConstructorAndGetters(){
        Point point = Point.create(1,2);
        Assertions.assertAll(
                () -> Assertions.assertEquals(point.getX(), 1),
                () -> Assertions.assertEquals(point.getY(), 2)
        );
    }

    @Test
    public void testConstructorInvalid(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Point.create(-1, 1)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Point.create(1, -1))
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Point point1 = Point.create(1, 2);
        Point point2 = Point.create(10, 2);
        Point point3 = Point.create(1, 2);
        Assertions.assertAll(
                () -> Assertions.assertEquals(point1, point1),
                () -> Assertions.assertEquals(point1, point3),
                () -> Assertions.assertNotEquals(point1, point2),
                () -> Assertions.assertNotEquals(point1, new Object()),
                () -> Assertions.assertEquals(point1.hashCode(), point1.hashCode()),
                () -> Assertions.assertEquals(point1.hashCode(), point3.hashCode()),
                () -> Assertions.assertNotEquals(point1.hashCode(), point2.hashCode())
        );
    }

    @Test
    public void testToString(){
        Point point = Point.create(1, 2);
        String expectedString = "Position[x = 1, y = 2]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(point.toString(), expectedString)
        );
    }
}
