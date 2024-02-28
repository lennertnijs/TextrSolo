package FileBufferRepo;

import com.Textr.FileBuffer.BufferState;
import com.Textr.FileBuffer.FileBuffer;
import com.Textr.FileBuffer.InsertionPoint;
import com.Textr.FileBufferRepo.ActiveFileBufferRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActiveFileBufferRepoTest {

    private FileBuffer buffer;
    @BeforeEach
    public void initialise(){
        InsertionPoint insertionPoint = InsertionPoint.create(5,5);
        buffer = FileBuffer.builder().id(1).fileId(1).bufferText("text".split(""))
                .insertionPosition(insertionPoint).state(BufferState.CLEAN).build();
    }

    @Test
    public void testSetAndGetBuffer(){
        ActiveFileBufferRepo repo = new ActiveFileBufferRepo();
        Assertions.assertAll(
                () -> Assertions.assertThrows(NullPointerException.class, repo::getBufferId),
                () -> Assertions.assertThrows(NullPointerException.class, repo::getBuffer),
                () -> repo.setBuffer(buffer),
                () -> Assertions.assertEquals(repo.getBufferId(), 1),
                () -> Assertions.assertEquals(repo.getBuffer(), buffer)

        );
    }
}
