import com.Textr.TerminalModel.BufferPoint;
import com.Textr.TerminalModel.BufferView;
import com.Textr.TerminalModel.Dimension2D;
import com.Textr.TerminalModel.State;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BufferViewTest {

    @Test
    public void testConstructorAndGetters(){
        BufferPoint point = BufferPoint.builder().x(5).y(5).build();
        Dimension2D dimensions = Dimension2D.builder().width(15).height(15).build();
        BufferView view = BufferView.builder().fileId(0).point(point).dimensions(dimensions)
                .text("text").insertionIndex(4).build();
        Assertions.assertAll(
                () -> Assertions.assertEquals(view.getFileId(), 0),
                () -> Assertions.assertEquals(view.getPoint(), point),
                () -> Assertions.assertEquals(view.getDimensions(), dimensions),
                () -> Assertions.assertEquals(view.getText(), "text"),
                () -> Assertions.assertEquals(view.getInsertionIndex(), 4),
                () -> Assertions.assertEquals(view.getState(), State.CLEAN)
        );
    }

    @Test
    public void testInvalidConstructor(){
        BufferPoint point = BufferPoint.builder().x(5).y(5).build();
        Dimension2D dimensions = Dimension2D.builder().width(15).height(15).build();
        BufferView.Builder invalidId = BufferView.builder().fileId(-1).point(point)
                .dimensions(dimensions).text("text").insertionIndex(1);
        BufferView.Builder invalidPoint = BufferView.builder().fileId(0).point(null)
                .dimensions(dimensions).text("text").insertionIndex(1);
        BufferView.Builder invalidDimension = BufferView.builder().fileId(0).point(point)
                .dimensions(null).text("text").insertionIndex(1);
        BufferView.Builder invalidText = BufferView.builder().fileId(0).point(point)
                .dimensions(dimensions).text(null).insertionIndex(1);
        BufferView.Builder invalidInsertionIndex = BufferView.builder().fileId(0).point(point)
                .dimensions(dimensions).text("text").insertionIndex(5);
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidId::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidPoint::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidDimension::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidText::build),
                () -> Assertions.assertThrows(IllegalArgumentException.class, invalidInsertionIndex::build)
        );
    }

    @Test
    public void testEqualsAndHashCode(){
        BufferPoint point = BufferPoint.builder().x(5).y(5).build();
        Dimension2D dimensions = Dimension2D.builder().width(15).height(15).build();
        BufferView view1 = BufferView.builder().fileId(1).point(point)
                .dimensions(dimensions).text("text").insertionIndex(1).build();
        BufferView view2 = BufferView.builder().fileId(1).point(point)
                .dimensions(dimensions).text("otherText").insertionIndex(1).build();
        BufferView view3 = BufferView.builder().fileId(1).point(point)
                .dimensions(dimensions).text("text").insertionIndex(1).build();
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
        BufferPoint point = BufferPoint.builder().x(5).y(5).build();
        Dimension2D dimensions = Dimension2D.builder().width(15).height(15).build();
        BufferView view = BufferView.builder().fileId(1).point(point)
                .dimensions(dimensions).text("text").insertionIndex(1).build();
        String expected = "BufferView[fileId = 1, point = BufferPoint[x = 5, y = 5], " +
                "dimensions = com.Textr.TerminalModel.Dimension2D@17c386de, " +
                "text = text, insertionIndex = 1, state = CLEAN]";
        Assertions.assertAll(
                () -> Assertions.assertEquals(view.toString(), expected)
        );
    }
}
