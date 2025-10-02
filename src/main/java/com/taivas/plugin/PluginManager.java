package com.taivas.plugin;

import com.taivas.mb2_plugin_lib.schema.*;
import com.taivas.rcon.RconClient;
import com.taivas.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

public class PluginManager {
    private static final Logger LOG = LoggerFactory.getLogger(PluginManager.class);

    private final String pluginPath;
    private List<Plugin> plugins;

    public PluginManager(String pluginPath) {
        this.pluginPath = pluginPath;
        plugins = new ArrayList<>();
    }

    public void reload(RconClient rcon, Settings settings) throws IOException {
        LOG.info("Attempting to reload plugins");

        if (plugins != null && !plugins.isEmpty()) {
            for (Plugin plugin : plugins) {
                LOG.info("Deactivating plugin {}...", plugin.getPluginName());
                plugin.onPluginDeactivate();
            }
        }

        plugins = new ArrayList<>();
        File pluginsDirectory = new File(pluginPath);
        if (!pluginsDirectory.exists()) {
            LOG.info("Plugin directory doesn't exists, creating a new one at {}", pluginsDirectory.getAbsolutePath());
            if (!pluginsDirectory.mkdir()) {
                LOG.error("No plugins directory found and couldn't create a new one! Panic!");
                return;
            }
        }

        if (!pluginsDirectory.isDirectory()) {
            LOG.error("Can't make plugin directory, a file with the same name already exists!");
            return;
        }

        File[] files = pluginsDirectory.listFiles((dir, name) -> name.endsWith(".jar"));
        if (files == null || files.length < 1) {
            LOG.error("No plugin jars found!");
        }

        LOG.info("Registering default plugin");
        plugins.add(new PluginSystemCommands(this));

        LOG.info("Found {} plugin jars", files.length);
        for (File file : files) {
            LOG.info("Loading {}", file.getName());
            URL[] urls = new URL[1];
            urls[0] = file.toURI().toURL();
            try (URLClassLoader pluginClassLoader = new URLClassLoader(urls, PluginManager.class.getClassLoader())) {
                ServiceLoader<Plugin> loader = ServiceLoader.load(Plugin.class, pluginClassLoader);
                for (Plugin plugin : loader) {
                    LOG.info("    - {} registered", plugin.getPluginName());
                    plugins.add(plugin);
                }
            }
        }

        LOG.info("Finished loading jars, {} plugins registered", plugins.size());
        for (Plugin plugin : plugins) {
            LOG.info("Activating plugin {}...", plugin.getPluginName());
            plugin.onPluginActivate(rcon, settings);
        }

        LOG.info("Finished reloading plugins ");
    }

    public Plugin[] getPlugins() {
        return plugins.toArray(new Plugin[0]);
    }

    public void callAdminSay(AdminSayEvent adminSayEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onAdminSay(adminSayEvent);
        }

    }

    public void callClientBegin(ClientBeginEvent clientBeginEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onClientBegin(clientBeginEvent);
        }
    }

    public void callClientConnect(ClientConnectEvent clientConnectEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onClientConnect(clientConnectEvent);
        }
    }

    public void callClientDisconnect(ClientDisconnectEvent clientDisconnectEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onClientDisconnect(clientDisconnectEvent);
        }
    }

    public void callClientSpawned(ClientSpawnedEvent clientSpawnedEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onClientSpawned(clientSpawnedEvent);
        }
    }

    public void callClientUserinfoChanged(ClientUserinfoChangedEvent clientUserinfoChangedEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onClientUserinfoChanged(clientUserinfoChangedEvent);
        }
    }

    public void callFragLimitHit(FragLimitHitEvent fragLimitHitEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onFragLimitHit(fragLimitHitEvent);
        }
    }

    public void callInitGame(InitGameEvent initGameEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onInitGame(initGameEvent);
        }
    }

    public void callKill(KillEvent killEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onKill(killEvent);
        }
    }

    public void callSay(SayEvent sayEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onSay(sayEvent);
        }
    }

    public void callSendingGameReport(SendingGameReportEvent sendingGameReportEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onSendingGameReport(sendingGameReportEvent);
        }
    }

    public void callServerInitialization(ServerInitializationEvent serverInitializationEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onServerInitialization(serverInitializationEvent);
        }
    }

    public void callShutdownGame(ShutdownGameEvent shutdownGameEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onShutdownGame(shutdownGameEvent);
        }
    }

    public void callSmod(SmodEvent smodEvent) {
        List<Plugin> cachedPlugins = plugins;
        for (Plugin plugin : cachedPlugins) {
            plugin.onSmod(smodEvent);
        }
    }
}
