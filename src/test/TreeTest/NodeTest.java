package TreeTest;

import com.Textr.Tree.Node;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class NodeTest {
    private Node<Integer> node1;
    private Node<Integer> node2;
    private Node<Integer> node3;
    private Node<Integer> node4;
    private Node<Integer> node5;
    @BeforeEach
    public void initialise(){
        node1 = new Node<>(1);
        node2 = new Node<>(2);
        node3 = new Node<>(3);
        node4 = new Node<>(4);
        node5 = new Node<>(5);
    }

    @Test
    public void testNode(){
        node1.addChildren(List.of(node2, node3));
        node2.setParent(node1);
        node3.addChild(node4);
        node4.setParent(node3);
        node4.addChild(node5);
        Assertions.assertAll(
                () -> Assertions.assertEquals(node1.getValue().get(), 1),
                () -> Assertions.assertFalse(node1.hasParent()),
                () -> Assertions.assertEquals(node1.getChildren(), new ArrayList<>(List.of(node2, node3))),

                () -> Assertions.assertEquals(node2.getValue().get(), 2),
                () -> Assertions.assertEquals(node3.getValue().get(), 3),
                () -> Assertions.assertEquals(node2.getParent(), node1),
                () -> Assertions.assertFalse(node3.hasChildren()),
                () -> Assertions.assertEquals(node2.getChildren(), new ArrayList<>()),
                () -> Assertions.assertEquals(node3.getChildren(), new ArrayList<>(List.of(node4))),

                () -> Assertions.assertEquals(node4.getValue().get(), 4),
                () -> Assertions.assertEquals(node4.getChildren(), new ArrayList<>(List.of(node5))),
                () -> Assertions.assertTrue(node4.hasParent()),

                () -> Assertions.assertEquals(node5.getValue().get(), 5),
                () -> Assertions.assertEquals(node5.getChildren(), new ArrayList<>()),
                () -> Assertions.assertFalse(node5.hasParent())
        );
    }

//    @Test
//    public void testSibling(){
//        node1.addChildren(List.of(node2, node3));
//        node2.setParent(node1);
//        node3.addChild(node4);
//        node3.setParent(node1);
//        node4.setParent(node3);
//        node4.addChild(node5);
//        Assertions.assertAll(
//                () -> Assertions.assertTrue(node2.isSiblingWith(node3)),
//                () -> Assertions.assertFalse(node3.isSiblingWith(node4))
//        );
//    }
}
