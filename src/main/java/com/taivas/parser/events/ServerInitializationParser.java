package com.taivas.parser.events;

import com.taivas.mb2_plugin_lib.schema.ServerInitializationEvent;

import java.util.regex.Matcher;

public class ServerInitializationParser extends MBEventParser<ServerInitializationEvent> {

    public ServerInitializationParser() {
        super("^------ Server Initialization ------$");
    }

    @Override
    protected ServerInitializationEvent parseEvent(Matcher matcher) {
        return new ServerInitializationEvent();
    }

}
