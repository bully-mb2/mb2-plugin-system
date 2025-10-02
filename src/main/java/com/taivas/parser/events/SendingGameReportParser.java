package com.taivas.parser.events;

import com.taivas.mb2_plugin_lib.schema.SendingGameReportEvent;

import java.util.regex.Matcher;

public class SendingGameReportParser extends MBEventParser<SendingGameReportEvent> {

    public SendingGameReportParser() {
        super("^(?:Sending|Saving) Game Report$");
    }

    @Override
    protected SendingGameReportEvent parseEvent(Matcher matcher) {
        return new SendingGameReportEvent();
    }

}
