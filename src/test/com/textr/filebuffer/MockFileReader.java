package com.textr.filebuffer;

import com.textr.file.IFileReader;

import java.io.File;

public final class MockFileReader implements IFileReader {


    @Override
    public String read(File file) {
        return "This is a mock text.";
    }
}
