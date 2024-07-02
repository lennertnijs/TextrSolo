package com.textr.view;

import com.textr.filebuffer.FileBuffer;
import com.textr.filebuffer.MockFileReader;
import com.textr.filebuffer.MockFileWriter;
import com.textr.input.Input;
import com.textr.input.InputType;
import com.textr.terminal.MockCommunicator;
import com.textr.util.Dimension2D;
import com.textr.util.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public final class BufferViewTest {

    private final Point position = new Point(5, 15);
    private final Dimension2D dimensions = new Dimension2D(50, 25);
    private final Point anchor = new Point(2, 2);
    private final MockCommunicator communicator = new MockCommunicator();
    private final String fileContent = "This is a mock text.";
    private final File file = Mockito.mock(File.class);
    private FileBuffer fileBuffer;
    private BufferView bufferView;

    @BeforeEach
    public void initialise(){
        FileBuffer.setFileReader(new MockFileReader());
        FileBuffer.setFileWriter(new MockFileWriter());
        fileBuffer = new FileBuffer(file);
        // fills all the fields
        bufferView = BufferView.builder()
                .position(position)
                .dimensions(dimensions)
                .anchor(anchor)
                .communicator(communicator)
                .file(file)
                .fileBuffer(fileBuffer)
                .build();
    }

    @Test
    public void testGetPosition(){
        assertEquals(position, bufferView.getPosition());
    }

    @Test
    public void testSetPosition(){
        bufferView.setPosition(new Point(500, 500));
        assertEquals(new Point(500, 500), bufferView.getPosition());
    }

    @Test
    public void testSetPositionToNull(){
        assertThrows(NullPointerException.class,
                () -> bufferView.setPosition(null));
    }

    @Test
    public void testGetDimensions(){
        assertEquals(dimensions, bufferView.getDimensions());
    }

    @Test
    public void testSetDimensions(){
        bufferView.setDimensions(new Dimension2D(2, 600));
        assertEquals(new Dimension2D(2, 600), bufferView.getDimensions());
    }

    @Test
    public void testSetDimensionsToNull(){
        assertThrows(NullPointerException.class,
                () -> bufferView.setDimensions(null));
    }

    @Test
    public void testBuilderWithoutPositionAllowed(){
        BufferView view = BufferView.builder()
                            .dimensions(dimensions)
                            .anchor(anchor)
                            .communicator(communicator)
                            .file(file)
                            .fileBuffer(fileBuffer)
                            .build();
        Point defaultPosition = new Point(0, 0);
        assertEquals(defaultPosition, view.getPosition());
    }

    @Test
    public void testBuilderWithNullPosition(){
        assertThrows(NullPointerException.class,
                () -> BufferView.builder()
                        .position(null)
                        .dimensions(dimensions)
                        .anchor(anchor)
                        .communicator(communicator)
                        .file(file)
                        .fileBuffer(fileBuffer)
                        .build());
    }

    @Test
    public void testBuilderWithNoDimensionsAllowed(){
        BufferView view = BufferView.builder()
                .position(position)
                .anchor(anchor)
                .communicator(communicator)
                .file(file)
                .fileBuffer(fileBuffer)
                .build();
        Dimension2D defaultDimensions = new Dimension2D(1, 1);
        assertEquals(defaultDimensions, view.getDimensions());
    }

    @Test
    public void testBuilderWithNullDimensions(){
        assertThrows(NullPointerException.class,
                () -> BufferView.builder()
                        .position(position)
                        .dimensions(null)
                        .anchor(anchor)
                        .communicator(communicator)
                        .file(file)
                        .fileBuffer(fileBuffer)
                        .build());
    }

    @Test
    public void testBuilderWithNoAnchorAllowed(){
        BufferView view = BufferView.builder()
                .position(position)
                .dimensions(dimensions)
                .communicator(communicator)
                .file(file)
                .fileBuffer(fileBuffer)
                .build();
        Point defaultAnchor = new Point(0, 0);
        assertEquals(defaultAnchor, view.getAnchor());
    }

    @Test
    public void testBuilderWithNullAnchor(){
        assertThrows(NullPointerException.class,
                () -> BufferView.builder()
                        .position(position)
                        .dimensions(dimensions)
                        .anchor(null)
                        .communicator(communicator)
                        .file(file)
                        .fileBuffer(fileBuffer)
                        .build());
    }

    @Test
    public void testBuilderWithNoCommunicator(){
        assertThrows(NullPointerException.class,
                () -> BufferView.builder()
                        .position(position)
                        .dimensions(dimensions)
                        .anchor(anchor)
                        .file(file)
                        .fileBuffer(fileBuffer)
                        .build());
    }

    @Test
    public void testBuilderWithNullCommunicator(){
        assertThrows(NullPointerException.class,
                () -> BufferView.builder()
                        .position(position)
                        .dimensions(dimensions)
                        .anchor(anchor)
                        .communicator(null)
                        .file(file)
                        .fileBuffer(fileBuffer)
                        .build());
    }

    @Test
    public void testBuilderWithNoFileOrFileBuffer(){
        assertThrows(NullPointerException.class,
                () -> BufferView.builder()
                        .position(position)
                        .dimensions(dimensions)
                        .anchor(anchor)
                        .communicator(communicator)
                        .build());
    }

    @Test
    public void testBuilderWithNoFileButSetFileBufferAllowed(){
        BufferView view =  BufferView.builder()
                        .position(position)
                        .dimensions(dimensions)
                        .anchor(anchor)
                        .communicator(communicator)
                        .fileBuffer(fileBuffer)
                        .build();
        assertEquals(fileContent, view.getText().getContent());
    }

    @Test
    public void testBuilderWithNoFileBufferButSetFileAllowed(){
        BufferView view =  BufferView.builder()
                .position(position)
                .dimensions(dimensions)
                .anchor(anchor)
                .communicator(communicator)
                .file(file)
                .build();
        assertEquals(fileContent, view.getText().getContent());
    }

    @Test
    public void testBuilderWithFileAndFileBufferSet(){
        BufferView view =  BufferView.builder()
                .position(position)
                .dimensions(dimensions)
                .anchor(anchor)
                .communicator(communicator)
                .file(file)
                .fileBuffer(fileBuffer)
                .build();
        assertEquals(fileContent, view.getText().getContent());
        // uses the file buffer
    }

    @Test
    public void testHandleInputCHARACTER(){
        Input input = Input.createCharacterInput('c');
        assertTrue(bufferView.handleInput(input));
        String expected = "cThis is a mock text.";
        assertEquals(expected, bufferView.getText().getContent());
        assertEquals(new Point(1, 0), bufferView.getInsertPoint());
    }

    @Test
    public void testHandleInputENTER(){
        Input input = Input.createSpecialInput(InputType.ENTER);
        assertTrue(bufferView.handleInput(input));
        String expected = "\nThis is a mock text.";
        assertEquals(expected, bufferView.getText().getContent());
        assertEquals(new Point(0, 1), bufferView.getInsertPoint());
    }

    @Test
    public void testHandleInputDELETE(){
        Input input = Input.createSpecialInput(InputType.DELETE);
        assertTrue(bufferView.handleInput(input));
        String expected = "his is a mock text.";
        assertEquals(expected, bufferView.getText().getContent());
        assertEquals(new Point(0, 0), bufferView.getInsertPoint());
    }

    @Test
    public void testHandleInputBACKSPACE(){
        Input input = Input.createSpecialInput(InputType.BACKSPACE);
        bufferView.handleInput(Input.createSpecialInput(InputType.ARROW_RIGHT));
        assertTrue(bufferView.handleInput(input));
        String expected = "his is a mock text.";
        assertEquals(expected, bufferView.getText().getContent());
        assertEquals(new Point(0, 0), bufferView.getInsertPoint());
    }

    @Test
    public void testHandleInputARROWUP(){
        Input input = Input.createSpecialInput(InputType.ARROW_UP);
        bufferView.handleInput(Input.createSpecialInput(InputType.ENTER));
        assertEquals(new Point(0, 1), bufferView.getInsertPoint());
        assertTrue(bufferView.handleInput(input));
        assertEquals(new Point(0, 0), bufferView.getInsertPoint());
    }

    @Test
    public void testHandleInputARROWRIGHT(){
        Input input = Input.createSpecialInput(InputType.ARROW_RIGHT);
        assertTrue(bufferView.handleInput(input));
        assertEquals(new Point(1, 0), bufferView.getInsertPoint());
    }

    @Test
    public void testHandleInputARROWDOWN(){
        Input input = Input.createSpecialInput(InputType.ARROW_DOWN);
        // add a second line and move back up
        bufferView.handleInput(Input.createSpecialInput(InputType.ENTER));
        bufferView.handleInput(Input.createSpecialInput(InputType.ARROW_UP));

        assertEquals(new Point(0, 0), bufferView.getInsertPoint());
        assertTrue(bufferView.handleInput(input));
        assertEquals(new Point(0, 1), bufferView.getInsertPoint());
    }

    @Test
    public void testHandleInputARROWLEFT(){
        Input input = Input.createSpecialInput(InputType.ARROW_LEFT);
        // move right one, so move left works
        bufferView.handleInput(Input.createSpecialInput(InputType.ARROW_RIGHT));

        assertTrue(bufferView.handleInput(input));
        assertEquals(new Point(0, 0), bufferView.getInsertPoint());
    }

    @Test
    public void testHandleInputCTRLZ(){
        Input input = Input.createSpecialInput(InputType.CTRL_Z);
        // insert something to undo
        bufferView.handleInput(Input.createSpecialInput(InputType.ENTER));
        assertEquals("\nThis is a mock text.", bufferView.getText().getContent());

        assertTrue(bufferView.handleInput(input));
        assertEquals("This is a mock text.", bufferView.getText().getContent());
    }

    @Test
    public void testHandleInputCTRLU(){
        Input input = Input.createSpecialInput(InputType.CTRL_U);
        // insert something to undo and undo it
        bufferView.handleInput(Input.createSpecialInput(InputType.ENTER));
        assertEquals("\nThis is a mock text.", bufferView.getText().getContent());
        bufferView.handleInput(Input.createSpecialInput(InputType.CTRL_Z));
        assertEquals("This is a mock text.", bufferView.getText().getContent());

        // redo the undo
        assertTrue(bufferView.handleInput(input));
        assertEquals("\nThis is a mock text.", bufferView.getText().getContent());
    }

    @Test
    public void testHandleInputCTRLS(){
        Input input = Input.createSpecialInput(InputType.CTRL_S);
        assertTrue(bufferView.handleInput(input));
    }

    @Test
    public void testHandleInputNotMapped(){
        Input input = Input.createSpecialInput(InputType.CTRL_G);
        assertFalse(bufferView.handleInput(input));
    }

    @Test
    public void testCanClose(){
        communicator.setPermissions(false);
        assertTrue(bufferView.canClose());
        bufferView.handleInput(Input.createSpecialInput(InputType.ENTER));
        assertFalse(bufferView.canClose());
        bufferView.handleInput(Input.createSpecialInput(InputType.CTRL_S));
        assertTrue(bufferView.canClose());
    }

    @Test
    public void testPrepareToClose(){
        // can't test this here
        bufferView.prepareToClose();
    }

    @Test
    public void testGetStatusBar(){
        String expected = "File path: null - Lines: 1 - Characters: 20 - Cursor: (0, 0) - State: CLEAN";
        assertEquals(expected, bufferView.getStatusBar());
    }

    @Test
    public void testDuplicate(){
        BufferView view = bufferView.duplicate();
        // both have the same file buffer, but cant test that.
        assertEquals(view.getText().getContent(), bufferView.getText().getContent());
    }
}
