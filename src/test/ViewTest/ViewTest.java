package ViewTest;

import com.Textr.View.Position;
import com.Textr.View.View;
import com.Textr.View.Dimension2D;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ViewTest {

    @Test
    public void testConstructorAndGetters(){
        Position point = Position.create(5,5);
        Dimension2D dimensions = Dimension2D.builder().width(15).height(15).build();
        View view = View.builder().fileBufferId(0).point(point).dimensions(dimensions).build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(view.getFileBufferId(), 0),
                () -> Assertions.assertEquals(view.getPosition(), point),
                () -> Assertions.assertEquals(view.getDimensions(), dimensions)
        );
    }

    @Test
    public void testInvalidConstructor(){
        Position point = Position.create(5,5);
        Dimension2D dimensions = Dimension2D.builder().width(15).height(15).build();
        View.Builder invalidId = View.builder().fileBufferId(-1).point(point)
                .dimensions(dimensions);
        View.Builder invalidPoint = View.builder().fileBufferId(0).point(null)
                .dimensions(dimensions);
        View.Builder invalidDimension = View.builder().fileBufferId(0).point(point)
                .dimensions(null);
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidId::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidPoint::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidDimension::build)
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        Position point = Position.create(5,5);
        Dimension2D dimensions = Dimension2D.builder().width(15).height(15).build();
        View view1 = View.builder().fileBufferId(1).point(point)
                .dimensions(dimensions).build();
        View view2 = View.builder().fileBufferId(2).point(point)
                .dimensions(dimensions).build();
        View view3 = View.builder().fileBufferId(1).point(point)
                .dimensions(dimensions).build();
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
        Position point = Position.create(5,5);
        Dimension2D dimensions = Dimension2D.builder().width(15).height(15).build();
        View view = View.builder().fileBufferId(1).point(point)
                .dimensions(dimensions).build();
        String expected = "BufferView[fileId = 1, point = Position[x = 5, y = 5], dimensions = Dimension2D[width = 15, height = 15]]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(view.toString(), expected)
        );
    }
}
