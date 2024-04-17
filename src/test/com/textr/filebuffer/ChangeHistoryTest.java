package com.textr.filebuffer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class ChangeHistoryTest {

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
        Cursor dummyCursor = Cursor.createNew(); // WHY IS THIS STATIC METHOD NEEDED??
        MockAction action1 = new MockAction();
        MockAction action2 = new MockAction();
        MockAction action3 = new MockAction();
        MockAction action4 = new MockAction();

        MockAction altAction3 = new MockAction();

        // Add some actions and execute
        ChangeHistory history = new ChangeHistory();
        history.executeAndAddAction(action1, dummyCursor);
        assertTrue(action1.wasExecuted, "Action added to ChangeHistory was not executed");
        done.push(action1);
        history.executeAndAddAction(action2, dummyCursor);
        assertTrue(action2.wasExecuted, "Action added to ChangeHistory was not executed");
        done.push(action2);
        history.executeAndAddAction(action3, dummyCursor);
        assertTrue(action3.wasExecuted, "Action added to ChangeHistory was not executed");
        done.push(action3);
        history.executeAndAddAction(action4, dummyCursor);
        assertTrue(action4.wasExecuted, "Action added to ChangeHistory was not executed");
        done.push(action4);

        // Undo a few operations
        history.undo(dummyCursor);
        done.pop();
        toDo.push(action4);
        assertFalse(action4.wasExecuted);
        history.undo(dummyCursor);
        done.pop();
        toDo.push(action3);
        assertFalse(action3.wasExecuted);

        // Add a new action
        history.executeAndAddAction(altAction3, dummyCursor);
        assertTrue(altAction3.wasExecuted, "Action added to ChangeHistory was not executed");
        removed = new LinkedList<>(toDo);
        toDo.clear();
        done.push(altAction3);

        checkActionExecution();
        // Redoing should have no effect
        history.redo(dummyCursor);
        checkActionExecution();
    }

    @Test
    void undo() {
        Cursor dummyCursor = Cursor.createNew();
        MockAction action1 = new MockAction();
        MockAction action2 = new MockAction();

        // Add some actions and execute
        ChangeHistory history = new ChangeHistory();
        history.executeAndAddAction(action1, dummyCursor);
        done.push(action1);
        checkActionExecution();
        history.executeAndAddAction(action2, dummyCursor);
        done.push(action2);
        checkActionExecution();

        // Undo actions
        history.undo(dummyCursor);
        done.pop();
        toDo.push(action2);
        checkActionExecution();
        history.undo(dummyCursor);
        done.pop();
        toDo.push(action1);
        checkActionExecution();
    }

    @Test
    void redo() {
        Cursor dummyCursor = Cursor.createNew();
        MockAction action1 = new MockAction();
        MockAction action2 = new MockAction();

        // Add some actions and execute
        ChangeHistory history = new ChangeHistory();
        history.executeAndAddAction(action1, dummyCursor);
        done.push(action1);
        checkActionExecution();
        history.executeAndAddAction(action2, dummyCursor);
        done.push(action2);
        checkActionExecution();

        // Undo actions
        history.undo(dummyCursor);
        done.pop();
        toDo.push(action2);
        checkActionExecution();
        history.undo(dummyCursor);
        done.pop();
        toDo.push(action1);
        checkActionExecution();

        // Redo actions
        history.redo(dummyCursor);
        toDo.pop();
        done.push(action1);
        checkActionExecution();
        history.redo(dummyCursor);
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
    public void execute(ICursor cursor) {
        if (wasExecuted)
            throw new IllegalStateException("Action executed twice");
        wasExecuted = true;
    }

    @Override
    public void undo(ICursor cursor) {
        if (!wasExecuted)
            throw new IllegalStateException("Un-executed action undone");
        wasExecuted = false;
    }
}