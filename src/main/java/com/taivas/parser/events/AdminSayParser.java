package com.taivas.parser.events;

import com.taivas.mb2_plugin_lib.schema.AdminSayEvent;

import java.util.regex.Matcher;

public class AdminSayParser extends MBEventParser<AdminSayEvent> {

    public AdminSayParser() {
        super("^SMOD say: .* \\(adminID: [0-9]{1,2}\\) \\(IP: .*\\) : (.*)$");
    }

    @Override
    protected AdminSayEvent parseEvent(Matcher matcher) {
        AdminSayEvent adminSayEvent = new AdminSayEvent();

        adminSayEvent.setMessage(matcher.group(1));

        return adminSayEvent;
    }

}
