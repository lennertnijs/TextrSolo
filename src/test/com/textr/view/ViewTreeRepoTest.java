package com.textr.view;

import com.textr.Settings;
import com.textr.service.LayoutGenerator;
import com.textr.util.Dimension2D;
import com.textr.util.Point;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ViewTreeRepoTest {

    private View view1;

    private View view2;

    private View view3;

    private View view4;

    private View view5;

    private View view6;

    private Dimension2D terminalDimensions;
    private ViewTreeRepo repo;
    private List<View> views;
    private LayoutGenerator layoutGenerator;


    @BeforeEach
    void initialise(){
        views = new ArrayList<>();
        Point initPoint = new Point(0,0);
        Dimension2D initDimension = new Dimension2D(10,10);
        Settings.defaultLineSeparator = "\r\n";
        view1 = new MockView(initPoint, initDimension);
        views.add(view1);
        view2 = new MockView(initPoint, initDimension);
        views.add(view2);
        view3 = new MockView(initPoint, initDimension);
        views.add(view3);
        view4 = new MockView(initPoint, initDimension);
        views.add(view4);
        view5 = new MockView(initPoint, initDimension);
        views.add(view5);
        view6 = new MockView(initPoint, initDimension);
        views.add(view6);
        repo = new ViewTreeRepo();
        repo.addAll(views);
        terminalDimensions = new Dimension2D(100,100);
        layoutGenerator = new LayoutGenerator(repo);
    }
    @Test
    void testAdd(){
        assertAll(
                () -> assertEquals(repo.get(0), views.get(0)),
                () -> assertEquals(repo.get(1), views.get(1)),
                () -> assertEquals(repo.get(2), views.get(2)),
                () -> assertEquals(repo.get(3), views.get(3)),
                () -> assertEquals(repo.get(4), views.get(4)),
                () -> assertEquals(repo.get(5), views.get(5)),
                () -> assertEquals(repo.getSize(), 6),
                () -> assertTrue(repo.getAllAtDepth(1).contains(view3) )
        );
    }
    @Test
    void testSetActive(){
        repo.setActive(view1);
        assertEquals(repo.getActive(), view1);
    }
    @Test
    void testrootIsVertical(){
        assertTrue(repo.rootIsVertical());
    }

    @Test
    void testsetActiveNext(){
        repo.setActive(view1);
        repo.setNextActive();
        assertEquals(repo.getActive(), view2);
        repo.rotate(true);
        repo.setNextActive();
        assertEquals(repo.getActive(), view4);
        repo.setPreviousActive();
        assertEquals(repo.getActive(), view2);
    }

    @Test
    public void testAddToNext(){
        View mockView = new MockView(new Point(0, 0), new Dimension2D(5, 5));
        repo.setActive(view1);
        repo.addNextTo(mockView, view1);
        assertEquals(view1, repo.getActive());
        repo.setNextActive();
        assertEquals(mockView, repo.getActive());
    }

    @Test
    void testRemove(){
        repo.remove(view3);
        assertFalse(repo.getAll().contains(view3));
        repo.removeAll();
        assertTrue(repo.getAll().isEmpty());
    }
    @Test
    void rotateTest(){
        repo.setActive(view2);
        repo.rotate(true);
        assertAll(
                () -> assertEquals(repo.getSize(), 6),
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(null);
                    actual.add(view4);
                    actual.add(view5);
                    actual.add(view6);
                    assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> assertEquals(repo.getAllAtDepth(2), List.of(view3, view2))
        );
        layoutGenerator.generate(terminalDimensions);
        repo.setActive(view3);
        repo.rotate(true);
        assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(view2);
                    actual.add(view4);
                    actual.add(view5);
                    actual.add(view6);
                    assertEquals(repo.getAllAtDepth(1), actual);
                }
        );
        layoutGenerator.generate(terminalDimensions);
        repo.setActive(view2);
        repo.rotate(false);
        assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(null);
                    actual.add(view5);
                    actual.add(view6);
                    assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> assertEquals(repo.getAllAtDepth(2), List.of(view2, view4))
        );
        layoutGenerator.generate(terminalDimensions);
        repo.setActive(view4);
        repo.rotate(true);
        assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(null);
                    actual.add(view6);
                    assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> assertEquals(repo.getAllAtDepth(2), List.of(view2, view5, view4))
        );
        layoutGenerator.generate(terminalDimensions);
        repo.setActive(view4);
        repo.rotate(false);
        assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(null);
                    assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> assertEquals(repo.getAllAtDepth(2), List.of(view2, view5, view4, view6))
        );
        layoutGenerator.generate(terminalDimensions);

        repo.setActive(view3);
        repo.rotate(false);
        assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(view2);
                    actual.add(null);
                    assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> assertEquals(repo.getAllAtDepth(2), List.of(view5, view4, view6))
        );
        layoutGenerator.generate(terminalDimensions);

        repo.setActive(view2);
        repo.rotate(true);
        assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(view3);
                    actual.add(view5);
                    actual.add(view2);
                    actual.add(null);
                    assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> assertEquals(repo.getAllAtDepth(2), List.of(view4, view6))
        );
        layoutGenerator.generate(terminalDimensions);

        repo.setActive(view3);
        repo.rotate(false);
        assertAll(
                () -> {
                    List<View> actual = new ArrayList<>();
                    actual.add(view1);
                    actual.add(null);
                    actual.add(view2);
                    actual.add(null);
                    assertEquals(repo.getAllAtDepth(1), actual);
                },
                () -> assertEquals(repo.getAllAtDepth(2), List.of(view3,view5,view4, view6))

        );
        layoutGenerator.generate(terminalDimensions);
    }

}
