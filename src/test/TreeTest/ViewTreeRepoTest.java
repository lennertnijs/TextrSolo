package TreeTest;

import com.Textr.Tree.Node;
import com.Textr.Tree.Tree;
import com.Textr.Tree.ViewTreeRepo;
import com.Textr.Util.Point;
import com.Textr.View.Dimension2D;
import com.Textr.View.View;
import com.Textr.View.ViewCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ViewTreeRepoTest {

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
    private ViewTreeRepo repo;
    private List<View> views;


    @BeforeEach
    public void initialise(){
//        views = generateSomeViews();
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
        repo = new ViewTreeRepo();
    }

//    @Test
//    public void testAdd(){
//        repo.addAll(views);
//        Tree<Integer> tree = repo.getTree();
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(repo.get(0), views.get(0)),
//                () -> Assertions.assertEquals(repo.get(1), views.get(1)),
//                () -> Assertions.assertEquals(repo.get(2), views.get(2)),
//                () -> Assertions.assertEquals(repo.get(3), views.get(3)),
//                () -> Assertions.assertEquals(repo.get(4), views.get(4)),
//
//                () -> Assertions.assertTrue(tree.containsValue(0)),
//                () -> Assertions.assertTrue(tree.containsValue(1)),
//                () -> Assertions.assertTrue(tree.containsValue(2)),
//                () -> Assertions.assertTrue(tree.containsValue(3)),
//                () -> Assertions.assertTrue(tree.containsValue(4)),
//
//                () -> Assertions.assertEquals(tree.getSize(), 5),
//                () -> Assertions.assertEquals(tree.getDepthOfValue(4), 1)
//        );
//    }

//    @Test
//    public void rotateTest(){
//        repo.addAll(views);
//        repo.rotateClockWise(1, 2);
//        Tree<View> tree = repo.getTree();
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(tree.getSize(), 6),
//                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 5),
//                () -> Assertions.assertEquals(tree.getDepth(0), 1),
//                () -> Assertions.assertEquals(tree.getDepth(1), 2),
//                () -> Assertions.assertEquals(tree.getDepth(2), 2),
//                () -> Assertions.assertEquals(tree.getDepth(3), 1),
//                () -> Assertions.assertEquals(tree.getDepth(4), 1)
//        );
//
//        repo.rotateClockWise(2, 3);
//
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(tree.getSize(), 6),
//                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 5),
//                () -> Assertions.assertEquals(tree.getDepth(0), 1),
//                () -> Assertions.assertEquals(tree.getDepth(1), 2),
//                () -> Assertions.assertEquals(tree.getDepth(2), 2),
//                () -> Assertions.assertEquals(tree.getDepth(3), 2),
//                () -> Assertions.assertEquals(tree.getDepth(4), 1)
//        );
//
//        repo.rotateClockWise(2,3);
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(tree.getSize(), 7),
//                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 5),
//                () -> Assertions.assertEquals(tree.getDepth(0), 1),
//                () -> Assertions.assertEquals(tree.getDepth(1), 2),
//                () -> Assertions.assertEquals(tree.getDepth(2), 3),
//                () -> Assertions.assertEquals(tree.getDepth(3), 3),
//                () -> Assertions.assertEquals(tree.getDepth(4), 1)
//        );
//
//        repo.rotateClockWise(2,3);
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(tree.getSize(), 8),
//                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 5),
//                () -> Assertions.assertEquals(tree.getDepth(0), 1),
//                () -> Assertions.assertEquals(tree.getDepth(1), 2),
//                () -> Assertions.assertEquals(tree.getDepth(2), 4),
//                () -> Assertions.assertEquals(tree.getDepth(3), 4),
//                () -> Assertions.assertEquals(tree.getDepth(4), 1)
//        );
//
//        repo.rotateClockWise(3,4);
//        Assertions.assertAll(
//                () -> Assertions.assertEquals(tree.getSize(), 8),
//                () -> Assertions.assertEquals(tree.getSizeValuesOnly(), 5),
//                () -> Assertions.assertEquals(tree.getDepth(0), 1),
//                () -> Assertions.assertEquals(tree.getDepth(1), 2),
//                () -> Assertions.assertEquals(tree.getDepth(2), 4),
//                () -> Assertions.assertEquals(tree.getDepth(3), 4),
//                () -> Assertions.assertEquals(tree.getDepth(4), 4)
//        );
//    }


//
//    private List<View> generateSomeViews(){
//        List<View> views = new ArrayList<>();
//        for(int i = 0; i < 5; i++){
//            Point position = Point.create(i,i);
//            views.add(ViewCreator.create(i, position, Dimension2D.create(5,5)));
//        }
//        return views;
//    }
}
