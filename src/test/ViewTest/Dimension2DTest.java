package ViewTest;

import com.Textr.View.Dimension2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Dimension2DTest {

    @Test
    public void testConstructorAndGetters(){
        Dimension2D dimensions = Dimension2D.create(15, 10);
        Assertions.assertAll(
                () -> Assertions.assertEquals(dimensions.getWidth(), 15),
                () -> Assertions.assertEquals(dimensions.getHeight(), 10)
        );
    }

    @Test
    public void testConstructorInvalid(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Dimension2D.create(0, 10)),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> Dimension2D.create(10, 0))
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Dimension2D dimensions1 = Dimension2D.create(15, 10);
        Dimension2D dimensions2 = Dimension2D.create(10, 15);
        Dimension2D dimensions3 = Dimension2D.create(15, 10);
        Assertions.assertAll(
                () -> Assertions.assertEquals(dimensions1, dimensions3),
                () -> Assertions.assertEquals(dimensions1, dimensions1),
                () -> Assertions.assertNotEquals(dimensions1, dimensions2),
                () -> Assertions.assertNotEquals(dimensions1, new Object()),
                () -> Assertions.assertEquals(dimensions1.hashCode(), dimensions3.hashCode()),
                () -> Assertions.assertEquals(dimensions1.hashCode(), dimensions3.hashCode()),
                () -> Assertions.assertNotEquals(dimensions1.hashCode(), dimensions2.hashCode())
        );
    }

    @Test
    public void testToString(){
        Dimension2D dimensions = Dimension2D.create(15, 10);
        String expected = "Dimension2D[width = 15, height = 10]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(dimensions.toString(), expected)
        );
    }
}
