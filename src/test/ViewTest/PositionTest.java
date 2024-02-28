package ViewTest;

import com.Textr.View.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void testConstructorAndGetters(){
        Position point = Position.create(1,2);
        Assertions.assertAll(
                () -> Assertions.assertEquals(point.getX(), 1),
                () -> Assertions.assertEquals(point.getY(), 2)
        );
    }

    @Test
    public void testConstructorInvalid(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Position.create(-1, 1)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Position.create(1, -1))
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Position point1 = Position.create(1, 2);
        Position point2 = Position.create(10, 2);
        Position point3 = Position.create(1, 2);
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
        Position point = Position.create(1, 2);
        String expectedString = "Position[x = 1, y = 2]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(point.toString(), expectedString)
        );
    }
}
