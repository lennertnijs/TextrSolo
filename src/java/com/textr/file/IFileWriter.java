package com.textr.file;

import java.io.File;
import java.io.IOException;

public interface IFileWriter {

    void write(String s, File file) throws IOException;
}
