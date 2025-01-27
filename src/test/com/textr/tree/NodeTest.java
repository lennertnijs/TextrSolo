package com.textr.tree;

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
    private Node<Integer> node6;
    private Node<Integer> node7;

    private Node<Integer> node8;

    @BeforeEach
    public void initialise(){
        node1 = new Node<>(1);
        node2 = new Node<>(2);
        node3 = new Node<>(3);
        node4 = new Node<>(4);
        node5 = new Node<>(5);
        node6 = new Node<>(null);
        node7 = new Node<>(7);
        node8 = new Node<>(7);
    }



    @Test
    public void testNode(){
        node1.addChildren(List.of(node2, node3));
        node3.addChild(node4);
        node4.addChildAt(node5, 0);
        Assertions.assertAll(
                () -> Assertions.assertTrue(node1.hasValue()),
                () -> Assertions.assertEquals(node1.getValue(), 1),
                () -> Assertions.assertFalse(node1.hasParent()),
                () -> Assertions.assertEquals(node1.getChildren(), new ArrayList<>(List.of(node2, node3))),

                () -> Assertions.assertEquals(node2.getValue(), 2),
                () -> Assertions.assertEquals(node3.getValue(), 3),
                () -> Assertions.assertEquals(node2.getParent(), node1),
                () -> Assertions.assertTrue(node3.hasChildren()),
                () -> Assertions.assertEquals(node2.getChildren(), new ArrayList<>()),
                () -> Assertions.assertEquals(node3.getChildren(), new ArrayList<>(List.of(node4))),

                () -> Assertions.assertEquals(node4.getValue(), 4),
                () -> Assertions.assertEquals(node4.getChildren(), new ArrayList<>(List.of(node5))),
                () -> Assertions.assertTrue(node4.hasParent()),

                () -> Assertions.assertEquals(node5.getValue(), 5),
                () -> Assertions.assertEquals(node5.getChildren(), new ArrayList<>()),

                () -> Assertions.assertFalse(node6.hasValue()),
                () -> Assertions.assertFalse(node6.hasChildren()),
                () -> Assertions.assertFalse(node6.hasSingleChild()),
                () -> Assertions.assertFalse(node1.hasSingleChild())

        );
        node6.addChild(node7);
        Assertions.assertAll(
                () -> Assertions.assertTrue(node6.hasSingleChild())
        );
        node6.removeChild(node7);
        Assertions.assertAll(
                () -> Assertions.assertFalse(node6.hasSingleChild())
        );

    }

    @Test
    public void testSibling(){
        node1.addChildren(List.of(node2, node3));
        node3.addChild(node4);
        node4.addChild(node5);
        Assertions.assertAll(
                () -> Assertions.assertTrue(node2.isSiblingWith(node3)),
                () -> Assertions.assertFalse(node3.isSiblingWith(node4))
        );
        node1.replaceChild(node3, node6);
        Assertions.assertAll(
                () -> Assertions.assertTrue(node6.isSiblingWith(node3))
        );
    }
    @Test
    public void testEqualsAndHashCode(){
        node7.addChild(node6);
        node8.addChild(node6);
        Assertions.assertAll(
                () -> Assertions.assertNotEquals(node1, node2),
                () -> Assertions.assertEquals(node7, node8),
                () -> Assertions.assertNotEquals(node1, null),
                () -> Assertions.assertNotEquals(node1, new Object()),
                () -> Assertions.assertNotEquals(node1.hashCode(), node2.hashCode()),
                () -> Assertions.assertEquals(node7.hashCode(), node8.hashCode())
        );
    }
}
