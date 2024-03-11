package TreeTest;

import com.Textr.Tree.Node;
import com.Textr.Tree.Tree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TreeWithEmptyNodesTest {

    private Node<Integer> node1;
    private Node<Integer> node2;
    private Node<Integer> node3;
    private Node<Integer> node4;
    private Node<Integer> node5;
    private Node<Integer> node6;
    private Node<Integer> node7;
    private Node<Integer> node8;
    private Node<Integer> node9;
    private Node<Integer> node10;
    private Tree<Integer> tree;


    @BeforeEach
    public void initialise(){
        node1 = new Node<>(1);
        node2 = new Node<>(2);
        node3 = new Node<>(3);
        node4 = new Node<>(4);
        node5 = new Node<>(null);
        node6 = new Node<>(null);
        node7 = new Node<>(null);
        node8 = new Node<>(null);
        node9 = new Node<>(9);
        node10 = new Node<>(10);
        tree = new Tree<>();
        tree.addChildToRoot(node1);
        tree.addChildToRoot(node5);
        tree.addChildToRoot(node6);
        tree.addChildToNode(node2, node5);
        tree.addChildToNode(node7, node5);
        tree.addChildToNode(node3, node7);
        tree.addChildToNode(node8, node6);
        tree.addChildToNode(node4, node8);
    }

    @Test
    public void testCreation(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(node1.getParent(), tree.getRoot()),
                () -> Assertions.assertEquals(node5.getParent(), tree.getRoot()),
                () -> Assertions.assertEquals(node6.getParent(), tree.getRoot()),
                () -> Assertions.assertEquals(tree.getRoot().getChildren(), new ArrayList<>(List.of(node1, node5, node6))),

                () -> Assertions.assertEquals(node1.getChildren(), new ArrayList<>()),

                () -> Assertions.assertEquals(node5.getChildren(), new ArrayList<>(List.of(node2, node7))),
                () -> Assertions.assertEquals(node2.getParent(), node5),
                () -> Assertions.assertEquals(node7.getParent(), node5),

                () -> Assertions.assertEquals(node8.getParent(), node6),
                () -> Assertions.assertEquals(node6.getChildren(), new ArrayList<>(List.of(node8))),

                () -> Assertions.assertEquals(node3.getParent(), node7),
                () -> Assertions.assertEquals(node7.getChildren(), new ArrayList<>(List.of(node3))),

                () -> Assertions.assertEquals(node4.getParent(), node8),
                () -> Assertions.assertEquals(node8.getChildren(), new ArrayList<>(List.of(node4))),

                () -> Assertions.assertEquals(node3.getChildren(), new ArrayList<>()),

                () -> Assertions.assertEquals(node4.getChildren(), new ArrayList<>()),

                () -> Assertions.assertTrue(tree.isLastValue(4)),
                () -> Assertions.assertFalse(tree.isLastValue(5)),
                () -> Assertions.assertFalse(tree.isLastValue(null))
        );
    }

    @Test
    public void testTreeSize(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(tree.getSize(), 8),
                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 4)
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
                () -> Assertions.assertTrue(tree.contains(node6)),
                () -> Assertions.assertTrue(tree.contains(node7)),
                () -> Assertions.assertTrue(tree.contains(node8)),
                () -> Assertions.assertFalse(tree.contains(node9)),
                () -> Assertions.assertFalse(tree.contains(node10))
        );
    }

    @Test
    public void testContainsValue(){
        Assertions.assertAll(
                () -> Assertions.assertTrue(tree.contains(1)),
                () -> Assertions.assertTrue(tree.contains(2)),
                () -> Assertions.assertTrue(tree.contains(3)),
                () -> Assertions.assertTrue(tree.contains(4)),
                () -> Assertions.assertFalse(tree.contains(5)),
                () -> Assertions.assertFalse(tree.contains(6)),
                () -> Assertions.assertFalse(tree.contains(7)),
                () -> Assertions.assertFalse(tree.contains(8)),
                () -> Assertions.assertFalse(tree.contains(9)),
                () -> Assertions.assertFalse(tree.contains(10))
        );
    }

    @Test
    public void testDepth(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(tree.getDepth(node1), 1),
                () -> Assertions.assertEquals(tree.getDepth(node2), 2),
                () -> Assertions.assertEquals(tree.getDepth(node3), 3),
                () -> Assertions.assertEquals(tree.getDepth(node4), 3),
                () -> Assertions.assertEquals(tree.getDepth(node5), 1),
                () -> Assertions.assertEquals(tree.getDepth(node6), 1),
                () -> Assertions.assertEquals(tree.getDepth(node7), 2),
                () -> Assertions.assertEquals(tree.getDepth(node8), 2),
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> tree.getDepth(node9))
        );
    }

    @Test
    public void testDepthFromValue(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(tree.getDepth(1), 1),
                () -> Assertions.assertEquals(tree.getDepth(2), 2),
                () -> Assertions.assertEquals(tree.getDepth(3), 3),
                () -> Assertions.assertEquals(tree.getDepth(4), 3),
                () -> Assertions.assertThrows(NoSuchElementException.class, () -> tree.getDepth(5))
        );
    }

    @Test
    public void testNodesAtDepth(){
        List<Integer> depth1 = new ArrayList<>();
        depth1.add(1);
        depth1.add(null);
        depth1.add(null);

        List<Integer> depth2 = new ArrayList<>();
        depth2.add(2);
        depth2.add(null);
        depth2.add(null);
        Assertions.assertAll(
                () -> Assertions.assertEquals(tree.getAllAtDepth(1), depth1),
                () -> Assertions.assertEquals(tree.getAllAtDepth(2), depth2),
                () -> Assertions.assertEquals(tree.getAllAtDepth(3), new ArrayList<>(List.of(3, 4)))
        );
    }

    @Test
    public void getNodeByValue(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(tree.getNode(1), node1),
                () -> Assertions.assertEquals(tree.getNode(2), node2),
                () -> Assertions.assertEquals(tree.getNode(3), node3),
                () -> Assertions.assertEquals(tree.getNode(4), node4),
                () -> Assertions.assertThrows(NoSuchElementException.class, () -> tree.getNode(6))
        );
    }

    @Test
    public void testRemove(){
        Assertions.assertAll(
                () -> tree.remove(node4),
                () -> Assertions.assertEquals(tree.getSize(), 7),
                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 3),
                () -> Assertions.assertFalse(tree.contains(node4)),

                () -> tree.remove(node5),
                () -> Assertions.assertEquals(tree.getSize(), 3),
                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 1),
                () -> Assertions.assertFalse(tree.contains(node5)),

                () -> tree.remove(node6),
                () -> Assertions.assertEquals(tree.getSize(), 1),
                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 1),

                () -> tree.remove(node1),
                () -> Assertions.assertEquals(tree.getSize(), 0)
        );
    }

    @Test
    public void testRemoveByValue(){
        Assertions.assertAll(
                () -> tree.remove(4),
                () -> Assertions.assertEquals(tree.getSize(), 7),
                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 3),
                () -> Assertions.assertFalse(tree.contains(node4)),

                () -> tree.remove(3),
                () -> Assertions.assertEquals(tree.getSize(), 6),
                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 2),
                () -> Assertions.assertTrue(tree.contains(node5)),

                () -> tree.remove(2),
                () -> Assertions.assertEquals(tree.getSize(), 5),
                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 1),

                () -> tree.remove(1),
                () -> Assertions.assertEquals(tree.getSize(), 4),
                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 0)
        );
    }

    @Test
    public void testGetValues(){
        Assertions.assertAll(
                () -> Assertions.assertEquals(tree.getAllValues(), new ArrayList<>(List.of(1,2,3,4))),
                () -> Assertions.assertEquals(tree.getNextValue(1), 2),
                () -> Assertions.assertEquals(tree.getPreviousValue(2), 1)
        );
    }
}
