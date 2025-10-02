package com.taivas.parser.events;

import com.taivas.mb2_plugin_lib.schema.FragLimitHitEvent;

import java.util.regex.Matcher;

public class FragLimitHitParser extends MBEventParser<FragLimitHitEvent> {

    public FragLimitHitParser() {
        super("^Exit: Kill limit hit.$");
    }

    @Override
    protected FragLimitHitEvent parseEvent(Matcher matcher) {
        return new FragLimitHitEvent();
    }

}
