package io.github.thatsmusic99.velocitywhitelist.config;

import io.github.thatsmusic99.configurationmaster.annotations.Option;
import io.github.thatsmusic99.configurationmaster.annotations.handlers.BooleanOptionHandler;
import io.github.thatsmusic99.configurationmaster.api.ConfigFile;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class WhitelistConfig extends ConfigFile {

    private static WhitelistConfig instance;
    public @Option(optionHandler = BooleanOptionHandler.class) boolean WHITELIST_ENABLED = false;
    public @Option String ACCESS_DENIED_MESSAGE = "<red>The server has been whitelisted!";

    public WhitelistConfig(@NotNull File file) throws IOException, IllegalAccessException {
        super(file);

        instance = this;
    }

    public static WhitelistConfig get() {
        return instance;
    }
}
