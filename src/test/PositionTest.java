import com.Textr.TerminalModel.Position;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void testConstructorAndGetters(){
        Position point = Position.builder().x(1).y(2).build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(point.getX(), 1),
                () -> Assertions.assertEquals(point.getY(), 2)
        );
    }

    @Test
    public void testConstructorInvalid(){
        Position.Builder invalidX = Position.builder().x(0).y(2);
        Position.Builder invalidY = Position.builder().x(1).y(0);
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidX::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidY::build)
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Position point1 = Position.builder().x(1).y(2).build();
        Position point2 = Position.builder().x(10).y(2).build();
        Position point3 = Position.builder().x(1).y(2).build();
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
        Position point = Position.builder().x(1).y(2).build();
        String expectedString = "BufferPoint[x = 1, y = 2]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(point.toString(), expectedString)
        );
    }
}
