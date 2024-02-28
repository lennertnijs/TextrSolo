package FileBufferRepo;

import com.Textr.FileBuffer.*;
import com.Textr.FileBufferRepo.ActiveFileBufferRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActiveFileBufferRepoTest {

    private FileBuffer buffer;
    private ActiveFileBufferRepo repo;
    @BeforeEach
    public void initialise(){
        InsertionPoint insertionPoint = InsertionPoint.create(5,5);
        buffer = FileBuffer.builder().fileId(1).bufferText(Text.create("text"))
                .insertionPosition(insertionPoint).state(BufferState.CLEAN).build();
        repo = new ActiveFileBufferRepo();
        FileBufferIdGenerator.resetGenerator();
    }

    @Test
    public void testSetAndGetBuffer(){
        Assertions.assertAll(
                () -> repo.setBuffer(buffer),
                () -> Assertions.assertEquals(repo.getBufferId(), 0),
                () -> Assertions.assertEquals(repo.getBuffer(), buffer)

        );
    }

    @Test
    public void testIllegalGetAndSet(){
        Assertions.assertAll(
                () -> Assertions.assertThrows(IllegalArgumentException.class, () -> repo.setBuffer(null)),
                () -> Assertions.assertThrows(NullPointerException.class, repo::getBufferId),
                () -> Assertions.assertThrows(NullPointerException.class, repo::getBuffer)
        );
    }
}
