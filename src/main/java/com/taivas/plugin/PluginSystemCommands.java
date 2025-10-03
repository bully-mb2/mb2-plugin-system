package com.taivas.plugin;


import com.taivas.mb2_plugin_lib.schema.*;
import com.taivas.rcon.RconClient;
import com.taivas.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;

public class PluginSystemCommands implements MBPlugin {

    private static final Logger LOG = LoggerFactory.getLogger(PluginSystemCommands.class);

    private final MBPluginManager pluginManager;
    private RconClient rcon;
    private Settings settings;

    public PluginSystemCommands(MBPluginManager pluginManager) {
        this.pluginManager = pluginManager;
    }

    @Override
    public String getPluginName() {
        return "Default System Commands Plugin";
    }

    @Override
    public void onPluginActivate(RconClient rcon, Settings settings) {
        this.rcon = rcon;
        this.settings = settings;
    }

    @Override
    public void onPluginDeactivate() {
    }

    @Override
    public void onAdminSay(AdminSayEvent adminSayEvent) {
        String message = adminSayEvent.getMessage();
        if (message.startsWith("!plugins")) {
            SendPluginList();
            return;
        }

        if (message.startsWith("!start_plugins")) {
            rcon.printConAll("Starting plugins...");
            StartPlugins();
            rcon.printConAll("Successfully started plugins!");
            return;
        }

        if (message.startsWith("!stop_plugins")) {
            rcon.printConAll("Stopping plugins...");
            StopPlugins();
            rcon.printConAll("Successfully stopped plugins!");
            return;
        }

        if (message.startsWith("!reload_plugins")) {
            rcon.printConAll("Reloading plugins...");
            StartPlugins();
            rcon.printConAll("Successfully reloaded plugins!");
            return;
        }
    }

    private void StartPlugins() {
        try {
            settings.load();
            pluginManager.startPlugins(rcon, settings);
            SendPluginList();
        } catch (IOException e) {
            rcon.printConAll("Something went wrong loading plugins, check the log for more details");
            LOG.error("Couldn't load server plugins", e);
        }
    }

    private void StopPlugins() {
        pluginManager.stopPlugins();
    }

    private void SendPluginList() {
        MBPlugin[] plugins = pluginManager.getPlugins();
        rcon.printConAll(String.format("Server has %d plugins loaded:", plugins.length));
        for (MBPlugin plugin : plugins) {
            rcon.printConAll(String.format("   - %s", plugin.getPluginName()));
        }
    }

    @Override
    public void onClientBegin(ClientBeginEvent clientBeginEvent) {

    }

    @Override
    public void onClientConnect(ClientConnectEvent clientConnectEvent) {

    }

    @Override
    public void onClientDisconnect(ClientDisconnectEvent clientDisconnectEvent) {

    }

    @Override
    public void onClientSpawned(ClientSpawnedEvent clientSpawnedEvent) {

    }

    @Override
    public void onClientUserinfoChanged(ClientUserinfoChangedEvent clientUserinfoChangedEvent) {

    }

    @Override
    public void onFragLimitHit(FragLimitHitEvent fragLimitHitEvent) {

    }

    @Override
    public void onInitGame(InitGameEvent initGameEvent) {

    }

    @Override
    public void onKill(KillEvent killEvent) {

    }

    @Override
    public void onSay(SayEvent sayEvent) {

    }

    @Override
    public void onSendingGameReport(SendingGameReportEvent sendingGameReportEvent) {

    }

    @Override
    public void onServerInitialization(ServerInitializationEvent serverInitializationEvent) {

    }

    @Override
    public void onShutdownGame(ShutdownGameEvent shutdownGameEvent) {

    }

    @Override
    public void onSmod(SmodEvent smodEvent) {

    }
}
