package FileTest;

import com.Textr.File.FileRepo;
import com.Textr.File.FileService;
import com.Textr.File.IFileRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileServiceTest {


    private IFileRepo repo;
    private FileService fileService;


    @BeforeEach
    public void initialise(){
        repo = new FileRepo();
        fileService = new FileService(repo);
    }

    @Test
    public void testAll(){

    }
}
