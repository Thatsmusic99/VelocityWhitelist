package io.github.thatsmusic99.velocitywhitelist;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.thatsmusic99.velocitywhitelist.commands.WhitelistCommand;
import io.github.thatsmusic99.velocitywhitelist.config.WhitelistConfig;
import io.github.thatsmusic99.velocitywhitelist.config.WhitelistList;
import io.github.thatsmusic99.velocitywhitelist.listeners.PlayerConnectEvent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(id = "velocitywhitelist", name = "Velocity Whitelist", version = "1.0-SNAPSHOT", authors = {"Thatsmusic99"})
public class VelocityWhitelist {

    private final ProxyServer server;
    private final Logger logger;
    private final Path data;

    @Inject
    public VelocityWhitelist(ProxyServer server, Logger logger, @DataDirectory Path data) throws IOException {
        this.server = server;
        this.logger = logger;
        this.data = data;

        // If the data folder does not exist, create it
        if (!Files.exists(data)) Files.createDirectory(data);
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) throws IOException {

        // Register event
        server.getEventManager().register(this, new PlayerConnectEvent());

        // Register command
        CommandManager manager = server.getCommandManager();
        CommandMeta meta = manager.metaBuilder("whitelist").aliases("vwhitelist").plugin(this).build();
        manager.register(meta, WhitelistCommand.createCommand(server));

        // Set up config file
        Path config = data.resolve("config.yml");
        Path whitelist = data.resolve("whitelist.yml");

        // Create files
        if (!Files.exists(config)) Files.createFile(config);
        if (!Files.exists(whitelist)) Files.createFile(whitelist);

        try {
            new WhitelistConfig(config.toFile()).load();
            new WhitelistList(whitelist.toFile()).load();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
