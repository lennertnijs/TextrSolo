package com.textr.view;

import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.util.Dimension2D;
import com.textr.util.Direction;
import com.textr.util.Point;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.LinkedList;

class SnakeViewTest {
    SnakeView view1;
    @BeforeEach
    void initialize(){
        view1 = new SnakeView(Point.create(0,0), Dimension2D.create(20,20));
        view1.initializeGame();
    }
    @AfterEach
    void tearDown() {}
    @Test
    void initializeGame() {
        Assertions.assertEquals(view1.getPosition(), Point.create(0,0));
        Assertions.assertEquals(view1.getDimensions(), Dimension2D.create(20,20));


    }
    @Test
    void getSnake() {
        LinkedList<Point> testSnake = new LinkedList<>();
        testSnake.add(Point.create(10, 10));
        int k = 1;
        while(k<6){
            testSnake.add(Point.create(10-k, 10));
            k++;
        }
        Assertions.assertEquals(view1.getSnake(), testSnake);
    }

    @Test
    void getFoods() {
        LinkedList<Point> testSnake = new LinkedList<>();
        testSnake.add(Point.create(10, 10));
        int k = 1;
        while(k<6){
            testSnake.add(Point.create(10-k, 10));
            k++;
        }
        Assertions.assertEquals(view1.getSnake(), testSnake);
        Assertions.assertFalse(testSnake.contains(view1.getFoods().get(0)));
        Assertions.assertFalse(testSnake.contains(view1.getFoods().get(1)));
        Assertions.assertFalse(testSnake.contains(view1.getFoods().get(2)));
    }

    @Test
    void getHeadOrientation() {
        Assertions.assertEquals(view1.getHeadOrientation(), Direction.RIGHT);
    }

    @Test
    void incrementTimer() {
        for (int i =0; i<98 ; i++){
            view1.incrementTimer();
        }
        Assertions.assertFalse(view1.incrementTimer());
        Assertions.assertTrue(view1.incrementTimer());
        LinkedList<Point> testSnake = new LinkedList<>();
        testSnake.add(Point.create(11, 10));
        int k = 1;
        while(k<6){
            testSnake.add(Point.create(10-k+1, 10));
            k++;
        }
        Assertions.assertEquals(testSnake, view1.getSnake());

    }
    @Test
    void generateStatusBar() {
        Assertions.assertTrue(view1.generateStatusBar().contains("Score : 0"));
        for (int i =0; i<100 ; i++){
            view1.incrementTimer();
        }
        Assertions.assertTrue(view1.generateStatusBar().contains("Score : 1"));
    }


    @Test
    void resize() {
    }

    @Test
    void handleInput() {
        Input input1 = Input.createSpecialInput(InputType.ARROW_DOWN);
        view1.handleInput(input1);
        Assertions.assertEquals( view1.getHeadOrientation(), Direction.DOWN);
        Input input2 = Input.createSpecialInput(InputType.ARROW_UP);
        view1.handleInput(input2);
        Assertions.assertEquals( view1.getHeadOrientation(), Direction.UP);
        Input input3 = Input.createSpecialInput(InputType.ARROW_LEFT);
        view1.handleInput(input3);
        Assertions.assertEquals( view1.getHeadOrientation(), Direction.UP);
        Input input4 = Input.createSpecialInput(InputType.ARROW_RIGHT);
        view1.handleInput(input4);
        Assertions.assertEquals( view1.getHeadOrientation(), Direction.RIGHT);
        Input input5 = Input.createCharacterInput('a');
        view1.handleInput(input5);
        for (int i =0; i<1100 ; i++){
            view1.incrementTimer();
        }
        Input input6 = Input.createSpecialInput(InputType.ENTER);
        view1.handleInput(input6);
        Assertions.assertTrue(view1.getRunning());

    }

    @Test
    void getRunning() {
        Assertions.assertTrue(view1.getRunning());
    }
}