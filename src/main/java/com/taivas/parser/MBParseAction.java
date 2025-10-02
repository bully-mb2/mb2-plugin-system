package com.taivas.parser;

import com.taivas.parser.events.MBEventParser;

import java.util.function.Consumer;


public class MBParseAction<T> {

    private final MBEventParser<T> eventParser;
    private final Consumer<T> callback;

    public MBParseAction (MBEventParser<T> eventParser, Consumer<T> callback) {
        this.eventParser = eventParser;
        this.callback = callback;
    }

    public void process(String line) {
        T result = eventParser.parseLine(line);
        if (result == null) {
            return;
        }

        callback.accept(result);
    }

    public String getName() {
        return eventParser.getClass().getSimpleName();
    }
}
