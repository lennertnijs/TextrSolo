package com.textr.filebuffer;

import com.textr.filebufferV2.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class HistoryTest {

    Deque<MockAction> toDo;
    Deque<MockAction> done;
    Deque<MockAction> removed;

    @BeforeEach
    void setUp() {
        toDo = new LinkedList<>();
        done = new LinkedList<>();
        removed = new LinkedList<>();
    }

    @Test
    void executeAndAddAction() {
        MockAction action1 = new MockAction();
        MockAction action2 = new MockAction();
        MockAction action3 = new MockAction();
        MockAction action4 = new MockAction();

        MockAction altAction3 = new MockAction();

        // Add some actions and execute
        History history = new History();
        history.executeAndAddAction(action1);
        assertTrue(action1.wasExecuted, "Action added to ChangeHistory was not executed");
        done.push(action1);
        history.executeAndAddAction(action2);
        assertTrue(action2.wasExecuted, "Action added to ChangeHistory was not executed");
        done.push(action2);
        history.executeAndAddAction(action3);
        assertTrue(action3.wasExecuted, "Action added to ChangeHistory was not executed");
        done.push(action3);
        history.executeAndAddAction(action4);
        assertTrue(action4.wasExecuted, "Action added to ChangeHistory was not executed");
        done.push(action4);

        // Undo a few operations
        history.undo();
        done.pop();
        toDo.push(action4);
        assertFalse(action4.wasExecuted);
        history.undo();
        done.pop();
        toDo.push(action3);
        assertFalse(action3.wasExecuted);

        // Add a new action
        history.executeAndAddAction(altAction3);
        assertTrue(altAction3.wasExecuted, "Action added to ChangeHistory was not executed");
        removed = new LinkedList<>(toDo);
        toDo.clear();
        done.push(altAction3);

        checkActionExecution();
        // Redoing should have no effect
        history.redo();
        checkActionExecution();
    }

    @Test
    void undo() {
        MockAction action1 = new MockAction();
        MockAction action2 = new MockAction();

        // Add some actions and execute
        History history = new History();
        history.executeAndAddAction(action1);
        done.push(action1);
        checkActionExecution();
        history.executeAndAddAction(action2);
        done.push(action2);
        checkActionExecution();

        // Undo actions
        history.undo();
        done.pop();
        toDo.push(action2);
        checkActionExecution();
        history.undo();
        done.pop();
        toDo.push(action1);
        checkActionExecution();
    }

    @Test
    void redo() {
        MockAction action1 = new MockAction();
        MockAction action2 = new MockAction();

        // Add some actions and execute
        History history = new History();
        history.executeAndAddAction(action1);
        done.push(action1);
        checkActionExecution();
        history.executeAndAddAction(action2);
        done.push(action2);
        checkActionExecution();

        // Undo actions
        history.undo();
        done.pop();
        toDo.push(action2);
        checkActionExecution();
        history.undo();
        done.pop();
        toDo.push(action1);
        checkActionExecution();

        // Redo actions
        history.redo();
        toDo.pop();
        done.push(action1);
        checkActionExecution();
        history.redo();
        toDo.pop();
        done.push(action2);
        checkActionExecution();
    }

    private void checkActionExecution() {
        for (MockAction action: toDo) {
            assertFalse(action.wasExecuted, "Action supposed to be yet to do was executed");
        }
        for (MockAction action: done) {
            assertTrue(action.wasExecuted, "Action supposed to be done was not yet executed");
        }
        for (MockAction action: removed) {
            assertFalse(action.wasExecuted, "Action removed after new Action is still considered executed");
        }
    }
}

class MockAction implements Action {

    boolean wasExecuted = false;

    @Override
    public int execute() {
        if (wasExecuted)
            throw new IllegalStateException("Action executed twice");
        wasExecuted = true;
        return -1;
    }

    @Override
    public int undo() {
        if (!wasExecuted)
            throw new IllegalStateException("Un-executed action undone");
        wasExecuted = false;
        return -1;
    }
}