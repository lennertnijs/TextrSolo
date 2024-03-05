package TreeTest;

import com.Textr.Tree.Node;
import com.Textr.Tree.Tree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TreeTest {

    private Node<Integer> node1;
    private Node<Integer> node2;
    private Node<Integer> node3;
    private Node<Integer> node4;
    private Node<Integer> node5;
    private Node<Integer> node6;
    private Tree<Integer> tree;


    @BeforeEach
    public void initialise(){
        node1 = new Node<>(1);
        node2 = new Node<>(2);
        node3 = new Node<>(3);
        node4 = new Node<>(4);
        node5 = new Node<>(5);
        node6 = new Node<>(6);
        tree = new Tree<>();
        tree.addChildToRoot(node1);
        tree.addChildToRoot(node2);
        tree.addChildToNode(node3, node2);
        tree.addChildToNode(node4, node2);
        tree.addChildToNode(node5, node3);
    }

    @Test
    public void testTreeCreation(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(tree.getRoot().getChildren(), new ArrayList<>(List.of(node1, node2))),
                () -> Assertions.assertEquals(node1.getParent(), tree.getRoot()),
                () -> Assertions.assertEquals(node2.getParent(), tree.getRoot()),

                () -> Assertions.assertEquals(node2.getChildren(), new ArrayList<>(List.of(node3, node4))),
                () -> Assertions.assertEquals(node3.getParent(), node2),
                () -> Assertions.assertEquals(node4.getParent(), node2),

                () -> Assertions.assertEquals(node3.getChildren(), new ArrayList<>(List.of(node5))),
                () -> Assertions.assertEquals(node5.getParent(), node3)
        );
    }

    @Test
    public void testTreeSize(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(tree.getSize(), 5)
        );
    }

    @Test
    public void testContainsNode(){
        Assertions.assertAll(
                () -> Assertions.assertTrue(tree.contains(node1)),
                () -> Assertions.assertTrue(tree.contains(node2)),
                () -> Assertions.assertTrue(tree.contains(node3)),
                () -> Assertions.assertTrue(tree.contains(node4)),
                () -> Assertions.assertTrue(tree.contains(node5)),
                () -> Assertions.assertFalse(tree.contains(node6))
        );
    }

    @Test
    public void testContainsValue(){
        Assertions.assertAll(
                () -> Assertions.assertTrue(tree.containsValue(1)),
                () -> Assertions.assertTrue(tree.containsValue(2)),
                () -> Assertions.assertTrue(tree.containsValue(3)),
                () -> Assertions.assertTrue(tree.containsValue(4)),
                () -> Assertions.assertTrue(tree.containsValue(5)),
                () -> Assertions.assertFalse(tree.containsValue(6))
        );
    }

    @Test
    public void testDepth(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(tree.getDepth(node1), 1),
                () -> Assertions.assertEquals(tree.getDepth(node2), 1),
                () -> Assertions.assertEquals(tree.getDepth(node3), 2),
                () -> Assertions.assertEquals(tree.getDepth(node4), 2),
                () -> Assertions.assertEquals(tree.getDepth(node5), 3)
        );
    }

    @Test
    public void testDepthByValue(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(tree.getDepthOfValue(1), 1),
                () -> Assertions.assertEquals(tree.getDepthOfValue(2), 1),
                () -> Assertions.assertEquals(tree.getDepthOfValue(3), 2),
                () -> Assertions.assertEquals(tree.getDepthOfValue(4), 2),
                () -> Assertions.assertEquals(tree.getDepthOfValue(5), 3)
        );
    }

    @Test
    public void getNodeByValue(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(tree.getNodeByValue(1), node1),
                () -> Assertions.assertEquals(tree.getNodeByValue(2), node2),
                () -> Assertions.assertEquals(tree.getNodeByValue(3), node3),
                () -> Assertions.assertEquals(tree.getNodeByValue(5), node5),
                () -> Assertions.assertEquals(tree.getNodeByValue(4), node4),
                () -> Assertions.assertThrows(NoSuchElementException.class, () -> tree.getNodeByValue(6))
        );
    }

    @Test
    public void testRemove(){
        Assertions.assertAll(
                () -> tree.remove(node4),
                () -> Assertions.assertEquals(tree.getSize(), 4),
                () -> Assertions.assertFalse(tree.contains(node4)),

                () -> tree.remove(node5),
                () -> Assertions.assertEquals(tree.getSize(), 3),
                () -> Assertions.assertFalse(tree.contains(node5)),

                () -> tree.remove(node5),
                () -> Assertions.assertEquals(tree.getSize(), 3),
                () -> Assertions.assertFalse(tree.contains(node5))
        );
    }

    @Test
    public void testRemoveFromValue(){
        Assertions.assertAll(
                () -> tree.removeFromValue(1),
                () -> Assertions.assertEquals(tree.getSize(), 4),
                () -> Assertions.assertFalse(tree.containsValue(1))
        );
    }
}
