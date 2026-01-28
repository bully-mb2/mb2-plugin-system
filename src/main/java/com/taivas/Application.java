package com.taivas;

import com.taivas.input.Input;
import com.taivas.input.InputFactory;
import com.taivas.input.NoInputFoundException;
import com.taivas.parser.MBParser;
import com.taivas.plugin.MBPluginManager;
import com.taivas.rcon.RconClient;
import com.taivas.settings.Settings;
import com.taivas.settings.SettingsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;

public class Application {

    private static final String CONFIG_FILE = "application.properties";
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IOException, NoInputFoundException {
        LOG.info("======== Starting mb2-log-reader ========");
        LOG.info("Loading settings");
        Settings settings = new SettingsBuilder(CONFIG_FILE)
                .withDefault("plugin-system.input", "udp")
                .withDefault("plugin-system.input.port", "63336")
                .withDefault("plugin-system.input.host", "127.0.0.1:29070")
                .withDefault("plugin-system.rcon.host", "127.0.0.1:29070")
                .withDefault("plugin-system.rcon.password", "password")
                .withDefault("plugin-system.plugin.path", "plugins")
                .build();

        String inputType = settings.get("plugin-system.input");
        LOG.info("Loading input {}", inputType);
        Input input = InputFactory.getInput(inputType);
        input.open(settings);

        LOG.info("Loading rcon");
        RconClient rcon = new RconClient();
        if (settings.hasProperty("plugin-system.rcon.bind")) {
            rcon.connect(
                    settings.getAddress("plugin-system.rcon.host"),
                    settings.getAddress("plugin-system.rcon.bind"),
                    settings.get("plugin-system.rcon.password"),
                    RconClient.DEFAULT_TIMEOUT_MILLISECONDS
            );
        } else {
            rcon.connect(
                    settings.getAddress("plugin-system.rcon.host"),
                    settings.get("plugin-system.rcon.password")
            );
        }

        LOG.info("Loading plugins");
        String pluginPath = settings.get("plugin-system.plugin.path");
        MBPluginManager pluginManager = new MBPluginManager(pluginPath);
        pluginManager.initialize(rcon, settings);
        pluginManager.startPlugins(rcon, settings);

        LOG.info("Loading parser");
        MBParser parser = new MBParser();
        parser.init(settings, pluginManager);

        LOG.info("Starting main loop");
        String line = "";
        while (!Thread.interrupted()) {
            try {
                line = input.readLine();
                LOG.debug(line);
                parser.parseLine(line);
            } catch (Exception e) {
                LOG.error("Uncaught exception while parsing. Last known line: {}", line, e);
            }
        }
    }

}
