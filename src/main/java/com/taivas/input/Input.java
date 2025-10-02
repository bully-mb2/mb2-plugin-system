package com.taivas.input;

import com.taivas.settings.Settings;

import java.io.IOException;

public interface Input {

    void open(Settings settings) throws IOException;
    String readLine() throws IOException;

}
