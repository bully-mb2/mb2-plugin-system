package com.taivas.plugin;

import com.taivas.mb2_plugin_lib.schema.*;
import com.taivas.rcon.RconClient;
import com.taivas.settings.Settings;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MBPluginManager {
    private static final Logger LOG = LoggerFactory.getLogger(MBPluginManager.class);

    private final String pluginPath;
    private final PluginManager pluginManager;
    private final PluginSystemCommands defaultPlugin;
    private List<MBPlugin> plugins;

    public MBPluginManager(String pluginPath) {
        this.pluginPath = pluginPath;
        this.pluginManager = new DefaultPluginManager(Path.of(pluginPath));
        this.defaultPlugin = new PluginSystemCommands(this);
        this.plugins = new ArrayList<>();
    }

    public void initialize(RconClient rcon, Settings settings) {
        defaultPlugin.onPluginActivate(rcon, settings);
    }

    public void startPlugins(RconClient rcon, Settings settings) {
        LOG.info("Attempting to start plugins");
        if (!plugins.isEmpty()) {
            LOG.info("Activated plugins detected");
            stopPlugins();
        }

        File pluginsDirectory = new File(pluginPath);
        if (!pluginsDirectory.exists()) {
            LOG.info("MBPlugin directory doesn't exists, creating a new one at {}", pluginsDirectory.getAbsolutePath());
            if (!pluginsDirectory.mkdir()) {
                LOG.error("No plugins directory found and couldn't create a new one! Panic!");
                return;
            }
        }

        if (!pluginsDirectory.isDirectory()) {
            LOG.error("Can't make plugin directory, a file with the same name already exists!");
            return;
        }

        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        List<MBPlugin> newPlugins = pluginManager.getExtensions(MBPlugin.class);
        LOG.info("Registering {} loaded plugins", plugins.size());
        for (MBPlugin plugin : newPlugins) {
            plugins.add(plugin);
            LOG.info("    - {} registered", plugin.getPluginName());
        }

        for (MBPlugin plugin : plugins) {
            LOG.info("Activating plugin {}...", plugin.getPluginName());
            plugin.onPluginActivate(rcon, settings);
        }

        LOG.info("Finished reloading plugins ");
    }

    public void stopPlugins() {
        LOG.info("Attempting to stop plugins");
        if (plugins != null && !plugins.isEmpty()) {
            for (MBPlugin plugin : plugins) {
                LOG.info("Deactivating plugin {}...", plugin.getPluginName());
                plugin.onPluginDeactivate();
            }
        }

        pluginManager.stopPlugins();
        pluginManager.unloadPlugins();
        plugins = new ArrayList<>();
        LOG.info("Finished stopping plugins");
    }

    public MBPlugin[] getPlugins() {
        return plugins.toArray(new MBPlugin[0]);
    }

    public void callAdminSay(AdminSayEvent adminSayEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onAdminSay(adminSayEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onAdminSay(adminSayEvent);
        }
    }

    public void callClientBegin(ClientBeginEvent clientBeginEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onClientBegin(clientBeginEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onClientBegin(clientBeginEvent);
        }
    }

    public void callClientConnect(ClientConnectEvent clientConnectEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onClientConnect(clientConnectEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onClientConnect(clientConnectEvent);
        }
    }

    public void callClientDisconnect(ClientDisconnectEvent clientDisconnectEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onClientDisconnect(clientDisconnectEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onClientDisconnect(clientDisconnectEvent);
        }
    }

    public void callClientSpawned(ClientSpawnedEvent clientSpawnedEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onClientSpawned(clientSpawnedEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onClientSpawned(clientSpawnedEvent);
        }
    }

    public void callClientUserinfoChanged(ClientUserinfoChangedEvent clientUserinfoChangedEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onClientUserinfoChanged(clientUserinfoChangedEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onClientUserinfoChanged(clientUserinfoChangedEvent);
        }
    }

    public void callFragLimitHit(FragLimitHitEvent fragLimitHitEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onFragLimitHit(fragLimitHitEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onFragLimitHit(fragLimitHitEvent);
        }
    }

    public void callInitGame(InitGameEvent initGameEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onInitGame(initGameEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onInitGame(initGameEvent);
        }
    }

    public void callKill(KillEvent killEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onKill(killEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onKill(killEvent);
        }
    }

    public void callSay(SayEvent sayEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onSay(sayEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onSay(sayEvent);
        }
    }

    public void callSendingGameReport(SendingGameReportEvent sendingGameReportEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onSendingGameReport(sendingGameReportEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onSendingGameReport(sendingGameReportEvent);
        }
    }

    public void callServerInitialization(ServerInitializationEvent serverInitializationEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onServerInitialization(serverInitializationEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onServerInitialization(serverInitializationEvent);
        }
    }

    public void callShutdownGame(ShutdownGameEvent shutdownGameEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onShutdownGame(shutdownGameEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onShutdownGame(shutdownGameEvent);
        }
    }

    public void callSmod(SmodEvent smodEvent) {
        List<MBPlugin> cachedMBPlugins = plugins;
        defaultPlugin.onSmod(smodEvent);
        for (MBPlugin plugin : cachedMBPlugins) {
            plugin.onSmod(smodEvent);
        }
    }
}
