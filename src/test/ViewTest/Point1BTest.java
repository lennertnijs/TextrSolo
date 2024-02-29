package ViewTest;

import com.Textr.View.Point1B;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Point1BTest {

    @Test
    public void testConstructorAndGetters(){
        Point1B point1B = Point1B.create(1,2);
        Assertions.assertAll(
                () -> Assertions.assertEquals(point1B.getX(), 1),
                () -> Assertions.assertEquals(point1B.getY(), 2)
        );
    }

    @Test
    public void testConstructorInvalid(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Point1B.create(-1, 1)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Point1B.create(1, -1))
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Point1B point1B1 = Point1B.create(1, 2);
        Point1B point1B2 = Point1B.create(10, 2);
        Point1B point1B3 = Point1B.create(1, 2);
        Assertions.assertAll(
                () -> Assertions.assertEquals(point1B1, point1B1),
                () -> Assertions.assertEquals(point1B1, point1B3),
                () -> Assertions.assertNotEquals(point1B1, point1B2),
                () -> Assertions.assertNotEquals(point1B1, new Object()),
                () -> Assertions.assertEquals(point1B1.hashCode(), point1B1.hashCode()),
                () -> Assertions.assertEquals(point1B1.hashCode(), point1B3.hashCode()),
                () -> Assertions.assertNotEquals(point1B1.hashCode(), point1B2.hashCode())
        );
    }

    @Test
    public void testToString(){
        Point1B point1B = Point1B.create(1, 2);
        String expectedString = "Point[x = 1, y = 2]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(point1B.toString(), expectedString)
        );
    }
}
