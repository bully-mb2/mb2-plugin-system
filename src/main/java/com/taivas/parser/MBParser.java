package com.taivas.parser;

import com.taivas.mb2_plugin_lib.schema.ClientBeginEvent;
import com.taivas.parser.events.*;
import com.taivas.plugin.PluginManager;
import com.taivas.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MBParser {

    private static final Logger LOG = LoggerFactory.getLogger(MBParser.class);

    private final List<MBParseAction<?>> actions;

    public MBParser() {
        this.actions = new ArrayList<>();
    }

    public void init(Settings settings, PluginManager pluginManager) {
        LOG.info("Initializing actions");
        actions.clear();
        actions.add(new MBParseAction<>(new ClientBeginParser(), pluginManager::callClientBegin));
        actions.add(new MBParseAction<>(new ClientConnectParser(), pluginManager::callClientConnect));
        actions.add(new MBParseAction<>(new ClientDisconnectParser(), pluginManager::callClientDisconnect));
        actions.add(new MBParseAction<>(new ClientSpawnedParser(), pluginManager::callClientSpawned));
        actions.add(new MBParseAction<>(new InitGameParser(), pluginManager::callInitGame));
        actions.add(new MBParseAction<>(new KillParser(), pluginManager::callKill));
        actions.add(new MBParseAction<>(new SayParser(), pluginManager::callSay));
        actions.add(new MBParseAction<>(new AdminSayParser(), pluginManager::callAdminSay));
        actions.add(new MBParseAction<>(new SendingGameReportParser(), pluginManager::callSendingGameReport));
        actions.add(new MBParseAction<>(new ServerInitializationParser(), pluginManager::callServerInitialization));
        actions.add(new MBParseAction<>(new ShutdownGameParser(), pluginManager::callShutdownGame));
        actions.add(new MBParseAction<>(new FragLimitHitParser(), pluginManager::callFragLimitHit));
        actions.add(new MBParseAction<>(new SmodParser(), pluginManager::callSmod));
        actions.add(new MBParseAction<>(new SmodSayParser(), pluginManager::callSmod));
        actions.add(new MBParseAction<>(new SmodSpecialParser(), pluginManager::callSmod));
        actions.add(new MBParseAction<>(new SmodTargetedParser(), pluginManager::callSmod));
        // TODO :: Reintegrate
        // parserList.add(new ClientUserinfoChangedParser());
        LOG.info("Registered {} parsers: ", actions.size());
        for (MBParseAction<?> action : actions) {
            LOG.info("   - {}", action.getName());
        }

        LOG.info("Ready to parse messages");
    }

    public void parseLine(String line) {
        for (MBParseAction<?> action : actions) {
            action.process(line);
        }
    }
}
