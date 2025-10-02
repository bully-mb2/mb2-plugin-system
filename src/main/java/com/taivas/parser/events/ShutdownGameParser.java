package com.taivas.parser.events;

import com.taivas.mb2_plugin_lib.schema.ShutdownGameEvent;

import java.util.regex.Matcher;

public class ShutdownGameParser extends MBEventParser<ShutdownGameEvent> {

    public ShutdownGameParser() {
        super("^ShutdownGame:$");
    }

    @Override
    protected ShutdownGameEvent parseEvent(Matcher matcher) {
        return new ShutdownGameEvent();
    }

}
